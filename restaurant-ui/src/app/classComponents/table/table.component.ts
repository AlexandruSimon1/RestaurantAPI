import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Table } from 'src/app/classes/table';
import { TableService } from 'src/app/services/table.service';

@Component({
  selector: 'app-tables',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  tables: Table[]

  constructor(private tableService: TableService,
    private router: Router) { }

  ngOnInit(): void {
    this.getTablesData();
  }

  private getTablesData() {
    this.tableService.getAllTables()
      .subscribe(data => {
        this.tables = data;
      });
  }

  showTableOrders(order) {
    return order.orderNumber;
  }

  deleteTableById(id: number) {
    this.tableService.deleteTableById(id)
      .subscribe(data => {
        console.log(data);
        this.getTablesData;
      });
    error => console.log(error);
  }

  updateTableById(id: number) {
    this.router.navigate(['update', id]);
  }

}
