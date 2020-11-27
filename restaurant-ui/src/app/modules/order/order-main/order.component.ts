import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from 'src/app/models/order';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  orders: Order[];

  constructor(private orderService: OrderService,
    private router: Router) { }

  ngOnInit(): void {
    this.getOrdersData();
  }

  private getOrdersData() {
    this.orderService.getOrders()
      .subscribe(data => {
        this.orders = data;
      });
  }

  showOrderMenus(menus) {
    return menus.map(
      menu => menu.categoryType + '-' + menu.name + '-' + menu.price)
      .join(', ');
  }

  deleteOrderById(id: number) {
    this.orderService.deleteOrdersById(id)
      .subscribe(data => {
        console.log(data);
        this.getOrdersData();
      },
        error => console.log(error));
  }

  updateOrderById(id: number) {
    this.router.navigate(['update', id]);
  }
}
