import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasedDataComponent } from './purchased-data.component';

describe('PurchasedDataComponent', () => {
  let component: PurchasedDataComponent;
  let fixture: ComponentFixture<PurchasedDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PurchasedDataComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PurchasedDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
