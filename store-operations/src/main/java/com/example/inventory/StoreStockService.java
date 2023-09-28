package com.example.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
class StoreStockService {

  private final ProductRepository productRepository;
  private final StoreStockRepository storeStockRepository;
  private final RestTemplate centralInventoryTemplate;

  public StoreStockService(StoreStockRepository storeStockRepository,
      ProductRepository productRepository,
      RestTemplateBuilder restTemplateBuilder,
      @Value("${central_inventory.url}") String centralInventoryUrl) {
    this.storeStockRepository = storeStockRepository;
    this.productRepository = productRepository;
    this.centralInventoryTemplate = restTemplateBuilder.rootUri(centralInventoryUrl).build();
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public List<ProductSearchResponse> lookupStoreStockLevel(String product) {
   return this.productRepository.findAll().stream()
        .filter( p -> p.getDescription().contains(product))
        .map(p -> {
          var searchResponse = new ProductSearchResponse();
          searchResponse.setDescription(p.getDescription());
          searchResponse.setSku(p.getSku());

          var stockLevel = this.storeStockRepository.findById(p.getSku()).map(s -> s.getQuantity()).orElse(0);
          searchResponse.setQuantity(stockLevel);

          return searchResponse;
        }).collect(Collectors.toList());

  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public List<ProductStockLevelResponse> lookupAllStoresStockLevel(Integer sku, int quantity) {
    var response = this.centralInventoryTemplate
        .getForObject("/stores/stock?sku={s}&quantity={q}", ProductStockLevelResponse[].class, sku,
            quantity);
    return Arrays.asList(response);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public Integer updateStoreStockLevel(Integer sku, int quantity) {

    return this.storeStockRepository.findWithLockBySku(sku).map(inv -> {
      inv.setQuantity(quantity);
      storeStockRepository.save(inv);
      return quantity;
    })
    .orElse(null);

  }  

  @Transactional(propagation = Propagation.REQUIRED)
  public Integer receiveStoreStockInventory(Integer sku, int quantity) {

    return this.storeStockRepository.findWithLockBySku(sku).map(inv -> {
      inv.setQuantity(quantity + inv.getQuantity());
      storeStockRepository.save(inv);
      return inv.getQuantity();
    })
    .orElse(null);
  } 

  @Transactional(propagation = Propagation.REQUIRED)
  public Integer purchaseStoreStockInventory(Integer sku, int quantity) {

    return this.storeStockRepository.findWithLockBySku(sku).map(inv -> {
      if((inv.getQuantity() - quantity) < 0)
        return Integer.valueOf(-1); // -1 Indicates not enough inventory for the purchase
      
      inv.setQuantity(inv.getQuantity() - quantity);
      storeStockRepository.save(inv);
      return inv.getQuantity();
    })
    .orElse(null);
  } 
  
}
