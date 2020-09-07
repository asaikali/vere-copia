import { Component, OnInit } from '@angular/core';
import {InventoryService} from "../inventory.service";
import { ActivatedRoute } from '@angular/router';
import {ProductStockLevelResponse} from "../models/product-stock-level-response";

@Component({
  selector: 'app-sku-inventory-detail',
  templateUrl: './sku-inventory-detail.component.html',
  styleUrls: ['./sku-inventory-detail.component.css']
})
export class SkuInventoryDetailComponent implements OnInit {

  constructor(private inventoryService: InventoryService,
              private route: ActivatedRoute) { }

  stockLevel:  ProductStockLevelResponse[];
  displayedColumns: string[] = ['sku', 'storeNumber', 'quantity'];

  ngOnInit(): void {
    this.searchProducts();
  }

  private searchProducts() : void {
    const sku = this.route.snapshot.paramMap.get('id');
    this.inventoryService.searchAllStore(sku).subscribe(
      (response : ProductStockLevelResponse[]) => {
        this.stockLevel = response;
      }
    );
  }

}
