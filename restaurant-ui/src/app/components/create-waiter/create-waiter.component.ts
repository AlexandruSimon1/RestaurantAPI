import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Waiter } from 'src/app/classes/waiter';
import { WaiterService } from 'src/app/services/waiter.service';

@Component({
  selector: 'app-create-waiter',
  templateUrl: './create-waiter.component.html',
  styleUrls: ['./create-waiter.component.css']
})
export class CreateWaiterComponent implements OnInit {

  waiter: Waiter = new Waiter();

  submitted = false;

  constructor(private waiterService: WaiterService,
    private router: Router) { }

  ngOnInit(): void {
  }

  newWaiter() {
    this.submitted = false;
    this.waiter = new Waiter();
  }

  save() {
    this.waiterService.createWaiter(this.waiter)
      .subscribe(data => {
        console.log(data);
        this.waiter = new Waiter();
        this.goToList();
      },
        error => console.log(error));
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  goToList() {
    this.router.navigate(['/waiters']);
  }
}
