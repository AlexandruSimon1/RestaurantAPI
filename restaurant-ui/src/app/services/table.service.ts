import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Table } from '../models/table';

@Injectable({
  providedIn: 'root'
})
export class TableService {

  private baseUrl = `${environment.baseAPIUrl}/${environment.api.table}`;

  constructor(private http: HttpClient) { }

  getAllTables(): Observable<Table[]> {
    return this.http.get<Table[]>(`${this.baseUrl}`);
  }

  getTableById(id: number): Observable<Table> {
    return this.http.get<Table>(`${this.baseUrl}/${id}`);
  }

  deleteTableById(id: number): Observable<Object> {
    return this.http.delete<Object>(`${this.baseUrl}/${id}`);
  }

  updateTableById(id: number, value: any): Observable<Object> {
    return this.http.put<Object>(`${this.baseUrl}/${id}`, value);
  }

  createTable(table: Table): Observable<Table> {
    return this.http.post<Table>(`${this.baseUrl}`, table);
  }
}
