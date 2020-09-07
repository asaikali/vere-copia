import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SkuInventoryDetailComponent} from "./sku-inventory-detail/sku-inventory-detail.component";
import {SearchComponent} from "./search/search.component";

const routes: Routes = [
  {path:'', component: SearchComponent},
  {path: "sku/:id", component: SkuInventoryDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
