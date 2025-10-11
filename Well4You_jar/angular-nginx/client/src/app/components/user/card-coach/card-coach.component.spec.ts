import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardCoachComponent } from './card-coach.component';

describe('CardCoachComponent', () => {
  let component: CardCoachComponent;
  let fixture: ComponentFixture<CardCoachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardCoachComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CardCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
