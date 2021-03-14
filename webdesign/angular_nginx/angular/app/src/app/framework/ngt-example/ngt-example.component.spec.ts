import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgtExampleComponent } from './ngt-example.component';

describe('NgtExampleComponent', () => {
  let component: NgtExampleComponent;
  let fixture: ComponentFixture<NgtExampleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NgtExampleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NgtExampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
