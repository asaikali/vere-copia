package com.example.inventory.model;

import com.example.inventory.model.StoreInventory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.object.StoredProcedure;

public interface StoreInventoryRepository extends JpaRepository<StoreInventory, StoreProduct> {

  List<StoreInventory> findByIdSku(String sku);
}
