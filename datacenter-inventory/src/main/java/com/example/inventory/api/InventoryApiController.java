package com.example.inventory.api;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class InventoryApiController {
  private final InventoryService inventoryService;

  public InventoryApiController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @GetMapping("/stores/stock")
  List<ProductStockLevelResponse> lookupStockLevel(@RequestParam String sku, @RequestParam int quantity) {
    return this.inventoryService.lookupStoreStockLevel(sku,quantity);
  }
}
