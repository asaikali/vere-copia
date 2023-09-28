package com.example.exchange;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * HTTP declarative interface for the Vere Copia API.  Each API method has a blocking and reactive version.
 *
 */
public interface VeraCopiaClient 
{
	@GetExchange("/api/search")
	public List<ProductSearchResponse> search(@RequestParam("product") String product);
	
	/*
	 * Reactive version
	 */
	@GetExchange("/api/search")
	public Flux<ProductSearchResponse> searchR(@RequestParam("product") String product);
	
	@GetExchange("/api/stores/stock")
	public List<ProductStockLevelResponse> searchAllStoreStockLevels(@RequestParam("sku") String sku, @RequestParam("quantity") Integer quantity);	
	
	/*
	 * Reactive version
	 */
	@GetExchange("/api/stores/stock")
	public List<ProductStockLevelResponse> searchAllStoreStockLevelsR(@RequestParam("sku") String sku, @RequestParam("quantity") Integer quantity);	
	
	@PostExchange("/api/inventory/{sku}")
	public Integer updateLocalStoreStockLevel(@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity);	
	
	/*
	 * Reactive version
	 */
	@PostExchange("/api/inventory/{sku}")
	public Mono<Integer> updateLocalStoreStockLevelR(@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity);		
	
	@PostExchange("/api/inventory/receive/{sku}")
	public Integer receiveStoreStockInventory(@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity);	
	
	/*
	 * Reactive version
	 */
	@PostExchange("/api/inventory/receive/{sku}")
	public Mono<Integer> receiveStoreStockInventoryR(@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity);	
	
	@PostExchange("/api/inventory/purchase/{sku}")
	public Integer purchaseStoreStockInventory(@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity);	
	
	/*
	 * Reactive version
	 */
	@PostExchange("/api/inventory/purchase/{sku}")
	public Mono<Integer> purchaseStoreStockInventoryR(@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity);	
}

