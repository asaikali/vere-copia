package com.example.inventory;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StoreStockApiController {
  private final StoreStockService inventoryService;

  public StoreStockApiController(StoreStockService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @GetMapping("/stores/stock")
  List<ProductStockLevelResponse> lookupStockLevel(@RequestParam Integer sku, @RequestParam Integer quantity) {
    return this.inventoryService.lookupStockLevel(sku,quantity);
  }
}
