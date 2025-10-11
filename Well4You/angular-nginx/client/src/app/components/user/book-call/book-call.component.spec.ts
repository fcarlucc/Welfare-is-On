import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookCallComponent } from './book-call.component';

describe('BookCallComponent', () => {
  let component: BookCallComponent;
  let fixture: ComponentFixture<BookCallComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookCallComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookCallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
