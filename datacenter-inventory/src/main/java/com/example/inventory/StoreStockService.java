package com.example.inventory;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class StoreStockService {

  private final StoreStockRepository storeStockRepository;

  public StoreStockService(StoreStockRepository storeStockRepository) {
    this.storeStockRepository = storeStockRepository;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public List<ProductStockLevelResponse> lookupStockLevel(Integer sku, int quantity) {
    return this.storeStockRepository.findByStoreProductSku(sku).stream()
        .filter(s -> s.getQuantity() >= quantity).map(s -> {
          var response = new ProductStockLevelResponse();
          response.setQuantity(s.getQuantity());
          response.setSku(s.getStoreProduct().getSku());
          response.setStoreNumber(s.getStoreProduct().getStoreNumber());
          return response;
        }).collect(Collectors.toList());
  }
}
