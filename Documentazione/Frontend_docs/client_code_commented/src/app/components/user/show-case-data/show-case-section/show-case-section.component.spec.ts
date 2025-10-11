import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCaseSectionComponent } from './show-case-section.component';

describe('ShowCaseSectionComponent', () => {
  let component: ShowCaseSectionComponent;
  let fixture: ComponentFixture<ShowCaseSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowCaseSectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShowCaseSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
