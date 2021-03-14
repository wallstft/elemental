import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SyndicatedLoanComponent } from './syndicated-loan.component';

describe('SyndicatedLoanComponent', () => {
  let component: SyndicatedLoanComponent;
  let fixture: ComponentFixture<SyndicatedLoanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SyndicatedLoanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SyndicatedLoanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
