import { Component, OnInit } from '@angular/core';
import { Administrator } from "../administrators";
import { AdministratorsService } from '../administrators.service';

@Component({
  selector: 'app-administrators',
  templateUrl: './administrators.component.html',
  styleUrls: ['./administrators.component.css']
})
export class AdministratorsComponent implements OnInit {

  administrators:any;
  id:number;

  administrator: Administrator = new Administrator(0, "", "", "", "", 0, "")

  constructor(private service: AdministratorsService) { }

  public getAdministrators() {
    let resp=this.service.getAllAdministrators();
    resp.subscribe((data)=>this.administrators=data);
  }

  public getAdministratorsById(id:number){
    let resp=this.service.getAdministratorsById(id);
    resp.subscribe((data)=>this.administrators=data);
  }

  public deleteAdministratorById(id:number){
    let resp=this.service.deleteAdministratorById(id);
    resp.subscribe((data)=>this.administrators=data);
  }
  ngOnInit(){
    let resp=this.service.getAllAdministrators();
    resp.subscribe((data)=>this.administrators=data);    
  }

}
