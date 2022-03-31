import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckOutDetailComponent } from './check-out-detail.component';

describe('CheckOut Management Detail Component', () => {
  let comp: CheckOutDetailComponent;
  let fixture: ComponentFixture<CheckOutDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckOutDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ checkOut: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CheckOutDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CheckOutDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load checkOut on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.checkOut).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
