import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdministratorComponent } from './administrator/administrator.component';
import { UpdateAdministratorComponent } from './components/update-administrator/update-administrator.component';

const routes: Routes = [
  { path: '', redirectTo: 'administrator', pathMatch: 'full' },
  { path: 'administrators', component: AdministratorComponent },
  { path: 'update/:id', component: UpdateAdministratorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
