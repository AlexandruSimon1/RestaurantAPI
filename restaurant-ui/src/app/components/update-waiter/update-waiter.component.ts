import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Waiter } from 'src/app/classes/waiter';
import { WaiterService } from 'src/app/services/waiter.service';

@Component({
  selector: 'app-update-waiter',
  templateUrl: './update-waiter.component.html',
  styleUrls: ['./update-waiter.component.css']
})
export class UpdateWaiterComponent implements OnInit {
  id: number;
  waiter: Waiter;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private waiterService: WaiterService) { }

  ngOnInit(): void {
    this.waiter = new Waiter();
    this.id = this.route.params['id'];

    this.waiterService.getWaitersById(this.id)
      .subscribe(data => {
        console.log(data);
        this.waiter = data;
      },
        error => console.log(error));
  }

  updateWaiterById() {
    this.waiterService.updateWaiterById(this.id, this.waiter)
      .subscribe(data => {
        console.log(data);
        this.waiter = new Waiter();
        this.goToList();
      },
        error => console.log(error));
  }

  onSubmit() {
    this.updateWaiterById();
  }

  goToList() {
    this.router.navigate([`/waiters`]);
  }
}
