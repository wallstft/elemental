import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditRiskComponent } from './credit-risk.component';

describe('CreditRiskComponent', () => {
  let component: CreditRiskComponent;
  let fixture: ComponentFixture<CreditRiskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreditRiskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditRiskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
