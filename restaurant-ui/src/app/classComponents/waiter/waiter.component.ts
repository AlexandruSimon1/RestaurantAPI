import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Waiter } from 'src/app/classes/waiter';
import { WaiterService } from 'src/app/services/waiter.service';

@Component({
  selector: 'app-waiter',
  templateUrl: './waiter.component.html',
  styleUrls: ['./waiter.component.css']
})
export class WaiterComponent implements OnInit {

  waiters: Waiter[];

  constructor(private waiterService: WaiterService,
    private router: Router) { }

  ngOnInit(): void {
    this.reloadData();
  }
  
  private reloadData() {
    this.waiterService.getWaiters()
      .subscribe(data => {
        this.waiters = data;
      });
  }

  deleteWaiterById(id: number) {
    this.waiterService.deleteWaitersById(id)
      .subscribe(data => {
        console.log(data);
        this.reloadData();
      });
  }

  updateWaiterById(id: number) {
    this.router.navigate(['update', id]);
  }
}