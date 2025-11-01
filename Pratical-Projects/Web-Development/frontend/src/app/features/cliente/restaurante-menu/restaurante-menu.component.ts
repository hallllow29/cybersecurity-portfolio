import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { loadStripe } from '@stripe/stripe-js';



@Component({
	selector: 'app-restaurante-menu',
	standalone: true,
	imports: [CommonModule, RouterModule, FormsModule],
	templateUrl: './restaurante-menu.component.html'
})
export class RestauranteMenuComponent implements OnInit {
	restauranteId!: string;
	menus: any[] = [];
	carrinho: any[] = [];
	quantidades: { [key: string]: number } = {};
	metodoPagamento: string = '';
	metodosPagamentoDisponiveis: string[] = [];
	modalAberto = false;
	pratoSelecionado: any = null;
	moradaEntrega: string = '';

	dadosCartao = {
		numero: '',
		nomeTitular: '',
		validade: '',
		cvv: ''
	};

	dadosMbway = {
		telefone: ''
	};

	dadosReferencia = {
		entidade: '',
		referencia: '',
		valor: 0
	};


	objectKeys = Object.keys;

	constructor(
		private route: ActivatedRoute,
		private http: HttpClient,
		private router: Router,
		private toastr: ToastrService,
		@Inject(PLATFORM_ID) private platformId: Object
	) { }

	ngOnInit(): void {
		this.restauranteId = this.route.snapshot.paramMap.get('id')!;
		this.carregarMenu();
	}

	carregarMenu(): void {
		if (!isPlatformBrowser(this.platformId)) return;

		const token = localStorage.getItem('token');
		const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

		this.http
			.get<any>(`http://localhost:3000/api/utilizador/restaurantes/${this.restauranteId}/menu`, { headers })
			.subscribe({
				next: (res) => {
					this.metodosPagamentoDisponiveis = res.metodosPagamento;

					this.menus = res.menus.map((menu: any) => {
						const categorias: any = {};

						for (const prato of menu.pratos) {
							const cat = prato.categoria || 'Sem Categoria';
							const sub = prato.subcategoria || 'Outros';

							if (!categorias[cat]) categorias[cat] = {};
							if (!categorias[cat][sub]) categorias[cat][sub] = [];

							categorias[cat][sub].push(prato);
						}

						return { ...menu, categorias, mostrar: false };
					});
				},
				error: (err) => console.error('Erro ao carregar menu:', err)
			});
	}

	timer: any = null;
	tempoRestante: number = 0;

	adicionarAoCarrinho(prato: any, dose: any): void {
		const key = prato._id + '_' + dose.nome;
		const qtd = this.quantidades[key] || 1;

		if (this.carrinho.length === 0) {
			this.iniciarTimer();
		}

		const existente = this.carrinho.find(item => item.prato._id === prato._id && item.dose === dose.nome);
		if (existente) {
			existente.quantidade += qtd;
		} else {
			this.carrinho.push({
				prato,
				dose: dose.nome,
				quantidade: qtd,
				precoUnitario: dose.preco
			});
		}

		this.quantidades[key] = 1;
	}

	removerDoCarrinho(index: number): void {
		this.carrinho.splice(index, 1);

		if (this.carrinho.length === 0) {
			this.pararTimer();
		}
	}

	iniciarTimer(): void {
		this.pararTimer();
		this.tempoRestante = 600;
		this.timer = setInterval(() => {
			this.tempoRestante--;
			if (this.tempoRestante <= 0) {
				this.pararTimer();
				this.carrinho = [];
				this.quantidades = {};
				this.toastr.info('O tempo expirou. O carrinho foi limpo.', 'Carrinho');
			}
		}, 1000);
	}

	pararTimer(): void {
		if (this.timer) {
			clearInterval(this.timer);
			this.timer = null;
		}
		this.tempoRestante = 0;
	}

	totalCarrinho(): number {
		return this.carrinho.reduce((sum, item) => sum + item.precoUnitario * item.quantidade, 0);
	}

