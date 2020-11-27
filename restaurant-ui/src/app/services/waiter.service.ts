import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Waiter } from '../models/waiter';

@Injectable({
  providedIn: 'root'
})
export class WaiterService {

  private baseUrl = "http://localhost:8181/api/v1/waiters";
  constructor(private http: HttpClient) { }

  getWaiters(): Observable<Waiter[]> {
    return this.http.get<Waiter[]>(`${this.baseUrl}`);
  }

  getWaitersById(id: number): Observable<Waiter> {
    return this.http.get<Waiter>(`${this.baseUrl}/${id}`);
  }

  deleteWaitersById(id: number): Observable<Object> {
    return this.http.delete<Object>(`${this.baseUrl}/${id}`);
  }

  updateWaiterById(id: number, value: any): Observable<Object> {
    return this.http.put<Object>(`${this.baseUrl} / ${id}`, value);
  }

  createWaiter(waiter: Waiter): Observable<Object> {
    return this.http.post<Object>(`${this.baseUrl}`, waiter);
  }
}
