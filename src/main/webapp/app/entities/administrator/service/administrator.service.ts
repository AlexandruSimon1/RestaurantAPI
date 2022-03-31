import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdministrator, getAdministratorIdentifier } from '../administrator.model';

export type EntityResponseType = HttpResponse<IAdministrator>;
export type EntityArrayResponseType = HttpResponse<IAdministrator[]>;

@Injectable({ providedIn: 'root' })
export class AdministratorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/administrators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(administrator: IAdministrator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(administrator);
    return this.http
      .post<IAdministrator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(administrator: IAdministrator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(administrator);
    return this.http
      .put<IAdministrator>(`${this.resourceUrl}/${getAdministratorIdentifier(administrator) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(administrator: IAdministrator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(administrator);
    return this.http
      .patch<IAdministrator>(`${this.resourceUrl}/${getAdministratorIdentifier(administrator) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAdministrator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAdministrator[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdministratorToCollectionIfMissing(
    administratorCollection: IAdministrator[],
    ...administratorsToCheck: (IAdministrator | null | undefined)[]
  ): IAdministrator[] {
    const administrators: IAdministrator[] = administratorsToCheck.filter(isPresent);
    if (administrators.length > 0) {
      const administratorCollectionIdentifiers = administratorCollection.map(
        administratorItem => getAdministratorIdentifier(administratorItem)!
      );
      const administratorsToAdd = administrators.filter(administratorItem => {
        const administratorIdentifier = getAdministratorIdentifier(administratorItem);
        if (administratorIdentifier == null || administratorCollectionIdentifiers.includes(administratorIdentifier)) {
          return false;
        }
        administratorCollectionIdentifiers.push(administratorIdentifier);
        return true;
      });
      return [...administratorsToAdd, ...administratorCollection];
    }
    return administratorCollection;
  }

  protected convertDateFromClient(administrator: IAdministrator): IAdministrator {
    return Object.assign({}, administrator, {
      dateOfBirth: administrator.dateOfBirth?.isValid() ? administrator.dateOfBirth.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((administrator: IAdministrator) => {
        administrator.dateOfBirth = administrator.dateOfBirth ? dayjs(administrator.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
