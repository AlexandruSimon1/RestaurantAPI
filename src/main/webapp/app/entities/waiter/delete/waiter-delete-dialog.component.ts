import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWaiter } from '../waiter.model';
import { WaiterService } from '../service/waiter.service';

@Component({
  templateUrl: './waiter-delete-dialog.component.html',
})
export class WaiterDeleteDialogComponent {
  waiter?: IWaiter;

  constructor(protected waiterService: WaiterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.waiterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
