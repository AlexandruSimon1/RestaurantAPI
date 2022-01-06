import { CreateUserComponent } from './modules/create-user/create-user.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
import { WaiterDetailComponent } from './modules/waiter/waiter-detail/waiter-detail.component';
import { OrderDetailComponent } from './modules/order/order-detail/order-detail.component';
import { CheckoutDetailComponent } from './modules/checkout/checkout-detail/checkout-detail.component';
import { MenuDetailComponent } from './modules/menus/menu-detail/menu-detail.component';
import { TablesDetailComponent } from './modules/table/tables-detail/tables-detail.component';
import { UpdateTableComponent } from './modules/table/update-table/update-table.component';
import { UpdateOrderComponent } from './modules/order/update-order/update-order.component';
import { UpdateMenusComponent } from './modules/menus/update-menus/update-menus.component';
import { UpdateCheckoutComponent } from './modules/checkout/update-checkout/update-checkout.component';
import { CreateCheckoutComponent } from './modules/checkout/create-checkout/create-checkout.component';
import { CreateMenusComponent } from './modules/menus/create-menus/create-menus.component';
import { CreateOrderComponent } from './modules/order/create-order/create-order.component';
import { CreateTablesComponent } from './modules/table/create-tables/create-tables.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './modules/home/home.component';

import { MatTableModule } from '@angular/material/table';
import { MatSidenavModule } from '@angular/material/sidenav';
import { PersonalModulesModule } from './modules/personal-modules.module';

@NgModule({
  declarations: [
    AppComponent,
    AdministratorComponent,
    AdministratorDetailComponent,
    UpdateAdministratorComponent,
    CreateAdministratorComponent,
    WaiterComponent,
    MenusComponent,
    OrderComponent,
    TableComponent,
    CheckoutComponent,
    CreateWaiterComponent,
    UpdateWaiterComponent,
    WaiterDetailComponent,
    OrderDetailComponent,
    CheckoutDetailComponent,
    MenuDetailComponent,
    TablesDetailComponent,
    UpdateTableComponent,
    UpdateOrderComponent,
    UpdateMenusComponent,
    UpdateCheckoutComponent,
    CreateCheckoutComponent,
    CreateMenusComponent,
    CreateOrderComponent,
    CreateTablesComponent,
    HomeComponent,
    CreateUserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatTableModule,
    PersonalModulesModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
