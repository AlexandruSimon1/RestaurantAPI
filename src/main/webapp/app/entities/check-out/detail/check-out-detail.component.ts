import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckOut } from '../check-out.model';

@Component({
  selector: 'jhi-check-out-detail',
  templateUrl: './check-out-detail.component.html',
})
export class CheckOutDetailComponent implements OnInit {
  checkOut: ICheckOut | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkOut }) => {
      this.checkOut = checkOut;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
