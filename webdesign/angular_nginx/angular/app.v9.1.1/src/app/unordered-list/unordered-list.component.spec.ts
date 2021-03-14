import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnorderedListComponent } from './unordered-list.component';

describe('UnorderedListComponent', () => {
  let component: UnorderedListComponent;
  let fixture: ComponentFixture<UnorderedListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnorderedListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnorderedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
