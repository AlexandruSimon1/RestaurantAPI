import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Checkout } from '../classes/checkout';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private baseUrl = "http://localhost:8181/api/v1/checkout";

  constructor(private http: HttpClient) { }

  getAllCheckout(): Observable<Checkout[]> {
    return this.http.get<Checkout[]>(`${this.baseUrl}`);
  }

  getCheckoutsByID(id: number): Observable<Checkout> {
    return this.http.get<Checkout>(`${this.baseUrl}/${id}`);
  }

  deleteCheckoutsByID(id: number): Observable<Object> {
    return this.http.delete<Object>(`${this.baseUrl}/${id}`);
  }

  updateCheckoutsById(id: number, value: any): Observable<Object> {
    return this.http.put<Object>(`${this.baseUrl}/${id}`, value);
  }

  createCheckout(checkout: Checkout): Observable<Object> {
    return this.http.post<Object>(`${this.baseUrl}`, checkout);
  }
}
