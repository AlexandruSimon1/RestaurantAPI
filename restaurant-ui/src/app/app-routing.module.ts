import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdministratorComponent } from './modules/administrator/administrator-main/administrator.component';
import { CheckoutComponent } from './modules/checkout/checkout-main/checkout.component';
import { MenusComponent } from './modules/menus/menus-main/menus.component';
import { OrderComponent } from './modules/order/order-main/order.component';
import { TableComponent } from './modules/table/table-main/table.component';
import { WaiterComponent } from './modules/waiter/waiter-main/waiter.component';
import { CreateAdministratorComponent } from './modules/administrator/create-administrator/create-administrator.component';
import { CreateWaiterComponent } from './modules/waiter/create-waiter/create-waiter.component';
import { UpdateAdministratorComponent } from './modules/administrator/update-administrator/update-administrator.component';
import { UpdateWaiterComponent } from './modules/waiter/update-waiter/update-waiter.component';
import { AdministratorDetailComponent } from './modules/administrator/administrator-detail/administrator-detail.component';

const routes: Routes = [
  
  { path: '', redirectTo: 'administrator', pathMatch: 'full' },
  { path: '', redirectTo: 'administrator', pathMatch: 'full' },
  { path: 'administrators', component: AdministratorComponent },
  { path: 'update/:id', component: UpdateAdministratorComponent },
  { path: 'createAdministrator', component: CreateAdministratorComponent },
  { path: 'waiters', component: WaiterComponent },
  { path: 'update/:id', component: UpdateWaiterComponent },
  { path: 'createWaiter', component: CreateWaiterComponent },
  { path: 'menus', component: MenusComponent },
  { path: 'orders', component: OrderComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'tables', component: TableComponent },
  { path: 'details/:id', component: AdministratorDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
