import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServersComponentComponent } from './servers-component.component';

describe('ServersComponentComponent', () => {
  let component: ServersComponentComponent;
  let fixture: ComponentFixture<ServersComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServersComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServersComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
