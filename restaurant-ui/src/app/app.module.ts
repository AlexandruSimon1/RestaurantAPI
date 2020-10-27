import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdministratorComponent } from './administrator/administrator.component';
import { CommonModule } from '@angular/common';
import { UpdateAdministratorComponent } from './components/update-administrator/update-administrator.component';
import { FormsModule } from '@angular/forms';
import { CreateAdministratorComponent } from './components/create-administrator/create-administrator.component';

@NgModule({
  declarations: [
    AppComponent,
    AdministratorComponent,
    UpdateAdministratorComponent,
    CreateAdministratorComponent
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
