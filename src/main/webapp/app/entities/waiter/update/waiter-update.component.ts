import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IWaiter, Waiter } from '../waiter.model';
import { WaiterService } from '../service/waiter.service';

@Component({
  selector: 'jhi-waiter-update',
  templateUrl: './waiter-update.component.html',
})
export class WaiterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    dateOfBirth: [],
    address: [],
    phoneNumber: [],
    email: [],
  });

  constructor(protected waiterService: WaiterService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ waiter }) => {
      this.updateForm(waiter);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const waiter = this.createFromForm();
    if (waiter.id !== undefined) {
      this.subscribeToSaveResponse(this.waiterService.update(waiter));
    } else {
      this.subscribeToSaveResponse(this.waiterService.create(waiter));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWaiter>>): void {
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

  protected updateForm(waiter: IWaiter): void {
    this.editForm.patchValue({
      id: waiter.id,
      firstName: waiter.firstName,
      lastName: waiter.lastName,
      dateOfBirth: waiter.dateOfBirth,
      address: waiter.address,
      phoneNumber: waiter.phoneNumber,
      email: waiter.email,
    });
  }

  protected createFromForm(): IWaiter {
    return {
      ...new Waiter(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      address: this.editForm.get(['address'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      email: this.editForm.get(['email'])!.value,
    };
  }
}
