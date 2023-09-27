package com.example.inventory.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="central_store_stock")
public class StoreStock extends VersionedObject {

  @EmbeddedId
  private StoreProduct  storeProduct;

  private int quantity;

  public StoreProduct getStoreProduct() {
    return storeProduct;
  }

  public void setStoreProduct(StoreProduct storeProduct) {
    this.storeProduct = storeProduct;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
