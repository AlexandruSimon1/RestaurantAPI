import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Menus } from 'src/app/classes/menus';
import { MenusService } from 'src/app/services/menus.service';

@Component({
  selector: 'app-menus',
  templateUrl: './menus.component.html',
  styleUrls: ['./menus.component.css']
})
export class MenusComponent implements OnInit {

  menus: Menus[];

  constructor(private menusService: MenusService,
    private router: Router) { }

  ngOnInit(): void {
    this.reloadData();
  }
  private reloadData() {
    this.menusService.getMenus()
      .subscribe(data => {
        this.menus = data;
      });
  }

  deleteMenuById(id: number) {
    this.menusService.deleteMenusById(id)
      .subscribe(data => {
        console.log(data);
        this.reloadData();
      },
        error => console.log(error));
  }

  updateMenuById(id: number) {
    this.router.navigate(['update', id]);
  }
}
