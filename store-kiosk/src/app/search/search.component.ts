import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProductSearchResponseModel} from "../models/product-search-response.model";
import {InventoryService} from "../inventory.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchForm = new FormGroup( {
    product :  new FormControl('')
  });

  displayedColumns: string[] = ['sku', 'description', 'quantity', 'hardship','probability'];
  searchResults:  ProductSearchResponseModel[];

  constructor(private inventoryService: InventoryService) {
  }

  search() : void {
    console.log('searching for ${this.searchForm.value.product.value}');
    this.inventoryService.searchStoreStock(this.searchForm.value.product).subscribe(
      (matchingProducts : ProductSearchResponseModel[]) => {
        this.searchResults = matchingProducts;
      });
  }

  public expectedHardShipProbability() : string {
    return "25%";
  }

  public expectedHardShip(sku:number) : string {
    if(sku >= 100 && sku < 200) {
      return "CPU";
    }

    if(sku >= 200 && sku < 300) {
      return "Leaked Database Connection";
    }

    if(sku >= 300  && sku < 400){
      return "Thread Chaos";
    }

    if(sku >= 400 &&  sku < 500) {
      return  "Leak Memory";
    }

    return "None";
  }

  ngOnInit(): void {
  }

}
