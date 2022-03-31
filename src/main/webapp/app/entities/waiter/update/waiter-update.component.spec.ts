import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WaiterService } from '../service/waiter.service';
import { IWaiter, Waiter } from '../waiter.model';

import { WaiterUpdateComponent } from './waiter-update.component';

describe('Waiter Management Update Component', () => {
  let comp: WaiterUpdateComponent;
  let fixture: ComponentFixture<WaiterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let waiterService: WaiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WaiterUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(WaiterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WaiterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    waiterService = TestBed.inject(WaiterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const waiter: IWaiter = { id: 456 };

      activatedRoute.data = of({ waiter });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(waiter));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Waiter>>();
      const waiter = { id: 123 };
      jest.spyOn(waiterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ waiter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: waiter }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(waiterService.update).toHaveBeenCalledWith(waiter);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Waiter>>();
      const waiter = new Waiter();
      jest.spyOn(waiterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ waiter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: waiter }));
      saveSubject.complete();

      // THEN
      expect(waiterService.create).toHaveBeenCalledWith(waiter);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Waiter>>();
      const waiter = { id: 123 };
      jest.spyOn(waiterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ waiter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(waiterService.update).toHaveBeenCalledWith(waiter);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
