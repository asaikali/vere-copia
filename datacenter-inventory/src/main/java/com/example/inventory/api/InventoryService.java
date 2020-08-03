package com.example.inventory.api;

import com.example.inventory.model.StoreInventoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class InventoryService {

  private final StoreInventoryRepository storeInventoryRepository;

  public InventoryService(StoreInventoryRepository storeInventoryRepository) {
    this.storeInventoryRepository = storeInventoryRepository;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public List<ProductStockLevelResponse> lookupStoreStockLevel(String sku, int quantity) {
    return this.storeInventoryRepository.findByIdSku(sku).stream()
        .filter(s -> s.getQuantity() >= quantity).map(s -> {
          var response = new ProductStockLevelResponse();
          response.setQuantity(s.getQuantity());
          response.setSku(s.getStoreProduct().getSku());
          response.setStoreNumber(s.getStoreProduct().getStoreNumber());
          return response;
        }).collect(Collectors.toList());
  }
}
