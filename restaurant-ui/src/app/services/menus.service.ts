import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Menus } from '../models/menus';

@Injectable({
  providedIn: 'root'
})
export class MenusService {

  private baseUrl = `${environment.baseAPIUrl}/${environment.api.menu}`;

  constructor(private http: HttpClient) { }

  getMenus(): Observable<Menus[]> {
    return this.http.get<Menus[]>(`${this.baseUrl}`);
  }

  getMenusById(id: number): Observable<Menus> {
    return this.http.get<Menus>(`${this.baseUrl}/${id}`);
  }

  deleteMenusById(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  updateMenusById(id: number, value: any): Observable<Object> {
    return this.http.put<Object>(`${this.baseUrl}/${id}`, value);
  }

  createMenus(menus: Menus): Observable<Object> {
    return this.http.post<Object>(`${this.baseUrl}`, menus);
  }
}
