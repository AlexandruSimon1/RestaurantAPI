import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCheckoutComponent } from './update-checkout.component';

describe('UpdateCheckoutComponent', () => {
  let component: UpdateCheckoutComponent;
  let fixture: ComponentFixture<UpdateCheckoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCheckoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateCheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
