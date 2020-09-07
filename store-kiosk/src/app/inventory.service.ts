import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProductSearchResponseModel} from "./models/product-search-response.model";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  constructor(private httpClient: HttpClient){}

  searchStoreStock(product : string) :Observable<ProductSearchResponseModel[]> {
    return this.httpClient
              .get<ProductSearchResponseModel[]>(`/api/search?product=${product}`)
              .pipe(
                map(data => data.map(data => new ProductSearchResponseModel().deserialize(data)))
    );
  }
}
