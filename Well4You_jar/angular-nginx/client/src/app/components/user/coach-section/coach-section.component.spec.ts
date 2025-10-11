import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachSectionComponent } from './coach-section.component';

describe('CoachSectionComponent', () => {
  let component: CoachSectionComponent;
  let fixture: ComponentFixture<CoachSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoachSectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CoachSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
