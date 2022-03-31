import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WaiterDetailComponent } from './waiter-detail.component';

describe('Waiter Management Detail Component', () => {
  let comp: WaiterDetailComponent;
  let fixture: ComponentFixture<WaiterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WaiterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ waiter: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WaiterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WaiterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load waiter on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.waiter).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
