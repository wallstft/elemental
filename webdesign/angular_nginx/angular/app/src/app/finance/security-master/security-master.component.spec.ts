import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityMasterComponent } from './security-master.component';

describe('SecurityMasterComponent', () => {
  let component: SecurityMasterComponent;
  let fixture: ComponentFixture<SecurityMasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SecurityMasterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SecurityMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
