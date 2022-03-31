import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckOutComponent } from '../list/check-out.component';
import { CheckOutDetailComponent } from '../detail/check-out-detail.component';
import { CheckOutUpdateComponent } from '../update/check-out-update.component';
import { CheckOutRoutingResolveService } from './check-out-routing-resolve.service';

const checkOutRoute: Routes = [
  {
    path: '',
    component: CheckOutComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckOutDetailComponent,
    resolve: {
      checkOut: CheckOutRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckOutUpdateComponent,
    resolve: {
      checkOut: CheckOutRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckOutUpdateComponent,
    resolve: {
      checkOut: CheckOutRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkOutRoute)],
  exports: [RouterModule],
})
export class CheckOutRoutingModule {}
