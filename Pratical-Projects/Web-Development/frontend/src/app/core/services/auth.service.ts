import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = 'http://localhost:3000/api';

  constructor(private http: HttpClient, private router: Router) { }

  // LOGIN
  loginRequest(data: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/login`, data);
  }

  // REGISTO
  registarUtilizador(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/registar`, formData);
  }

  // Guardar token + dados
  salvarLogin(token: string, userId: string, role: string): void {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      localStorage.setItem('role', role);
    }
  }

  // Logout
  logout(): void {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      localStorage.removeItem('role');
    }
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('token');
    }
    return null;
  }

  getUserId(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('userId');
    }
    return null;
  }

  getRole(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      const r = localStorage.getItem('role');
      console.log('🔑 Role no localStorage:', r);
      return r;
    }
    return null;
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  isCliente(): boolean {
    return this.getRole() === 'cliente';
  }

  isRestaurante(): boolean {
    return this.getRole() === 'restaurante';
  }

  isAdmin(): boolean {
    return this.getRole() === 'admin';
  }

  getUser() {
    return {
      id: this.getUserId(),
      role: this.getRole()
    };
  }
}
