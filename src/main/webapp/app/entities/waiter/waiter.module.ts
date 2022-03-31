import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WaiterComponent } from './list/waiter.component';
import { WaiterDetailComponent } from './detail/waiter-detail.component';
import { WaiterUpdateComponent } from './update/waiter-update.component';
import { WaiterDeleteDialogComponent } from './delete/waiter-delete-dialog.component';
import { WaiterRoutingModule } from './route/waiter-routing.module';

@NgModule({
  imports: [SharedModule, WaiterRoutingModule],
  declarations: [WaiterComponent, WaiterDetailComponent, WaiterUpdateComponent, WaiterDeleteDialogComponent],
  entryComponents: [WaiterDeleteDialogComponent],
})
export class WaiterModule {}
