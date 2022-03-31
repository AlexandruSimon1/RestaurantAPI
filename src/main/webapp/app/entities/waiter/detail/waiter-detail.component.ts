import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWaiter } from '../waiter.model';

@Component({
  selector: 'jhi-waiter-detail',
  templateUrl: './waiter-detail.component.html',
})
export class WaiterDetailComponent implements OnInit {
  waiter: IWaiter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ waiter }) => {
      this.waiter = waiter;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
