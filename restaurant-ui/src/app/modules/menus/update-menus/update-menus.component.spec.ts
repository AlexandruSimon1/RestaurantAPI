import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateMenusComponent } from './update-menus.component';

describe('UpdateMenusComponent', () => {
  let component: UpdateMenusComponent;
  let fixture: ComponentFixture<UpdateMenusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateMenusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateMenusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
