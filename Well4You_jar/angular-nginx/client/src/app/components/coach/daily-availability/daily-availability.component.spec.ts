import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DailyAvailabilityComponent } from './daily-availability.component';

describe('DailyAvailabilityComponent', () => {
  let component: DailyAvailabilityComponent;
  let fixture: ComponentFixture<DailyAvailabilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailyAvailabilityComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DailyAvailabilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
