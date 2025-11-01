import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RestauranteService {
  private apiUrl = 'http://localhost:3000/api/restaurante';
  private publicApiUrl = 'http://localhost:3000/api/public';

  constructor(private http: HttpClient) { }

  obterPerfil(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<any>(`${this.apiUrl}/perfil`, { headers });
  }

  atualizarPerfil(formData: FormData): Observable<any> {
    return this.http.put(`${this.apiUrl}/perfil`, formData, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + (localStorage.getItem('token') || '')
      })
    });
  }

  listarEncomendas(estado = '') {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const options = estado ? { headers, params: { status: estado } } : { headers };

    return this.http.get<any[]>(`${this.apiUrl}/encomendas`, options);
  }

  atualizarEstadoEncomenda(id: string, novoEstado: string) {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.put(
      `${this.apiUrl}/encomendas/${id}/estado`,
      { novoEstado },
      { headers }
    );
  }

  obterRestaurantesPublicos() {
    return this.http.get<any[]>(`${this.publicApiUrl}/restaurantes`);
  }

}
