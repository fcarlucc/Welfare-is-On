import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WelfareCoachComponent } from './welfare-coach.component';

describe('WelfareCoachComponent', () => {
  let component: WelfareCoachComponent;
  let fixture: ComponentFixture<WelfareCoachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WelfareCoachComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WelfareCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
