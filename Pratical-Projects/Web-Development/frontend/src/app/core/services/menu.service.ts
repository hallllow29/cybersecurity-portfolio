import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Menu {
  nome: string;
  descricao: string;
  pratos: any[];
  imagem: string;
}
@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private apiUrl = 'http://localhost:3000/api/admin/menus'; 
  private publicUrl = 'http://localhost:3000/api/menus'; 

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    if (typeof window === 'undefined') return new HttpHeaders();
    const token = localStorage.getItem('token') || '';
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  criarMenu(formData: FormData): Observable<any> {
    return this.http.post(this.apiUrl, formData, {
      headers: this.getHeaders()
    });
  }

  listarMenusPrivado(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`, {
      headers: this.getHeaders()
    });
  }

  listarMenus(): Observable<any[]> {
    return this.http.get<any[]>(`${this.publicUrl}`, {
      headers: this.getHeaders()
    });
  }

  obterMenuPorId(id: string): Observable<Menu> {
    return this.http.get<Menu>(`http://localhost:3000/api/menus/${id}`, {
      headers: new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('token'))
    });
  }

  editarMenu(id: string, formData: FormData): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, formData, {
      headers: this.getHeaders()
    });
  }


  apagarMenu(id: string) {
    const token = localStorage.getItem('token') || '';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }

}