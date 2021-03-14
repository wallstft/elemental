import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayoutTemplateComponent } from './layout-template.component';

describe('LayoutTemplateComponent', () => {
  let component: LayoutTemplateComponent;
  let fixture: ComponentFixture<LayoutTemplateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LayoutTemplateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LayoutTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
