import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Administrator } from 'src/app/classes/administrator';
import { AdministratorService } from 'src/app/services/administrator.service';

@Component({
  selector: 'app-create-administrator',
  templateUrl: './create-administrator.component.html',
  styleUrls: ['./create-administrator.component.css']
})
export class CreateAdministratorComponent implements OnInit {

  administrator: Administrator = new Administrator();
  submitted = false;

  constructor(private administratorService: AdministratorService,
    private router: Router) { }

  ngOnInit(): void {
  }

  newAdministrator() {
    this.submitted = false;
    this.administrator = new Administrator();
  }

  save() {
    this.administratorService.
      createAdministrator(this.administrator).
      subscribe(data => {
        console.log(data);
        this.administrator = new Administrator();
        this.goToList();
      },
        error => console.log(error));
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  goToList() {
    this.router.navigate(['/administrators']);
  }
}
