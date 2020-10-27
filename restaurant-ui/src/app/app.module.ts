import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdministratorsComponent } from './administrators/administrators.component';
import { WaitersComponent } from './waiters/waiters.component';
import { OrdersComponent } from './orders/orders.component';
import { MenusComponent } from './menus/menus.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { TablesComponent } from './tables/tables.component';
import { AdministratorsService } from './administrators.service';
import { WaitersService } from './waiters.service';
import { CheckoutService } from './checkout.service';
import { MenusService } from './menus.service';
import { TablesService } from './tables.service';
import { OrdersService } from './orders.service';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    AdministratorsComponent,
    WaitersComponent,
    OrdersComponent,
    MenusComponent,
    CheckoutComponent,
    TablesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [AdministratorsService, MenusService, WaitersService, CheckoutService, TablesService, OrdersService],

  bootstrap: [AppComponent]
})
export class AppModule { }
