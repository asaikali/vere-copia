package com.example.inventory;

import com.example.hardship.CpuHardship;
import com.example.hardship.DatabaseHardship;
import com.example.hardship.MemoryHardship;
import com.example.hardship.ThreadHardship;
import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StoreStockApiController {
  private final StoreStockService inventoryService;
  private final CpuHardship cpuHardship = new CpuHardship();
  private final MemoryHardship memoryHardship = new MemoryHardship();
  private final ThreadHardship threadHardship = new ThreadHardship();
  private final DatabaseHardship databaseHardship;
  private final Random experienceHardship = new Random();

  public StoreStockApiController(StoreStockService inventoryService,DatabaseHardship databaseHardship) {
    this.inventoryService = inventoryService;
    this.databaseHardship = databaseHardship;
  }

  @GetMapping("/search")
  List<ProductSearchResponse> storeProductSearch(@RequestParam String product) {
    return this.inventoryService.lookupStoreStockLevel(product);
  }

  @GetMapping("/stores/stock")
  List<ProductStockLevelResponse> allStoresStockLevelLookup(@RequestParam Integer sku, @RequestParam Integer quantity) {
    this.generateHardship(sku);
    return this.inventoryService.lookupAllStoresStockLevel(sku,quantity);
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
