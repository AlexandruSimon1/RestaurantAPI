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
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
