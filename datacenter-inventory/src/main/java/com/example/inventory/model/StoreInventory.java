package com.example.inventory.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="store_inventory")
public class StoreInventory extends VersionedObject {

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
