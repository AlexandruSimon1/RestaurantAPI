import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITable, Table } from '../table.model';
import { TableService } from '../service/table.service';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';

@Component({
  selector: 'jhi-table-update',
  templateUrl: './table-update.component.html',
})
export class TableUpdateComponent implements OnInit {
  isSaving = false;

  ordersCollection: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    number: [],
    order: [],
  });

  constructor(
    protected tableService: TableService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ table }) => {
      this.updateForm(table);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const table = this.createFromForm();
    if (table.id !== undefined) {
      this.subscribeToSaveResponse(this.tableService.update(table));
    } else {
      this.subscribeToSaveResponse(this.tableService.create(table));
    }
  }

  trackOrderById(index: number, item: IOrder): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITable>>): void {
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

  protected updateForm(table: ITable): void {
    this.editForm.patchValue({
      id: table.id,
      number: table.number,
      order: table.order,
    });

    this.ordersCollection = this.orderService.addOrderToCollectionIfMissing(this.ordersCollection, table.order);
  }

  protected loadRelationshipsOptions(): void {
    this.orderService
      .query({ 'tableId.specified': 'false' })
      .pipe(map((res: HttpResponse<IOrder[]>) => res.body ?? []))
      .pipe(map((orders: IOrder[]) => this.orderService.addOrderToCollectionIfMissing(orders, this.editForm.get('order')!.value)))
      .subscribe((orders: IOrder[]) => (this.ordersCollection = orders));
  }

  protected createFromForm(): ITable {
    return {
      ...new Table(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      order: this.editForm.get(['order'])!.value,
    };
  }
}
