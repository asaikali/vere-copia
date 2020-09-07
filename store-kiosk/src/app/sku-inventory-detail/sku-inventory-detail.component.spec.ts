import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkuInventoryDetailComponent } from './sku-inventory-detail.component';

describe('SkuInventoryDetailComponent', () => {
  let component: SkuInventoryDetailComponent;
  let fixture: ComponentFixture<SkuInventoryDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkuInventoryDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkuInventoryDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
