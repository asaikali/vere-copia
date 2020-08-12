import {Deserializable} from "./deserializable.model";

export class ProductSearchResponseModel implements Deserializable{
  public sku : number;
  public description : string;
  public quantity: number;


  deserialize(input: any): this {
    return Object.assign(this, input);
  }
}
