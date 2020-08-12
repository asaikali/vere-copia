import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {InventoryService} from "./inventory.service";
import {ProductSearchResponseModel} from "./models/product-search-response.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'store-kiosk';
  searchForm = new FormGroup( {
    product :  new FormControl('')
  });


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

  getFontSize() {
    return Math.max(10, this.fontSizeControl.value);
  }
}
