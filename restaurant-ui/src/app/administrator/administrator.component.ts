import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Administrator } from '../classes/administrator';
import { AdministratorService } from '../services/administrator.service';


@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css']
})
export class AdministratorComponent implements OnInit {

  administrators: Observable<Administrator[]>;

  constructor(private router: Router, private administratorService: AdministratorService) { }


  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.administrators = this.administratorService.getAdministrators();
  }

  deleteAdministratorById(id: number) {
    this.administratorService.deleteAdministratorById(id)
      .subscribe(data => {
        console.log(data);
        this.reloadData();
      },
        error => console.log(error));
  }
  
  updateAdministratorById(id: number) {
    this.router.navigate(['update', id]);
  }
}
