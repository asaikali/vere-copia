package com.example.inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.inventory.model.StoreStock;

import jakarta.persistence.LockModeType;

public interface StoreStockRepository extends JpaRepository<StoreStock, Integer> {

	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	Optional<StoreStock> findWithLockBySku(Integer sku);

}
