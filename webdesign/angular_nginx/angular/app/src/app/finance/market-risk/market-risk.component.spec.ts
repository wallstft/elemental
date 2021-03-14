import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketRiskComponent } from './market-risk.component';

describe('MarketRiskComponent', () => {
  let component: MarketRiskComponent;
  let fixture: ComponentFixture<MarketRiskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarketRiskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketRiskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
