package com.example.inventory;

import com.example.hardship.CpuHardship;
import com.example.hardship.DatabaseHardship;
import com.example.hardship.MemoryHardship;
import com.example.hardship.ThreadHardship;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
class StoreStockApiController {
  private final StoreStockService inventoryService;
  private final CpuHardship cpuHardship = new CpuHardship();
  private final MemoryHardship memoryHardship = new MemoryHardship();
  private final ThreadHardship threadHardship = new ThreadHardship();
  private final DatabaseHardship databaseHardship;
  private final Random experienceHardship = new Random();
  private final MeterRegistry registry;

  public StoreStockApiController(StoreStockService inventoryService,DatabaseHardship databaseHardship, MeterRegistry registry) {
    this.inventoryService = inventoryService;
    this.databaseHardship = databaseHardship;
    this.registry = registry;
  }

  @GetMapping("/api/search")
  List<ProductSearchResponse> storeProductSearch(@RequestParam String product) {

    List<ProductSearchResponse> response = this.inventoryService.lookupStoreStockLevel(product);
    // if the response is empty, we are going to
    // be logging that for business to analyze
    if(registry != null && product != null) {
      if (response.size() == 0) {
        // what was the product string?
        Counter counter = registry.counter("product.search.none", "product", product);
        counter.increment();
      } else {
        Counter counter = registry.counter("product.search.found", "product", product);
        counter.increment();
      }
    }
    return response;
  }

  @GetMapping("/api/stores/stock")
  List<ProductStockLevelResponse> allStoresStockLevelLookup(@RequestParam Integer sku, @RequestParam Integer quantity) {
    this.generateHardship(sku);
    List<ProductStockLevelResponse> response = this.inventoryService.lookupAllStoresStockLevel(sku,quantity);
    // if the response is empty, we are going to
    // be logging that for business to analyze
    if(registry != null) {
      if(response.size() == 0) {
        // what were the sku and quantity?
        // what was the product string?
        Counter counter = registry.counter("stock.lookup.none", "sku", sku.toString(), "quantity", quantity.toString());
        counter.increment();
      } else {
        Counter counter = registry.counter("stock.lookup.found", "sku", sku.toString(), "quantity", quantity.toString());
        counter.increment();
      }
    }
    return response;
  }

  private void generateHardship(int sku) {
    if(this.experienceHardship.nextInt() % 4 == 0) {
      return;
    }

    if(sku >= 100 & sku < 200) {
      this.cpuHardship.fibonacci();
    }

    if(sku >= 200 && sku < 300) {
      this.databaseHardship.leakConnection();
    }

    if(sku >= 300  && sku < 400){
      this.threadHardship.chaos();
    }

    if(sku >= 400 &&  sku < 500) {
      this.memoryHardship.leakRandomMegaBytes();
    }
  }
}
