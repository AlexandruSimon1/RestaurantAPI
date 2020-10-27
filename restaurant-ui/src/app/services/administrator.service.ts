import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Administrator } from '../classes/administrator';


@Injectable({
  providedIn: 'root'
})
export class AdministratorService {
  private baseUrl = "http://localhost:8181/api/v1/administrators";

  constructor(private http: HttpClient) { }
  getAdministrators(): Observable<Administrator[]> {
    return this.http.get<Administrator[]>(`${this.baseUrl}`);
  }
  getAdministratorById(id: number): Observable<Administrator> {
    return this.http.get<Administrator>(`${this.baseUrl}/${id}`);
  }
  deleteAdministratorById(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType:'text'});
  }
  createAdministrator(administrator: Administrator): Observable<Administrator> {
    return this.http.post<Administrator>(`${this.baseUrl}`, administrator);
  }
  updateAdministratorById(id: number, value: any): Observable<Administrator> {
    return this.http.put<Administrator>(`${this.baseUrl}/${id}`, value);
  }
}
