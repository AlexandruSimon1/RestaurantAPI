import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterDetailComponent } from './waiter-detail.component';

describe('WaiterDetailComponent', () => {
  let component: WaiterDetailComponent;
  let fixture: ComponentFixture<WaiterDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaiterDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WaiterDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
