import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Administrator } from 'src/app/classes/administrator';
import { AdministratorService } from 'src/app/services/administrator.service';



@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css']
})

export class AdministratorComponent implements OnInit {

  administrators: Administrator[];

  constructor(private router: Router,
    private administratorService: AdministratorService) { }


  ngOnInit(): void {
    this.getAdministratorsData();
  }

  private getAdministratorsData() {
    this.administratorService.getAdministrators()
      .subscribe(data => {
        this.administrators = data;
      });
  }


  deleteAdministratorById(id: number) {
    this.administratorService.deleteAdministratorsById(id)
      .subscribe(data => {
        console.log(data);
        this.getAdministratorsData();
      },
        error => console.log(error));
  }

  updateAdministratorById(id: number) {
    this.router.navigate(['update', id]);
  }
}
