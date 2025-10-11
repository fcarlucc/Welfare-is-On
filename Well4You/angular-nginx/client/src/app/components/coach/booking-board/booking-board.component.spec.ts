import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingBoardComponent } from './booking-board.component';

describe('BookingBoardComponent', () => {
  let component: BookingBoardComponent;
  let fixture: ComponentFixture<BookingBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingBoardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookingBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
