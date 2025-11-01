import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Restaurante } from './interfaces/restaurante.interface';
import { Estatisticas } from './interfaces/estatisticas.interface';
import { Menu } from './interfaces/menu.interface';
import { Prato } from '../../../core/interfaces/prato.interface';
import { Encomenda } from './interfaces/encomenda.interface';

interface DashboardResponse {
  restaurante: Restaurante;
  estatisticas: Estatisticas;
  menus: Menu[];
  pratos: Prato[];
  encomendas: Encomenda[];
}

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private readonly API_URL = 'http://localhost:3000/api/restaurante';

  constructor(private http: HttpClient) {}

  carregarDashboard(restauranteId: string, token: string): Observable<DashboardResponse> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<DashboardResponse>(`${this.API_URL}/dashboard/${restauranteId}`, { headers });
  }
}
