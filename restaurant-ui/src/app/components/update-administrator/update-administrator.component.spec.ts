import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAdministratorComponent } from './update-administrator.component';

describe('UpdateAdministratorComponent', () => {
  let component: UpdateAdministratorComponent;
  let fixture: ComponentFixture<UpdateAdministratorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateAdministratorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
