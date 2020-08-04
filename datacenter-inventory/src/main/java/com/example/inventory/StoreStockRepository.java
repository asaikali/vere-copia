package com.example.inventory;

import com.example.inventory.model.StoreProduct;
import com.example.inventory.model.StoreStock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockRepository extends JpaRepository<StoreStock, StoreProduct> {

  List<StoreStock> findByStoreProductSku(Integer sku);
}
