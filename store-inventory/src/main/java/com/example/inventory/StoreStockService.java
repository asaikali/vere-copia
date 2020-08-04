package com.example.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
class StoreStockService {

  private final StoreStockRepository storeStockRepository;
  private final RestTemplate centralInventoryTemplate;

  public StoreStockService(StoreStockRepository storeStockRepository,
      RestTemplateBuilder restTemplateBuilder,
      @Value("${central_inventory.url}") String centralInventoryUrl) {
    this.storeStockRepository = storeStockRepository;
    this.centralInventoryTemplate = restTemplateBuilder.rootUri(centralInventoryUrl).build();
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public int lookupStoreStockLevel(Integer sku, int quantity) {
    return this.storeStockRepository.findById(sku).map(stock -> stock.getQuantity()).orElse(0);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public List<ProductStockLevelResponse> lookupAllStoresStockLevel(Integer sku, int quantity) {
    var response = this.centralInventoryTemplate
        .getForObject("/stores/stock?sku={s}&quantity={q}", ProductStockLevelResponse[].class, sku,
            quantity);
    return Arrays.asList(response);
  }
}
