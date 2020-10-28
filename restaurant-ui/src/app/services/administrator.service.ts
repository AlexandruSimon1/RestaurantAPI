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

  deleteAdministratorsById(id: number): Observable<Object> {
    return this.http.delete<Object>(`${this.baseUrl}/${id}`);
  }

  createAdministrator(administrator: Administrator): Observable<Object> {
    return this.http.post<Object>(`${this.baseUrl}`, administrator);
  }

  updateAdministratorById(id: number, value: any): Observable<Object> {
    return this.http.put<Object>(`${this.baseUrl}/${id}`, value);
  }
}
