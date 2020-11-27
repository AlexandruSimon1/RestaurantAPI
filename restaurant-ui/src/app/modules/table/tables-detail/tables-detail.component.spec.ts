import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TablesDetailComponent } from './tables-detail.component';

describe('TablesDetailComponent', () => {
  let component: TablesDetailComponent;
  let fixture: ComponentFixture<TablesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TablesDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TablesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
