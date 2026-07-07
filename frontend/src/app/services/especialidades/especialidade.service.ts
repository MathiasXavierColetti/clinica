import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Especialidade } from './especialidade.model'; // Importa a interface

@Injectable({
  providedIn: 'root',
})
export class EspecialidadeService {
  private apiUrl = 'http://localhost:8787/api/especialidades';

  constructor(private http: HttpClient) {}

  listar(): Observable<Especialidade[]> {
    return this.http.get<Especialidade[]>(this.apiUrl);
  }

  salvar(esp: Especialidade): Observable<Especialidade> {
    return esp.id
      ? this.http.put<Especialidade>(`${this.apiUrl}/${esp.id}`, esp)
      : this.http.post<Especialidade>(this.apiUrl, esp);
  }

  // CORREÇÃO NO SERVIÇO
  excluir(id: number): Observable<void> {
    // O 'return' é obrigatório aqui
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
