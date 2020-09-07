import {Deserializable} from "./deserializable.model";

export class ProductStockLevelResponse implements Deserializable {
  public storeNumber : number;
  public sku : number;
  public quantity: number;

  deserialize(input: any): this {
    return Object.assign(this, input);
  }
}
