import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWaiter, Waiter } from '../waiter.model';
import { WaiterService } from '../service/waiter.service';

@Injectable({ providedIn: 'root' })
export class WaiterRoutingResolveService implements Resolve<IWaiter> {
  constructor(protected service: WaiterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWaiter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((waiter: HttpResponse<Waiter>) => {
          if (waiter.body) {
            return of(waiter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Waiter());
  }
}
