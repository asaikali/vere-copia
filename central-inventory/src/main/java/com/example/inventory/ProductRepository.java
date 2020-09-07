package com.example.inventory;

import com.example.inventory.model.Product;
import org.springframework.data.repository.CrudRepository;

interface ProductRepository extends CrudRepository<Product,Integer> {

}
