import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdministratorService } from '../service/administrator.service';
import { IAdministrator, Administrator } from '../administrator.model';

import { AdministratorUpdateComponent } from './administrator-update.component';

describe('Administrator Management Update Component', () => {
  let comp: AdministratorUpdateComponent;
  let fixture: ComponentFixture<AdministratorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let administratorService: AdministratorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdministratorUpdateComponent],
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
      .overrideTemplate(AdministratorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdministratorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    administratorService = TestBed.inject(AdministratorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const administrator: IAdministrator = { id: 456 };

      activatedRoute.data = of({ administrator });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(administrator));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Administrator>>();
      const administrator = { id: 123 };
      jest.spyOn(administratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrator }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(administratorService.update).toHaveBeenCalledWith(administrator);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Administrator>>();
      const administrator = new Administrator();
      jest.spyOn(administratorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrator }));
      saveSubject.complete();

      // THEN
      expect(administratorService.create).toHaveBeenCalledWith(administrator);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Administrator>>();
      const administrator = { id: 123 };
      jest.spyOn(administratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(administratorService.update).toHaveBeenCalledWith(administrator);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
