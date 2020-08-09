package com.example.inventory;

class ProductSearchResponse {
  private Integer sku;
  private String description;
  private Integer quantity;

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

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
