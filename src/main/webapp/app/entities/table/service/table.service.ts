import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITable, getTableIdentifier } from '../table.model';

export type EntityResponseType = HttpResponse<ITable>;
export type EntityArrayResponseType = HttpResponse<ITable[]>;

@Injectable({ providedIn: 'root' })
export class TableService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tables');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(table: ITable): Observable<EntityResponseType> {
    return this.http.post<ITable>(this.resourceUrl, table, { observe: 'response' });
  }

  update(table: ITable): Observable<EntityResponseType> {
    return this.http.put<ITable>(`${this.resourceUrl}/${getTableIdentifier(table) as number}`, table, { observe: 'response' });
  }

  partialUpdate(table: ITable): Observable<EntityResponseType> {
    return this.http.patch<ITable>(`${this.resourceUrl}/${getTableIdentifier(table) as number}`, table, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTableToCollectionIfMissing(tableCollection: ITable[], ...tablesToCheck: (ITable | null | undefined)[]): ITable[] {
    const tables: ITable[] = tablesToCheck.filter(isPresent);
    if (tables.length > 0) {
      const tableCollectionIdentifiers = tableCollection.map(tableItem => getTableIdentifier(tableItem)!);
      const tablesToAdd = tables.filter(tableItem => {
        const tableIdentifier = getTableIdentifier(tableItem);
        if (tableIdentifier == null || tableCollectionIdentifiers.includes(tableIdentifier)) {
          return false;
        }
        tableCollectionIdentifiers.push(tableIdentifier);
        return true;
      });
      return [...tablesToAdd, ...tableCollection];
    }
    return tableCollection;
  }
}
