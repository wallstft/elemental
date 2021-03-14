import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatSideNavbarComponent } from './mat-side-navbar.component';

describe('MatSideNavbarComponent', () => {
  let component: MatSideNavbarComponent;
  let fixture: ComponentFixture<MatSideNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatSideNavbarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MatSideNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
