import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Menus } from '../classes/menus';

@Injectable({
  providedIn: 'root'
})
export class MenusService {

  private baseUrl = "http://localhost:8181/api/v1/menus";

  constructor(private http: HttpClient) { }

  getMenus(): Observable<Menus[]> {
    return this.http.get<Menus[]>(`${this.baseUrl}`);
  }

  getMenusById(id: number): Observable<Menus> {
    return this.http.get<Menus>(`${this.baseUrl}/${id}`);
  }

  deleteMenusById(id: number): Observable<Object> {
    return this.http.delete<Object>(`${this.baseUrl}/${id}`);
  }

  updateMenusById(id: number, value: any): Observable<Object> {
    return this.http.put<Object>(`${this.baseUrl}/${id}`, value);
  }

  createMenus(menus: Menus): Observable<Object> {
    return this.http.post<Object>(`${this.baseUrl}`, menus);
  }
}
