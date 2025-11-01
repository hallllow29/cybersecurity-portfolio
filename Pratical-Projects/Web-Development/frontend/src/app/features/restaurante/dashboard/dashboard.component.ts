import {
	Component,
	HostListener,
	Inject,
	OnInit,
	PLATFORM_ID,
} from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { DashboardService } from './dashboard.service';
import { Restaurante } from './interfaces/restaurante.interface';
import { Estatisticas } from './interfaces/estatisticas.interface';
import { Menu } from './interfaces/menu.interface';
import { Prato } from './../../../core/interfaces/prato.interface';
import { Encomenda } from './interfaces/encomenda.interface';
import { Notificacao } from './interfaces/notificacao.interface';
import { io, Socket } from 'socket.io-client';
import { ToastService } from '../../../core/services/toast.service';

@Component({
	standalone: true,
	selector: 'app-dashboard-restaurante',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.scss'],
	imports: [CommonModule, RouterModule],
})
export class DashboardRestauranteComponent implements OnInit {
	restaurante!: Restaurante;
	estatisticas!: Estatisticas;
	menus: Menu[] = [];
	pratos: Prato[] = [];
	encomendas: Encomenda[] = [];
	notificacoes: Notificacao[] = [];
	notificacoesNaoLidas = 0;

	dropdowns = {
		menus: false,
		pratos: false,
		encomenda: false,
	};
	dropdownVisible = false;
	dropdownNotificacoesVisivel = false;

	modalVisible = false;
	modalItem: Menu | Prato | null = null;
	modalTipo: 'menu' | 'prato' | '' = '';

	private socket!: Socket;

	constructor(
		private dashboardService: DashboardService,
		private route: ActivatedRoute,
		private router: Router,
		private toast: ToastService,
		@Inject(PLATFORM_ID) private platformId: Object
	) { }

	ngOnInit(): void {
		if (!isPlatformBrowser(this.platformId)) return;

		const restauranteId = localStorage.getItem('userId');
		const token = localStorage.getItem('token');

		if (!restauranteId || !token) {
			this.router.navigate(['/login']);
			return;
		}

		this.dashboardService.carregarDashboard(restauranteId, token).subscribe({
			next: (res) => {
				this.restaurante = res.restaurante;
				this.estatisticas = res.estatisticas;
				this.menus = res.menus;
				this.pratos = res.pratos;
				this.encomendas = res.encomendas;

				this.iniciarSocket(restauranteId);
			},
			error: () => this.router.navigate(['/login']),
		});
	}

	toggleDropdown(tipo: keyof typeof this.dropdowns, event: MouseEvent): void {
		event.stopPropagation();
		for (const key in this.dropdowns) {
			if (key !== tipo) this.dropdowns[key as keyof typeof this.dropdowns] = false;
		}
		this.dropdowns[tipo] = !this.dropdowns[tipo];
	}

	togglePerfilDropdown(): void {
		this.dropdownVisible = !this.dropdownVisible;
	}

	logout(): void {
		localStorage.clear();
		this.router.navigate(['/login']);
	}

	abrirModal(item: Menu | Prato, tipo: 'menu' | 'prato'): void {
		this.modalItem = item;
		this.modalTipo = tipo;
		this.modalVisible = true;
	}

	fecharModal(): void {
		this.modalItem = null;
		this.modalVisible = false;
		document.body.classList.remove('modal-open');
	}

	editarPerfil(): void {
		this.router.navigate(['/restaurante/editar']);
	}

	verPerfil(): void {
		this.router.navigate(['/perfil']);
	}

	verTodasNotificacoes(event: MouseEvent): void {
		event.stopPropagation();
		this.dropdownNotificacoesVisivel = false;
		this.router.navigate(['/restaurante/notificacoes']);
	}

	marcarNotificacaoComoLida(notificacao: Notificacao): void {
		if (!notificacao.lida) {
			notificacao.lida = true;
			this.notificacoesNaoLidas = Math.max(0, this.notificacoesNaoLidas - 1);
		}
	}

	iniciarSocket(restauranteId: string): void {
		this.socket = io('http://localhost:3000');
		this.socket.on('connect', () => {
			this.socket.emit('joinRestauranteRoom', restauranteId);
			this.socket.on('nova-encomenda', (data: Notificacao) => {
				this.notificacoes.unshift(data);
				this.notificacoesNaoLidas++;
				const total = (data.total ?? 0).toFixed(2);
				this.toast.showInfo(`Nova encomenda de cliente: €${total}`, '📦 Encomenda Recebida');
			});
		});
	}

	toggleDropdownNotificacoes(event: MouseEvent): void {
		event.stopPropagation();
		this.dropdownNotificacoesVisivel = !this.dropdownNotificacoesVisivel;
		if (this.dropdownNotificacoesVisivel) {
			this.notificacoesNaoLidas = 0;
		}
	}

	@HostListener('document:click', ['$event'])
	fecharDropdowns(event: MouseEvent): void {
		const target = event.target as HTMLElement;
		if (!target.closest('.notification-wrapper')) {
			this.dropdownNotificacoesVisivel = false;
		}
	}

	verNotificacao(notif: Notificacao, event: MouseEvent): void {
		event.stopPropagation();
		this.dropdownNotificacoesVisivel = false;
		const id = notif.encomendaId || notif._id;
		if (id) {
			this.router.navigate(['/restaurante/encomendas/detalhes', id]);
		}
	}

	formatarDataNotificacao(timestamp: string | Date | undefined): string {
		if (!timestamp) return '';
		const data = timestamp instanceof Date ? timestamp : new Date(timestamp);
		const agora = new Date();
		const diff = agora.getTime() - data.getTime();
		if (diff < 60000) return 'Agora mesmo';
		if (diff < 3600000) return `${Math.floor(diff / 60000)}m atrás`;
		if (diff < 86400000) return `${Math.floor(diff / 3600000)}h atrás`;
		return data.toLocaleDateString('pt-PT') + ' ' + data.toLocaleTimeString('pt-PT', { hour: '2-digit', minute: '2-digit' });
	}

	getLogoUrl(): string {
		if (this.restaurante?.logoUrl) {
			return 'http://localhost:3000' + this.restaurante.logoUrl;
		}
		return 'https://via.placeholder.com/150';
	}
	usarImagemPadrao(event: Event) {
		const imgElement = event.target as HTMLImageElement;
		imgElement.src = 'https://via.placeholder.com/150';
	}

	getImagemSrc(imagem: string | null | undefined): string {
	if (!imagem) return '';
	return imagem.startsWith('http') ? imagem : `http://localhost:3000${imagem}`;
}

}
