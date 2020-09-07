package com.example.inventory;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class StoreStockService {

  private final StoreStockRepository storeStockRepository;
  private final StoreRepository storeRepository;
  private final ProductRepository productRepository;

  public StoreStockService(StoreStockRepository storeStockRepository, StoreRepository storeRepository,ProductRepository productRepository) {
    this.storeStockRepository = storeStockRepository;
    this.storeRepository = storeRepository;
    this.productRepository = productRepository;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public List<ProductStockLevelResponse> lookupStockLevel(Integer sku, int quantity) {
    return this.storeStockRepository.findByStoreProductSku(sku).stream()
        .filter(s -> s.getQuantity() >= quantity).map(s -> {
          var store  = storeRepository.findById(s.getStoreProduct().getStoreNumber()).orElseThrow();
          var product = productRepository.findById(s.getStoreProduct().getSku()).orElseThrow();
          var response = new ProductStockLevelResponse();
          response.setQuantity(s.getQuantity());
          response.setSku(s.getStoreProduct().getSku());
          response.setProduct(product.getDescription());
          response.setStoreNumber(s.getStoreProduct().getStoreNumber());
          response.setStoreName(store.getName());
          response.setStoreAddress(String.format("%s , %s, %s, %s", store.getStreet(),store.getCity(),store.getProvince(),store.getPostalCode()));

          return response;
        }).collect(Collectors.toList());
  }
}
