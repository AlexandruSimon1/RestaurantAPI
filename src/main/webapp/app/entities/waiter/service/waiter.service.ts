import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWaiter, getWaiterIdentifier } from '../waiter.model';

export type EntityResponseType = HttpResponse<IWaiter>;
export type EntityArrayResponseType = HttpResponse<IWaiter[]>;

@Injectable({ providedIn: 'root' })
export class WaiterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/waiters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(waiter: IWaiter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(waiter);
    return this.http
      .post<IWaiter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(waiter: IWaiter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(waiter);
    return this.http
      .put<IWaiter>(`${this.resourceUrl}/${getWaiterIdentifier(waiter) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(waiter: IWaiter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(waiter);
    return this.http
      .patch<IWaiter>(`${this.resourceUrl}/${getWaiterIdentifier(waiter) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWaiter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWaiter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWaiterToCollectionIfMissing(waiterCollection: IWaiter[], ...waitersToCheck: (IWaiter | null | undefined)[]): IWaiter[] {
    const waiters: IWaiter[] = waitersToCheck.filter(isPresent);
    if (waiters.length > 0) {
      const waiterCollectionIdentifiers = waiterCollection.map(waiterItem => getWaiterIdentifier(waiterItem)!);
      const waitersToAdd = waiters.filter(waiterItem => {
        const waiterIdentifier = getWaiterIdentifier(waiterItem);
        if (waiterIdentifier == null || waiterCollectionIdentifiers.includes(waiterIdentifier)) {
          return false;
        }
        waiterCollectionIdentifiers.push(waiterIdentifier);
        return true;
      });
      return [...waitersToAdd, ...waiterCollection];
    }
    return waiterCollection;
  }

  protected convertDateFromClient(waiter: IWaiter): IWaiter {
    return Object.assign({}, waiter, {
      dateOfBirth: waiter.dateOfBirth?.isValid() ? waiter.dateOfBirth.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? dayjs(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((waiter: IWaiter) => {
        waiter.dateOfBirth = waiter.dateOfBirth ? dayjs(waiter.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
