import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeoTableComponent } from './neo-table.component';

describe('NeoTableComponent', () => {
  let component: NeoTableComponent;
  let fixture: ComponentFixture<NeoTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NeoTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NeoTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
