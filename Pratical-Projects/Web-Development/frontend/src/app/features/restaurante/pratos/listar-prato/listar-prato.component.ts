import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PratoService } from '../../../../core/services/prato.service';
import { Prato } from '../../../../core/interfaces/prato.interface';

@Component({
	selector: 'app-listar-prato',
	standalone: true,
	templateUrl: './listar-prato.component.html',
	styleUrls: ['./listar-prato.component.scss'],
	imports: [CommonModule, RouterModule, FormsModule]
})
export class ListarPratoComponent implements OnInit {
	pratos: Prato[] = [];
	selectedPrato?: Prato;
	isBrowser = false;

	categoriasPrioritarias = ['Entrada', 'Prato Principal', 'Sobremesa', 'Bebidas'];
	filtro = {
		nome: '',
		categoria: '',
		ordenarPor: ''
	};

	constructor(
		private pratoService: PratoService,
		private router: Router,
		@Inject(PLATFORM_ID) private platformId: Object
	) {}

	ngOnInit(): void {
		this.isBrowser = isPlatformBrowser(this.platformId);
		if (this.isBrowser) {
			this.carregarPratos();
		}
	}

	carregarPratos(): void {
		this.pratoService.listarPratos().subscribe({
			next: (data) => this.pratos = data,
			error: (err) => console.error('Erro ao buscar pratos:', err)
		});
	}

	verDetalhes(id: string): void {
		this.router.navigate(['/prato/detalhes', id]);
	}

	editarPrato(id: string): void {
		this.router.navigate(['/prato/editar', id]);
	}

	eliminarPrato(id: string): void {
		if (!confirm('Tens a certeza que queres eliminar este prato?')) return;

		this.pratoService.apagarPrato(id).subscribe({
			next: () => this.pratos = this.pratos.filter(p => p._id !== id),
			error: () => alert('Erro ao eliminar prato.')
		});
	}

	voltarAoDashboard(): void {
		const id = localStorage.getItem('userId');
		id ? this.router.navigate(['/restaurante/dashboard', id]) : console.error('ID do restaurante não encontrado no localStorage.');
	}

	get pratosFiltrados(): Prato[] {
		return this.pratos
			.filter(p => !this.filtro.nome || p.nome.toLowerCase().includes(this.filtro.nome.toLowerCase()))
			.filter(p => !this.filtro.categoria || p.categoria === this.filtro.categoria)
			.sort((a, b) => {
				const precoA = a.doses?.[0]?.preco ?? 0;
				const precoB = b.doses?.[0]?.preco ?? 0;

				return this.filtro.ordenarPor === 'preco_asc' ? precoA - precoB :
					this.filtro.ordenarPor === 'preco_desc' ? precoB - precoA : 0;
			});
	}

	get pratosAgrupados(): Record<string, Record<string, Prato[]>> {
		return this.pratosFiltrados.reduce((acc, prato) => {
			const cat = prato.categoria || 'Outros';
			const subcat = prato.subcategoria || 'Outro';
			acc[cat] = acc[cat] || {};
			acc[cat][subcat] = acc[cat][subcat] || [];
			acc[cat][subcat].push(prato);
			return acc;
		}, {} as Record<string, Record<string, Prato[]>>);
	}

	objectKeys(obj: object): string[] {
		return Object.keys(obj);
	}

	getImagemSrc(imagem: string | undefined | null): string {
	if (!imagem) return '';
	return imagem.startsWith('http') ? imagem : `http://localhost:3000${imagem}`;
}
}
