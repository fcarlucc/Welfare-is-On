import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCaseDataComponent } from './show-case-data.component';

describe('ShowCaseDataComponent', () => {
  let component: ShowCaseDataComponent;
  let fixture: ComponentFixture<ShowCaseDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowCaseDataComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShowCaseDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
