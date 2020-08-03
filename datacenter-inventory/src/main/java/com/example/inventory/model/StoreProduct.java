package com.example.inventory.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StoreProduct implements Serializable {

  @Column(name="store_number")
  private int storeNumber;

  @Column(name="sku")
  private String sku;

  public int getStoreNumber() {
    return storeNumber;
  }

  public void setStoreNumber(int storeNumber) {
    this.storeNumber = storeNumber;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StoreProduct that = (StoreProduct) o;

    if (storeNumber != that.storeNumber) {
      return false;
    }
    return sku.equals(that.sku);
  }

  @Override
  public int hashCode() {
    int result = storeNumber;
    result = 31 * result + sku.hashCode();
    return result;
  }
}
