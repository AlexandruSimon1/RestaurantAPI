import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICheckOut, CheckOut } from '../check-out.model';

import { CheckOutService } from './check-out.service';

describe('CheckOut Service', () => {
  let service: CheckOutService;
  let httpMock: HttpTestingController;
  let elemDefault: ICheckOut;
  let expectedResult: ICheckOut | ICheckOut[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CheckOutService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      paymentType: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CheckOut', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CheckOut()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CheckOut', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          paymentType: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CheckOut', () => {
      const patchObject = Object.assign({}, new CheckOut());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CheckOut', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          paymentType: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CheckOut', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCheckOutToCollectionIfMissing', () => {
      it('should add a CheckOut to an empty array', () => {
        const checkOut: ICheckOut = { id: 123 };
        expectedResult = service.addCheckOutToCollectionIfMissing([], checkOut);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkOut);
      });

      it('should not add a CheckOut to an array that contains it', () => {
        const checkOut: ICheckOut = { id: 123 };
        const checkOutCollection: ICheckOut[] = [
          {
            ...checkOut,
          },
          { id: 456 },
        ];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, checkOut);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CheckOut to an array that doesn't contain it", () => {
        const checkOut: ICheckOut = { id: 123 };
        const checkOutCollection: ICheckOut[] = [{ id: 456 }];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, checkOut);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkOut);
      });

      it('should add only unique CheckOut to an array', () => {
        const checkOutArray: ICheckOut[] = [{ id: 123 }, { id: 456 }, { id: 23845 }];
        const checkOutCollection: ICheckOut[] = [{ id: 123 }];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, ...checkOutArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const checkOut: ICheckOut = { id: 123 };
        const checkOut2: ICheckOut = { id: 456 };
        expectedResult = service.addCheckOutToCollectionIfMissing([], checkOut, checkOut2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkOut);
        expect(expectedResult).toContain(checkOut2);
      });

      it('should accept null and undefined values', () => {
        const checkOut: ICheckOut = { id: 123 };
        expectedResult = service.addCheckOutToCollectionIfMissing([], null, checkOut, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkOut);
      });

      it('should return initial array if no CheckOut is added', () => {
        const checkOutCollection: ICheckOut[] = [{ id: 123 }];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, undefined, null);
        expect(expectedResult).toEqual(checkOutCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
