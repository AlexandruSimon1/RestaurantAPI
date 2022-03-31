import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TableComponent } from '../list/table.component';
import { TableDetailComponent } from '../detail/table-detail.component';
import { TableUpdateComponent } from '../update/table-update.component';
import { TableRoutingResolveService } from './table-routing-resolve.service';

const tableRoute: Routes = [
  {
    path: '',
    component: TableComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TableDetailComponent,
    resolve: {
      table: TableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TableUpdateComponent,
    resolve: {
      table: TableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TableUpdateComponent,
    resolve: {
      table: TableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tableRoute)],
  exports: [RouterModule],
})
export class TableRoutingModule {}
