import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IWaiter, Waiter } from '../waiter.model';

import { WaiterService } from './waiter.service';

describe('Waiter Service', () => {
  let service: WaiterService;
  let httpMock: HttpTestingController;
  let elemDefault: IWaiter;
  let expectedResult: IWaiter | IWaiter[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WaiterService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      dateOfBirth: currentDate,
      address: 'AAAAAAA',
      phoneNumber: 0,
      email: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateOfBirth: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Waiter', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateOfBirth: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.create(new Waiter()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Waiter', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          dateOfBirth: currentDate.format(DATE_FORMAT),
          address: 'BBBBBB',
          phoneNumber: 1,
          email: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Waiter', () => {
      const patchObject = Object.assign(
        {
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          phoneNumber: 1,
        },
        new Waiter()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Waiter', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          dateOfBirth: currentDate.format(DATE_FORMAT),
          address: 'BBBBBB',
          phoneNumber: 1,
          email: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Waiter', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addWaiterToCollectionIfMissing', () => {
      it('should add a Waiter to an empty array', () => {
        const waiter: IWaiter = { id: 123 };
        expectedResult = service.addWaiterToCollectionIfMissing([], waiter);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(waiter);
      });

      it('should not add a Waiter to an array that contains it', () => {
        const waiter: IWaiter = { id: 123 };
        const waiterCollection: IWaiter[] = [
          {
            ...waiter,
          },
          { id: 456 },
        ];
        expectedResult = service.addWaiterToCollectionIfMissing(waiterCollection, waiter);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Waiter to an array that doesn't contain it", () => {
        const waiter: IWaiter = { id: 123 };
        const waiterCollection: IWaiter[] = [{ id: 456 }];
        expectedResult = service.addWaiterToCollectionIfMissing(waiterCollection, waiter);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(waiter);
      });

      it('should add only unique Waiter to an array', () => {
        const waiterArray: IWaiter[] = [{ id: 123 }, { id: 456 }, { id: 3615 }];
        const waiterCollection: IWaiter[] = [{ id: 123 }];
        expectedResult = service.addWaiterToCollectionIfMissing(waiterCollection, ...waiterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const waiter: IWaiter = { id: 123 };
        const waiter2: IWaiter = { id: 456 };
        expectedResult = service.addWaiterToCollectionIfMissing([], waiter, waiter2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(waiter);
        expect(expectedResult).toContain(waiter2);
      });

      it('should accept null and undefined values', () => {
        const waiter: IWaiter = { id: 123 };
        expectedResult = service.addWaiterToCollectionIfMissing([], null, waiter, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(waiter);
      });

      it('should return initial array if no Waiter is added', () => {
        const waiterCollection: IWaiter[] = [{ id: 123 }];
        expectedResult = service.addWaiterToCollectionIfMissing(waiterCollection, undefined, null);
        expect(expectedResult).toEqual(waiterCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
