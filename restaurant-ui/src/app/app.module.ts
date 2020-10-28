import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { UpdateAdministratorComponent } from './components/update-administrator/update-administrator.component';
import { FormsModule } from '@angular/forms';
import { CreateAdministratorComponent } from './components/create-administrator/create-administrator.component';
import { AdministratorComponent } from './classComponents/administrator/administrator.component';
import { WaiterComponent } from './classComponents/waiter/waiter.component';
import { MenusComponent } from './classComponents/menus/menus.component';
import { OrderComponent } from './classComponents/order/order.component';
import { TableComponent } from './classComponents/table/table.component';
import { CheckoutComponent } from './classComponents/checkout/checkout.component';
import { CreateWaiterComponent } from './components/create-waiter/create-waiter.component';
import { UpdateWaiterComponent } from './components/update-waiter/update-waiter.component';

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
