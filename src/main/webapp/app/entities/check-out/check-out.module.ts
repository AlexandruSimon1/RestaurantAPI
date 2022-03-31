import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CheckOutComponent } from './list/check-out.component';
import { CheckOutDetailComponent } from './detail/check-out-detail.component';
import { CheckOutUpdateComponent } from './update/check-out-update.component';
import { CheckOutDeleteDialogComponent } from './delete/check-out-delete-dialog.component';
import { CheckOutRoutingModule } from './route/check-out-routing.module';

@NgModule({
  imports: [SharedModule, CheckOutRoutingModule],
  declarations: [CheckOutComponent, CheckOutDetailComponent, CheckOutUpdateComponent, CheckOutDeleteDialogComponent],
  entryComponents: [CheckOutDeleteDialogComponent],
})
export class CheckOutModule {}
