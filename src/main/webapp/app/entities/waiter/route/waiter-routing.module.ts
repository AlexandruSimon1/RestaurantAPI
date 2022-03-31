import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WaiterComponent } from '../list/waiter.component';
import { WaiterDetailComponent } from '../detail/waiter-detail.component';
import { WaiterUpdateComponent } from '../update/waiter-update.component';
import { WaiterRoutingResolveService } from './waiter-routing-resolve.service';

const waiterRoute: Routes = [
  {
    path: '',
    component: WaiterComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WaiterDetailComponent,
    resolve: {
      waiter: WaiterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WaiterUpdateComponent,
    resolve: {
      waiter: WaiterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WaiterUpdateComponent,
    resolve: {
      waiter: WaiterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(waiterRoute)],
  exports: [RouterModule],
})
export class WaiterRoutingModule {}
