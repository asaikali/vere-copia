package com.example.exchange;

public class ProductStockLevelResponse {
	  private int storeNumber;
	  private String storeName;
	  private String storeAddress;
	  private String product;
	  private int sku;
	  private int quantity;

	  public String getProduct() {
	    return product;
	  }

	  public void setProduct(String product) {
	    this.product = product;
	  }

	  public String getStoreName() {
	    return storeName;
	  }

	  public void setStoreName(String storeName) {
	    this.storeName = storeName;
	  }

	  public String getStoreAddress() {
	    return storeAddress;
	  }

	  public void setStoreAddress(String storeAddress) {
	    this.storeAddress = storeAddress;
	  }

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

