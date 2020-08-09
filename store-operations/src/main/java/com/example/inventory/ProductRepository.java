package com.example.inventory;

import com.example.inventory.model.Product;
import com.example.inventory.model.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
