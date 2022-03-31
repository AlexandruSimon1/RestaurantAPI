import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITable, Table } from '../table.model';

import { TableService } from './table.service';

describe('Table Service', () => {
  let service: TableService;
  let httpMock: HttpTestingController;
  let elemDefault: ITable;
  let expectedResult: ITable | ITable[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TableService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      number: 0,
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

    it('should create a Table', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Table()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Table', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          number: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Table', () => {
      const patchObject = Object.assign({}, new Table());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Table', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          number: 1,
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

    it('should delete a Table', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTableToCollectionIfMissing', () => {
      it('should add a Table to an empty array', () => {
        const table: ITable = { id: 123 };
        expectedResult = service.addTableToCollectionIfMissing([], table);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(table);
      });

      it('should not add a Table to an array that contains it', () => {
        const table: ITable = { id: 123 };
        const tableCollection: ITable[] = [
          {
            ...table,
          },
          { id: 456 },
        ];
        expectedResult = service.addTableToCollectionIfMissing(tableCollection, table);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Table to an array that doesn't contain it", () => {
        const table: ITable = { id: 123 };
        const tableCollection: ITable[] = [{ id: 456 }];
        expectedResult = service.addTableToCollectionIfMissing(tableCollection, table);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(table);
      });

      it('should add only unique Table to an array', () => {
        const tableArray: ITable[] = [{ id: 123 }, { id: 456 }, { id: 81287 }];
        const tableCollection: ITable[] = [{ id: 123 }];
        expectedResult = service.addTableToCollectionIfMissing(tableCollection, ...tableArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const table: ITable = { id: 123 };
        const table2: ITable = { id: 456 };
        expectedResult = service.addTableToCollectionIfMissing([], table, table2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(table);
        expect(expectedResult).toContain(table2);
      });

      it('should accept null and undefined values', () => {
        const table: ITable = { id: 123 };
        expectedResult = service.addTableToCollectionIfMissing([], null, table, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(table);
      });

      it('should return initial array if no Table is added', () => {
        const tableCollection: ITable[] = [{ id: 123 }];
        expectedResult = service.addTableToCollectionIfMissing(tableCollection, undefined, null);
        expect(expectedResult).toEqual(tableCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
