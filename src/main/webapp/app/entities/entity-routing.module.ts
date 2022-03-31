import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'administrator',
        data: { pageTitle: 'restaurantApiApp.administrator.home.title' },
        loadChildren: () => import('./administrator/administrator.module').then(m => m.AdministratorModule),
      },
      {
        path: 'waiter',
        data: { pageTitle: 'restaurantApiApp.waiter.home.title' },
        loadChildren: () => import('./waiter/waiter.module').then(m => m.WaiterModule),
      },
      {
        path: 'menu',
        data: { pageTitle: 'restaurantApiApp.menu.home.title' },
        loadChildren: () => import('./menu/menu.module').then(m => m.MenuModule),
      },
      {
        path: 'check-out',
        data: { pageTitle: 'restaurantApiApp.checkOut.home.title' },
        loadChildren: () => import('./check-out/check-out.module').then(m => m.CheckOutModule),
      },
      {
        path: 'table',
        data: { pageTitle: 'restaurantApiApp.table.home.title' },
        loadChildren: () => import('./table/table.module').then(m => m.TableModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'restaurantApiApp.order.home.title' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
