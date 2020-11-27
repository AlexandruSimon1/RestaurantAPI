import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Administrator } from 'src/app/models/administrator';
import { AdministratorService } from 'src/app/services/administrator.service';

@Component({
  selector: 'app-update-administrator',
  templateUrl: './update-administrator.component.html',
  styleUrls: ['./update-administrator.component.css']
})
export class UpdateAdministratorComponent implements OnInit {

  id: number;
  administrator: Administrator

  constructor(private route: ActivatedRoute, private router: Router,
    private administratorService: AdministratorService) { }

  ngOnInit(): void {
    this.administrator = new Administrator();
    this.id = this.route.snapshot.params['id'];

    this.administratorService.getAdministratorById(this.id).subscribe(data => {
      console.log(data);
      this.administrator = data;
    },
      error => console.log(error));
  }

  updateAdministratorById() {
    this.administratorService.updateAdministratorById(this.id, this.administrator)
      .subscribe(data => {
        console.log(data);
        this.administrator = new Administrator();
        this.goToList();
      }
        , error => console.log(error));
  }

  onSubmit() {
    this.updateAdministratorById();
  }

  goToList() {
    this.router.navigate(['/administrators']);
  }
}
