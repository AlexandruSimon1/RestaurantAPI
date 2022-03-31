import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICheckOut, getCheckOutIdentifier } from '../check-out.model';

export type EntityResponseType = HttpResponse<ICheckOut>;
export type EntityArrayResponseType = HttpResponse<ICheckOut[]>;

@Injectable({ providedIn: 'root' })
export class CheckOutService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/check-outs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(checkOut: ICheckOut): Observable<EntityResponseType> {
    return this.http.post<ICheckOut>(this.resourceUrl, checkOut, { observe: 'response' });
  }

  update(checkOut: ICheckOut): Observable<EntityResponseType> {
    return this.http.put<ICheckOut>(`${this.resourceUrl}/${getCheckOutIdentifier(checkOut) as number}`, checkOut, { observe: 'response' });
  }

  partialUpdate(checkOut: ICheckOut): Observable<EntityResponseType> {
    return this.http.patch<ICheckOut>(`${this.resourceUrl}/${getCheckOutIdentifier(checkOut) as number}`, checkOut, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckOut>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckOut[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCheckOutToCollectionIfMissing(checkOutCollection: ICheckOut[], ...checkOutsToCheck: (ICheckOut | null | undefined)[]): ICheckOut[] {
    const checkOuts: ICheckOut[] = checkOutsToCheck.filter(isPresent);
    if (checkOuts.length > 0) {
      const checkOutCollectionIdentifiers = checkOutCollection.map(checkOutItem => getCheckOutIdentifier(checkOutItem)!);
      const checkOutsToAdd = checkOuts.filter(checkOutItem => {
        const checkOutIdentifier = getCheckOutIdentifier(checkOutItem);
        if (checkOutIdentifier == null || checkOutCollectionIdentifiers.includes(checkOutIdentifier)) {
          return false;
        }
        checkOutCollectionIdentifiers.push(checkOutIdentifier);
        return true;
      });
      return [...checkOutsToAdd, ...checkOutCollection];
    }
    return checkOutCollection;
  }
}
