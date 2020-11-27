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

@NgModule({
  declarations: [
    AppComponent,
    AdministratorComponent,
    UpdateAdministratorComponent,
    CreateAdministratorComponent,
    WaiterComponent,
    MenusComponent,
    OrderComponent,
    TableComponent,
    CheckoutComponent,
    CreateWaiterComponent,
    UpdateWaiterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
