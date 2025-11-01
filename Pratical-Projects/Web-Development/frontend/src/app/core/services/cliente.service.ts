import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private apiUrl = 'http://localhost:3000/api/utilizador';

  constructor(private http: HttpClient) { }

  getDashboard(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<any>(`${this.apiUrl}/dashboard`, { headers });
  }

  getRestaurantes() {
    return this.http.get('http://localhost:3000/api/utilizador/restaurantes');
  }

  alterarCredenciais(dados: { novoEmail: string; novaPassword: string }) {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put('http://localhost:3000/api/utilizador/alterar-credenciais', dados, { headers });
  }

  getHistoricoEncomendas(): Observable<any[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<any[]>('http://localhost:3000/api/utilizador/encomendas', { headers });
  }

  getRestaurantePorId(id: string) {
    return this.http.get(`http://localhost:3000/api/utilizador/restaurantes/${id}`);
  }

}
