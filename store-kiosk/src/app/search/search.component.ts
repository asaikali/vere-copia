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
  title = 'store-kiosk';
  searchForm = new FormGroup( {
    product :  new FormControl('')
  });

  displayedColumns: string[] = ['sku', 'description', 'quantity'];
  searchResults:  ProductSearchResponseModel[];

  search() : void {
    console.log(this.searchForm.value.product.value);
    this.inventoryService.search(this.searchForm.value.product).subscribe(
      (matchingProducts : ProductSearchResponseModel[]) => {
        this.searchResults = matchingProducts;
      });
  }

  options: FormGroup;
  colorControl = new FormControl('primary');
  fontSizeControl = new FormControl(16, Validators.min(10));

  constructor(fb: FormBuilder,private inventoryService: InventoryService) {
    this.options = fb.group({
      color: this.colorControl,
      fontSize: this.fontSizeControl,
    });
  }


}
