import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Especialidade {
  private apiUrl = 'http://localhost:8787/api/especialidades';
  constructor(private http: HttpClient) {}

  listar(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  salvar(esp: any): Observable<any> {
    return esp.id
      ? this.http.put(`${this.apiUrl}/${esp.id}`, esp)
      : this.http.post(this.apiUrl, esp);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
