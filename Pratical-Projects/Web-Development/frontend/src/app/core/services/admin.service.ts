import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  _id: string;
  email: string;
  role: string;
}

export interface Restaurant {
  _id: string;
  nome: string;
  pessoaResponsavel: string;
  telefone: string;
  validado: boolean;
}

export interface DashboardData {
  utilizadores: User[];
  restaurantes: Restaurant[];
}

export interface ActivityLog {
  _id: string;
  userId: { email: string; role: string };
  action: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = 'http://localhost:3000/api/admin';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  // Obter dados do dashboard (utilizadores e restaurantes)
  obterDashboard(): Observable<DashboardData> {
    const headers = this.getHeaders();
    return this.http.get<DashboardData>(`${this.apiUrl}/dashboard`, { headers });
  }

  // Remover utilizador
  removerUtilizador(userId: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.apiUrl}/utilizador/${userId}/remover`, {}, { headers });
  }

  // Validar restaurante
  validarRestaurante(restaurantId: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.apiUrl}/restaurante/${restaurantId}/validar`, {}, { headers });
  }

  // Remover restaurante
  removerRestaurante(restaurantId: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(`${this.apiUrl}/restaurante/${restaurantId}/remover`, {}, { headers });
  }

  obterLogs(): Observable<ActivityLog[]> {
    const headers = this.getHeaders();
    return this.http.get<ActivityLog[]>(`${this.apiUrl}/logs`, { headers });
  }

}