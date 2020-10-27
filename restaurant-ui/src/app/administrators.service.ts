import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class AdministratorsService {

  constructor(private http: HttpClient) { }

  public getAllAdministrators(){
    return this.http.get("http://localhost:8081/api/v1/administrators",{responseType: 'text' as 'json'});
  }
  
  public getAdministratorsById(id){
    return this.http.get("http://localhost8081:/api/v1/administrators/"+id);
  }

  public deleteAdministratorById(id){
    return this.http.delete("http://localhost:8081/api/v1/administrators/"+id);
  }
}
