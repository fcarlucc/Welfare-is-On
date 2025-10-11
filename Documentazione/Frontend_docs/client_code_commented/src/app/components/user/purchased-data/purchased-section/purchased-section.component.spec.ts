import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasedSectionComponent } from './purchased-section.component';

describe('PurchasedSectionComponent', () => {
  let component: PurchasedSectionComponent;
  let fixture: ComponentFixture<PurchasedSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PurchasedSectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PurchasedSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
