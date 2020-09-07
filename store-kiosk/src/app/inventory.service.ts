import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProductSearchResponseModel} from "./models/product-search-response.model";
import {map} from "rxjs/operators";
import {ProductStockLevelResponse} from "./models/product-stock-level-response";

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

  searchAllStore(sku: string): Observable<ProductStockLevelResponse[]> {
    return this.httpClient
    .get<ProductStockLevelResponse[]>(`/api/stores/stock?sku=${sku}&quantity=1`)
    .pipe(
      map(data => data.map(data => new ProductStockLevelResponse().deserialize(data)))
    );
  }
}
