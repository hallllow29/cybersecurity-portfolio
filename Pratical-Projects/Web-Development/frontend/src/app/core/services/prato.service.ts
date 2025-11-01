import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Prato } from '../interfaces/prato.interface';

@Injectable({
	providedIn: 'root'
})
export class PratoService {
	private apiUrl = 'http://localhost:3000/api/admin/pratos';

	constructor(private http: HttpClient) { }

	private getHeaders(): { headers: HttpHeaders } {
		if (typeof window === 'undefined') return { headers: new HttpHeaders() };

		const token = localStorage.getItem('token') || '';
		console.log('✅ Token enviado:', token);

		return {
			headers: new HttpHeaders({
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			})
		};
	}

	listarPratos(): Observable<Prato[]> {
		return this.http.get<Prato[]>(this.apiUrl, this.getHeaders());
	}

	apagarPrato(id: string): Observable<void> {
		return this.http.delete<void>(`${this.apiUrl}/${id}`, this.getHeaders());
	}

	adicionarPrato(formData: FormData): Observable<any> {
		const token = localStorage.getItem('token') || '';
		return this.http.post(this.apiUrl, formData, {
			headers: new HttpHeaders({ 'Authorization': `Bearer ${token}` })
		});
	}

	editarPrato(id: string, formData: FormData): Observable<any> {
		const token = localStorage.getItem('token') || '';
		return this.http.put(`${this.apiUrl}/${id}`, formData, {
			headers: new HttpHeaders({ 'Authorization': `Bearer ${token}` })
		});
	}

	obterPratoPorId(id: string): Observable<Prato> {
		return this.http.get<Prato>(`${this.apiUrl}/${id}`, this.getHeaders());
	}

	obterPratoPublicoPorId(id: string): Observable<Prato> {
		return this.http.get<Prato>(`http://localhost:3000/api/pratos/${id}`);
	}

	checkIfExistsInApi(nome: string): Observable<Prato | null> {
	return this.http.get<Prato | null>(`/api/pratos/detalhes?nome=${encodeURIComponent(nome)}`);
}
}
