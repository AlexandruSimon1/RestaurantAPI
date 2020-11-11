import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdministratorComponent } from './classComponents/administrator/administrator.component';
import { CheckoutComponent } from './classComponents/checkout/checkout.component';
import { MenusComponent } from './classComponents/menus/menus.component';
import { OrderComponent } from './classComponents/order/order.component';
import { TableComponent } from './classComponents/table/table.component';
import { WaiterComponent } from './classComponents/waiter/waiter.component';
import { CreateAdministratorComponent } from './components/create-administrator/create-administrator.component';
import { CreateWaiterComponent } from './components/create-waiter/create-waiter.component';
import { UpdateAdministratorComponent } from './components/update-administrator/update-administrator.component';
import { UpdateWaiterComponent } from './components/update-waiter/update-waiter.component';

const routes: Routes = [
  { path: '', redirectTo: 'waiter', pathMatch: 'full' },
  { path: '', redirectTo: 'administrator', pathMatch: 'full' },
  { path: '', redirectTo: 'menu', pathMatch: 'full' },
  { path: '', redirectTo: 'order', pathMatch: 'full' },
  { path: '', redirectTo: 'checkout', pathMatch: 'full' },
  { path: '', redirectTo: 'table', pathMatch: 'full' },
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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
