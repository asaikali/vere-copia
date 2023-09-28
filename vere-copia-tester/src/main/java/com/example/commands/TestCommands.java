package com.example.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.exchange.VeraCopiaClient;
import com.example.printers.ProductSearchPrinter;
import com.example.printers.ProductStockLevelPrinter;


@ShellComponent
public class TestCommands 
{
	@Autowired
	protected VeraCopiaClient client;
	
	protected ProductSearchPrinter productSearchPrinter;
	
	protected ProductStockLevelPrinter stockLevelPrinter;
	
	public TestCommands()
	{
		productSearchPrinter = new ProductSearchPrinter();
		
		stockLevelPrinter = new ProductStockLevelPrinter();
	}
	
	@ShellMethod(value = "Perform a store search by a product name.", key = "search")
	public String search(@ShellOption(defaultValue = "ball", help="The name of the project to search for") String product)
	{
		final var results = client.search(product);
		
		if (results.size() == 0)  
		   return "No results found.";
		else
		{
			return productSearchPrinter.generateRecordsReport(results);
		}
		 
	}
	
	@ShellMethod(value = "Searches all stores for the availability for a given product sku.", key = "searchStoreAvailability")
	public String searchStoreAvailability(@ShellOption(defaultValue = "100", help="The sku of the product") String sku,
			@ShellOption(defaultValue = "0", help="Minimum availalbe inventory amount that a store must have") Integer quantity)
	{
		final var results = client.searchAllStoreStockLevels(sku, quantity);
		
		if (results.size() == 0)  
		   return "No results found.";
		else
		{
			return stockLevelPrinter.generateRecordsReport(results);
		}
		 
	}	
	
	@ShellMethod(value = "Updates the inventory level for a given project sku at the \"Local\" store.", key = "updateProductInventoryLevel")
	public String updateProductInventoryLevel(@ShellOption(defaultValue = "100", help="The sku of the product to update") String sku,
			@ShellOption(defaultValue = "25", help="The new inventory level of the product") Integer quantity)
	{
		try
		{
			final var newAmount = client.updateLocalStoreStockLevel(sku, quantity);
			return String.format("New inventory level for product sku %s: %d", sku, newAmount);
		}
		catch (WebClientResponseException.NotFound e)
		{
			return "Requested SKU could not be found.";
		}
		
	}	
	
	@ShellMethod(value = "Receives new inventory resulting in additive an inventory level for a given project sku at the \"Local\" store.", key = "receiveProductInventory")
	public String receiveProductInventory(@ShellOption(defaultValue = "100", help="The sku of the product received") String sku,
			@ShellOption(defaultValue = "25", help="The amount of inventory received") Integer quantity)
	{
		try
		{
			final var newAmount = client.receiveStoreStockInventory(sku, quantity);
			return String.format("New inventory level for product sku %s: %d", sku, newAmount);
		}
		catch (WebClientResponseException.NotFound e)
		{
			return "Requested SKU could not be found.";
		}
		
	}	
	
	@ShellMethod(value = "Purchase an given amount of a product sku at the \"Local\" store resulting in a decrease of inventory.", key = "purchaseProductInventory")
	public String purchaseProductInventory(@ShellOption(defaultValue = "100", help="The sku of the product to update") String sku,
			@ShellOption(defaultValue = "25", help="The amount of the product to be purchases") Integer quantity)
	{
		try
		{
			final var newAmount = client.purchaseStoreStockInventory(sku, quantity);
			return String.format("New inventory level for product sku %s: %d", sku, newAmount);
		}
		catch (WebClientResponseException.NotFound e)
		{
			return "Requested SKU could not be found.";
		}
		catch (WebClientResponseException.BadRequest e)
		{
			return "Not enough inventory available to fulfill the request.";
		}		
	}	
}
