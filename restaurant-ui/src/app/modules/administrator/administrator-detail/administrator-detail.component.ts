import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Administrator } from 'src/app/models/administrator';
import { AdministratorService } from 'src/app/services/administrator.service';

@Component({
  selector: 'app-administrator-detail',
  templateUrl: './administrator-detail.component.html',
  styleUrls: ['./administrator-detail.component.css']
})
export class AdministratorDetailComponent implements OnInit {

  id: number;
  administrator: Administrator;

  constructor(private route: ActivatedRoute,private router: Router,
    private administratorService: AdministratorService) { }

  ngOnInit() {
    this.administrator = new Administrator();

    this.id = this.route.snapshot.params['id'];
    
    this.administratorService.getAdministratorById(this.id)
      .subscribe(data => {
        console.log(data)
        this.administrator = data;
      }, error => console.log(error));
  }

  list(){
    this.router.navigate(['administrators']);
  }
}
