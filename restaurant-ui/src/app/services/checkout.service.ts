import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Checkout } from '../models/checkout';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private baseUrl = `${environment.baseAPIUrl}/${environment.api.checkout}`;

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
