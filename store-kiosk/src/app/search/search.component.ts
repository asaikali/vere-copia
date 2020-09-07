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

  displayedColumns: string[] = ['sku', 'description', 'quantity'];
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

  ngOnInit(): void {
  }

}
