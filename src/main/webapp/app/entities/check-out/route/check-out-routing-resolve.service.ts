import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckOut, CheckOut } from '../check-out.model';
import { CheckOutService } from '../service/check-out.service';

@Injectable({ providedIn: 'root' })
export class CheckOutRoutingResolveService implements Resolve<ICheckOut> {
  constructor(protected service: CheckOutService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckOut> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkOut: HttpResponse<CheckOut>) => {
          if (checkOut.body) {
            return of(checkOut.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckOut());
  }
}
