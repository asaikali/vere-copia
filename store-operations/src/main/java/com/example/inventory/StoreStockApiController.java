package com.example.inventory;

import java.util.List;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hardship.CpuHardship;
import com.example.hardship.DatabaseHardship;
import com.example.hardship.MemoryHardship;
import com.example.hardship.ThreadHardship;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.SpanTag;

@RestController
class StoreStockApiController {
  private final StoreStockService inventoryService;
  private final CpuHardship cpuHardship = new CpuHardship();
  private final MemoryHardship memoryHardship = new MemoryHardship();
  private final ThreadHardship threadHardship = new ThreadHardship();
  private final DatabaseHardship databaseHardship;
  private final Random experienceHardship = new Random();
  private final MeterRegistry registry;

  private final Tracer tracer;

  public StoreStockApiController(StoreStockService inventoryService,DatabaseHardship databaseHardship, MeterRegistry registry, Tracer tracer) {
    this.inventoryService = inventoryService;
    this.databaseHardship = databaseHardship;
    this.registry = registry;
    this.tracer = tracer;
  }

  @GetMapping("/api/search")
  List<ProductSearchResponse> storeProductSearch(@RequestParam @SpanTag  String product) {

    tracer.currentSpan().tag("foo", product);

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

  /**
   * Correct a store stock inventory level of a sku/product with an absolute value.
   * @param sku The product/sku to update.
   * @param quantity The new stock inventory level of the product/sku.
   * @return The update value of the stock level.
   */
  @PostMapping(value="/api/inventory/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Integer> updateStoreStockLevel(@PathVariable("sku") Integer sku, @RequestParam Integer quantity)
  {
    final var newStock = this.inventoryService.updateStoreStockLevel(sku, quantity);

    return (newStock != null) ?
        ResponseEntity.ok().body(newStock) :
        ResponseEntity.notFound().build();
  }

  /**
   * Receives addition store stock inventory for a sku/product with an absolute value.  Results in adding to the 
   * current inventory level.
   * @param sku The product sku to add to.
   * @param quantity The amount of inventory received which will be added to the current level.
   * @return The new value of the stock level.
   */
  @PostMapping(value="/api/inventory/receive/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Integer> receiveStoreStockInventory(@PathVariable("sku") Integer sku, @RequestParam Integer quantity)
  {
    final var newStock = this.inventoryService.receiveStoreStockInventory(sku, quantity);

    return (newStock != null) ?
        ResponseEntity.ok().body(newStock) :
        ResponseEntity.notFound().build();
  }

  /**
   * Excutes a purchase of product/sku resulting in a decrement of store stock inventory.  An HTTP BAD_REQUEST error is returned
   * if there is not enough stock to fulfill the purchase request.
   * @param sku The product/sku to puchase.
   * @param quantity The amount to of the product to purchase
   * @return The new value of the stock level.
   */
  @PostMapping(value="/api/inventory/purchase/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Integer> purchaseStockInventory(@PathVariable("sku") Integer sku, @RequestParam Integer quantity)
  {
    final var newStock = this.inventoryService.purchaseStoreStockInventory(sku, quantity);

    if (newStock != null)
      if (newStock.intValue() == -1)
        return ResponseEntity.badRequest().build();
      else
        return ResponseEntity.ok().body(newStock);
    else
      return ResponseEntity.notFound().build();
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
