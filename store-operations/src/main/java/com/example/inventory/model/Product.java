package com.example.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends VersionedObject{

  @Id
  @Column(name = "sku")
  private Integer sku;

  @Column(name = "description")
  private String description;

  public Integer getSku() {
    return sku;
  }

  public void setSku(Integer sku) {
    this.sku = sku;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
