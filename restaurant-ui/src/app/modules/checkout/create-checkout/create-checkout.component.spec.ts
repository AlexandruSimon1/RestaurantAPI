import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCheckoutComponent } from './create-checkout.component';

describe('CreateCheckoutComponent', () => {
  let component: CreateCheckoutComponent;
  let fixture: ComponentFixture<CreateCheckoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCheckoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateCheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
