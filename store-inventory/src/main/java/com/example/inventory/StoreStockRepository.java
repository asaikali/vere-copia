package com.example.inventory;

import com.example.inventory.model.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStockRepository extends JpaRepository<StoreStock, Integer> {
}
