import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpSurveyComponent } from './sign-up-survey.component';

describe('SignUpSurveyComponent', () => {
  let component: SignUpSurveyComponent;
  let fixture: ComponentFixture<SignUpSurveyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignUpSurveyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SignUpSurveyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
