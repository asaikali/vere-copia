package com.example.inventory;

import com.example.inventory.model.Store;
import org.springframework.data.repository.CrudRepository;

interface StoreRepository extends CrudRepository<Store,Integer> {

}
