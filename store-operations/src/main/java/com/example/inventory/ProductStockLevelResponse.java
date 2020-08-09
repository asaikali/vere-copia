package com.example.inventory;

class ProductStockLevelResponse {
  private int storeNumber;
  private int sku;
  private int quantity;

  public int getStoreNumber() {
    return storeNumber;
  }

  public void setStoreNumber(int storeNumber) {
    this.storeNumber = storeNumber;
  }

  public int getSku() {
    return sku;
  }

  public void setSku(int sku) {
    this.sku = sku;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
