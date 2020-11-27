import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Checkout } from 'src/app/models/checkout';
import { CheckoutService } from 'src/app/services/checkout.service';

@Component({
  selector: 'app-checkout-main',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  checkout: Checkout[];

  constructor(private checkoutService: CheckoutService,
    private router: Router) { }

  ngOnInit(): void {
    this.getCheckoutData();
  }

  private getCheckoutData() {
    this.checkoutService.getAllCheckout().
      subscribe(data => {
        this.checkout = data;
      });
  }

  showCheckoutOrders(order) {
    return order.orderNumber;
  }

  deleteCheckoutById(id: number) {
    this.checkoutService.deleteCheckoutsByID(id)
      .subscribe(data => {
        console.log(data);
        this.getCheckoutData
      });
    error => console.log(error);
  }

  updateCheckoutById(id: number) {
    this.router.navigate(['update', id]);
  }

}
