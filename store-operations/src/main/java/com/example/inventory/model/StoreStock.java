package com.example.inventory.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
