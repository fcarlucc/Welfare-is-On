import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCoachComponent } from './create-coach.component';

describe('CreateCoachComponent', () => {
  let component: CreateCoachComponent;
  let fixture: ComponentFixture<CreateCoachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateCoachComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateCoachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