	finalizarEncomenda(): void {
		if (!this.moradaEntrega) {
			alert('Por favor, preencha a morada de entrega.');
			return;
		}

		switch (this.metodoPagamento) {
			case 'Cartão de Crédito/Débito':
				this.criarSessaoStripe(); // Redireciona para Stripe
				return; // Não continua com o POST local
				break;
			case 'MB Way':
				if (!this.dadosMbway.telefone) {
					alert('Informe o número de telemóvel para o MB Way.');
					return;
				}
				break;
			case 'Referência Multibanco':
				if (!this.dadosReferencia.entidade || !this.dadosReferencia.referencia || !this.dadosReferencia.valor) {
					alert('Preencha os dados da referência multibanco.');
					return;
				}
				break;
		}

		const token = localStorage.getItem('token');
		const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

		const payload = {
			restaurante: this.restauranteId,
			pratos: this.carrinho.map(item => ({
				prato: item.prato._id,
				dose: item.dose,
				quantidade: item.quantidade,
				precoUnitario: item.precoUnitario
			})),
			total: this.totalCarrinho(),
			metodoPagamento: this.metodoPagamento,
			moradaEntrega: this.moradaEntrega,
			dadosPagamento: {
				cartao: this.dadosCartao,
				mbway: this.dadosMbway,
				referencia: this.dadosReferencia
			}
		};

		this.http.post('http://localhost:3000/api/utilizador/encomendar', payload, { headers }).subscribe({
			next: () => {
				this.toastr.success('Encomenda realizada com sucesso!', 'Sucesso');
				this.carrinho = [];
				this.metodoPagamento = '';
				this.moradaEntrega = '';
				this.pararTimer();
				this.toastr.success('Encomenda registada. Aguarda pagamento.', 'MB Way');
				this.router.navigate(['/cliente/perfil'], { queryParams: { secao: 'encomendas' } });
			},
			error: (err) => {
				console.trace('Erro ao finalizar encomenda:', err);
				this.toastr.error('Erro ao finalizar encomenda.');
			}
		});
	}

	abrirModal(prato: any): void {
		this.pratoSelecionado = prato;
		this.modalAberto = true;
	}

	fecharModal(): void {
		this.modalAberto = false;
		this.pratoSelecionado = null;
	}

	criarSessaoStripe(): void {
		console.log('🟡 A iniciar Stripe Checkout...');
		const token = localStorage.getItem('token');
		const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
		const stripePromise = loadStripe('pk_test_51RTlHUQW6xNkqh58FxTa2GLzQM6DRLVfPWrHPb8ClrwCrLP9fJTklNRuEIqjt1as9RNJbH2UNuyuMbokED4CmPcT008TSBSAl6');

		const payload = {
			restaurante: this.restauranteId,
			pratos: this.carrinho.map(item => ({
				prato: item.prato._id,
				dose: item.dose,
				quantidade: item.quantidade,
				precoUnitario: item.precoUnitario
			})),
			total: this.totalCarrinho(),
			metodoPagamento: 'Cartão de Crédito/Débito',
			moradaEntrega: this.moradaEntrega
		};

		this.http.post<any>('http://localhost:3000/api/stripe/pagamento', payload, { headers }).subscribe({
			next: async (res) => {
				const stripe = await stripePromise;
				if (stripe && res.sessionId) {
					await stripe.redirectToCheckout({ sessionId: res.sessionId });
				} else {
					this.toastr.error('Erro ao redirecionar para o Stripe.');
					console.error('Stripe ou sessionId em falta:', res);
				}
			},
			error: (err) => {
				console.error('Erro ao iniciar pagamento:', err);
				this.toastr.error('Erro ao iniciar pagamento com Stripe.');
			}
		});
	}

	getImagemSrc(imagem: string | null | undefined): string {
	if (!imagem) return '';
	return imagem.startsWith('http') ? imagem : `http://localhost:3000${imagem}`;
}
}
