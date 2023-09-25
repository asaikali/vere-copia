package com.example.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="store_stock")
public class StoreStock extends VersionedObject {


  @Id
  @Column(name="sku")
  private Integer sku;

  @Column(name="quantity")
  private int quantity;

  public Integer getSku() {
    return sku;
  }

  public void setSku(Integer sku) {
    this.sku = sku;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
