package com.example.commands;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.exchange.VeraCopiaClient;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class StoreOpsSim 
{
	private static final int REORDER_THRESHOLD = 3;
	
	private static final int REORDER_AMOUNT = 10;
	
	private static final int INVENTORY_CHECK_INTERVAL = 60;
	
	private static final int LIGHT_SHOPPERS = 1;

	private static final int MEDIUM_SHOPPERS = 5;
	
	private static final int HEAVY_SHOPPERS = 15;
	
	private static final int LIGHT_SHOPPER_INTERVAL = 5;

	private static final int MEDIUM_SHOPPER_INTERVAL = 3;
	
	private static final int HEAVY_SHOPPER_INTERVAL = 1;	
	
	enum TrafficLoad
	{
		light,
		
		medium,
		
		heavy
	}

	@Autowired
	protected VeraCopiaClient client;
	
	// Mainly used a mutable Boolean wrapper vs needing the built in concurrent access method
	protected final AtomicBoolean running;
	
	protected final Object inventoryWakeup;
	
	protected final Runnable shopperSim;
	
	protected final Runnable inventorySim;
	
	protected final ExecutorService serviceExec;
	
	protected final ExecutorService shopperExec;
	
	protected final ArrayList<Integer> skus;
	
	protected final AtomicReference<TrafficLoad> load;
	
	public StoreOpsSim()
	{
		this(TrafficLoad.light);
	}
	
	public StoreOpsSim(TrafficLoad load)
	{
		this.load = new AtomicReference<>(load);
		
		running = new AtomicBoolean(false); 
		inventoryWakeup = new Object();
		
		skus = new ArrayList<>();
		
		shopperSim = () -> {
			shopperSim();
		};
		
		inventorySim = () -> {
			inventorySim();
		};
		
		serviceExec = Executors.newFixedThreadPool(2, new ThreadFactoryBuilder().setDaemon(true).build());
		shopperExec = new ThreadPoolExecutor(5, 25, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(50));
		
		
		serviceExec.execute(shopperSim);
		serviceExec.execute(inventorySim);
	}
	
	public void startSimulation()
	{
		synchronized (running)
		{
			running.set(true);
			running.notifyAll();		
		}
	}
	
	public void stopSimulation()
	{
		synchronized (running)
		{
			running.set(false);
			running.notifyAll();		
		}
	}
	
	public boolean isRunning()
	{
		return running.get();
	}
	
	public void setLoad(TrafficLoad load)
	{
		this.load.set(load);
	}
	
	public TrafficLoad getLoad()
	{
		return load.get();
	}
	
	protected void waitForRunSignal()
	{
		if (!running.get())
		{
			synchronized(running)
			{
				try
				{
					running.wait();
				}
				catch (Exception e) {}
			}
		}
	}
	
	protected Flux<Void> shopTheStore()
	{
		final int MAX_SKU_PRUCHASES = 10;
		final int MIN_SKU_PRUCHASES = 2;
		final int MAX_QUANTITY = 5;
		final int MIN_QUANTITY = 1;	
		
		if (skus.size() == 0)
			return Flux.empty();
		
		// make random purchases
		final var rn = new SecureRandom();
		rn.setSeed(System.currentTimeMillis());
		
		// how many products should we purchase
		final var numProdPurchases = rn.nextInt(MAX_SKU_PRUCHASES - MIN_SKU_PRUCHASES + 1) + MIN_SKU_PRUCHASES;
		
		final Flux<Integer> shoppers = Flux.fromStream(IntStream.rangeClosed(1, numProdPurchases).boxed()).delayElements(Duration.ofMillis(50));
		
		return shoppers.flatMap(i ->
		{					
			// randomly select what to buy
			final var sku = skus.get(rn.nextInt(skus.size() - 1));
			
			// randomly select quantity to buy
			final var qauntiy = rn.nextInt(MAX_QUANTITY - MIN_QUANTITY + 1) + MIN_QUANTITY;
			
			log.info("Attempting to purchase a quanity of {} items for sku {}", qauntiy, sku);
			
				return client.purchaseStoreStockInventoryR(sku, qauntiy)
					.doOnSuccess(s -> log.info("Purchase of {} items of sku {} was successful", qauntiy, sku))
					.onErrorResume(e -> 
					{	
						if (e instanceof WebClientResponseException.BadRequest)
						{
							log.info("Not enough inventory of sku {} is available.  Requesting more inventory", sku);
							
							// not enough inventory... trigger the inventory sim to replenish with more stock
							synchronized(inventoryWakeup) { inventoryWakeup.notifyAll();}
						}
						else
							log.info("Failed to successfully purchase sku {}: {}", sku, e.getMessage());
						return Mono.empty();
					})
					.then(Mono.empty());
		});
	}
	
	protected void shopperSim()
	{
		while(true)
		{
			waitForRunSignal();
			
			
			if (running.get())
			{
				// initialize skus if not already done
				try {
					if (skus.size() == 0)
						skus.addAll(client.search("*").stream().map(prod -> prod.getSku()).collect(Collectors.toList()));
					
				}
				catch (Exception e) {log.error("Failed to load product skus: {}", e.getLocalizedMessage(), e);}
				
				int loadSize = getShopperSizeForLoadSize();
				
				final Flux<Integer> shoppers = Flux.fromStream(IntStream.rangeClosed(1, loadSize).boxed()).delayElements(Duration.ofMillis(50));
				
				log.info("\r\n---------   Getting ready to shop.  ---------\r\n\r\n");
				
				shoppers.flatMap(i -> 
				{
					try
					{
						return shopTheStore();
					}
					catch(Exception e) {
						log.error("General shopping error: {}", e.getMessage(), e);
						return Mono.empty();
					}
				})
				.collectList().block();
			}
			
			// sleep
			try
			{
				Thread.sleep(Duration.ofSeconds(getShopperSleepTime()).toMillis());
			}
			catch (Exception e) {}
			
		}
	}
	
	protected void inventorySim()
	{
		while(true)
		{
			waitForRunSignal();
			
			if (running.get())
			{
				try
				{
					log.info("\r\n---------   Checking if any inventory needs to be restocked. \r\n\r\n");
					
					// check current stock level
					client.searchR("*")
					.filter(prod -> prod.getQuantity() < REORDER_THRESHOLD)
					.flatMap(prod ->  {
						
						log.info("Requesting to restock sku {} with {} more items{}", prod.getSku(), REORDER_AMOUNT);
						
						return client.receiveStoreStockInventoryR(prod.getSku(), REORDER_AMOUNT)
							.doOnSuccess(s -> log.info("Restock of sku {} was successful", prod.getSku()))
							.onErrorResume(e -> {
								log.error("Failed to restock sku {} with {} more items", prod.getSku(), REORDER_AMOUNT);
								return Mono.empty();
							});
					})
					.collectList().block();
				}
				catch (Exception e)
				{
					log.error("Restock error: {}", e.getLocalizedMessage(), e);
				}
			}
			
			// use a "wait" vs a sleep as it may be necessary to wake up early to receive more inventory
			synchronized(inventoryWakeup)
			{
				try
				{
					inventoryWakeup.wait(Duration.ofSeconds(INVENTORY_CHECK_INTERVAL).toMillis());
				}
				catch (Exception e) {}
			}
		}
	}	
	
	public Integer getShopperSleepTime()
	{
		switch (load.get())
		{
			case light:
				return LIGHT_SHOPPER_INTERVAL;
			case medium:
				return MEDIUM_SHOPPER_INTERVAL;
			case heavy:
				return HEAVY_SHOPPER_INTERVAL;
			default:
				return LIGHT_SHOPPER_INTERVAL;
		}
	}
	
	public int getShopperSizeForLoadSize()
	{
		switch (load.get())
		{
			case light:
				return LIGHT_SHOPPERS;
			case medium:
				return MEDIUM_SHOPPERS;
			case heavy:
				return HEAVY_SHOPPERS;
			default:
				return LIGHT_SHOPPERS;
		}
	}
}
