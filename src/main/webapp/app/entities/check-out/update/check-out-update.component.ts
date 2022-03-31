import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICheckOut, CheckOut } from '../check-out.model';
import { CheckOutService } from '../service/check-out.service';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';

@Component({
  selector: 'jhi-check-out-update',
  templateUrl: './check-out-update.component.html',
})
export class CheckOutUpdateComponent implements OnInit {
  isSaving = false;

  ordersCollection: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    paymentType: [],
    order: [],
  });

  constructor(
    protected checkOutService: CheckOutService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkOut }) => {
      this.updateForm(checkOut);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkOut = this.createFromForm();
    if (checkOut.id !== undefined) {
      this.subscribeToSaveResponse(this.checkOutService.update(checkOut));
    } else {
      this.subscribeToSaveResponse(this.checkOutService.create(checkOut));
    }
  }

  trackOrderById(index: number, item: IOrder): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckOut>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(checkOut: ICheckOut): void {
    this.editForm.patchValue({
      id: checkOut.id,
      paymentType: checkOut.paymentType,
      order: checkOut.order,
    });

    this.ordersCollection = this.orderService.addOrderToCollectionIfMissing(this.ordersCollection, checkOut.order);
  }

  protected loadRelationshipsOptions(): void {
    this.orderService
      .query({ 'checkOutId.specified': 'false' })
      .pipe(map((res: HttpResponse<IOrder[]>) => res.body ?? []))
      .pipe(map((orders: IOrder[]) => this.orderService.addOrderToCollectionIfMissing(orders, this.editForm.get('order')!.value)))
      .subscribe((orders: IOrder[]) => (this.ordersCollection = orders));
  }

  protected createFromForm(): ICheckOut {
    return {
      ...new CheckOut(),
      id: this.editForm.get(['id'])!.value,
      paymentType: this.editForm.get(['paymentType'])!.value,
      order: this.editForm.get(['order'])!.value,
    };
  }
}
