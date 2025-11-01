import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MenuService } from '../../../../core/services/menu.service';
import { Menu } from '../../../../core/services/menu.service';


@Component({
	selector: 'app-menu-detalhes',
	standalone: true,
	imports: [CommonModule, RouterModule],
	templateUrl: './menu-detalhes.component.html',
	styleUrls: ['./menu-detalhes.component.scss']
})

export class MenuDetalhesComponent implements OnInit {
	menu: Menu | null = null;
	modalPrato: any = null;
	categorias: { [nome: string]: any[] } = {};

	constructor(
		private menuService: MenuService,
		private route: ActivatedRoute,
		private router: Router
	) { }

	ngOnInit(): void {
		const menuId = this.route.snapshot.paramMap.get('id');
		if (menuId) {
			this.carregarMenu(menuId);
		}
	}

	carregarMenu(id: string): void {
		this.menuService.obterMenuPorId(id).subscribe({
			next: (res: Menu) => {
				this.menu = res;
				this.categorias = {};

				for (const prato of res.pratos) {
					const cat = prato.categoria || 'Outros';
					if (!this.categorias[cat]) {
						this.categorias[cat] = [];
					}
					this.categorias[cat].push(prato);
				}

			},
			error: (err) => {
				console.error('Erro ao carregar o menu', err);
			}
		});
	}

	voltarAoDashboard(): void {
		const id = localStorage.getItem('userId');
		if (id) {
			this.router.navigate(['/restaurante/dashboard', id]);
		} else {
			console.error('ID do restaurante não encontrado no localStorage.');
		}
	}

	abrirModal(prato: any): void {
		if (typeof prato.infoNutricional === 'string') {
			try {
				prato.infoNutricional = JSON.parse(prato.infoNutricional);
			} catch {
				prato.infoNutricional = null;
			}
		}

		this.modalPrato = prato;
	}

	fecharModal(): void {
		this.modalPrato = null;
	}

	formatId(categoria: string): string {
		return 'cat-' + categoria.toLowerCase().replace(/\s+/g, '-');
	}

	getImagemSrc(imagem: string | null | undefined): string {
	if (!imagem) return '';
	return imagem.startsWith('http') ? imagem : `http://localhost:3000${imagem}`;
}
}
