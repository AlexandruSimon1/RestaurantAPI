import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateWaiterComponent } from './update-waiter.component';

describe('UpdateWaiterComponent', () => {
  let component: UpdateWaiterComponent;
  let fixture: ComponentFixture<UpdateWaiterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateWaiterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateWaiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
