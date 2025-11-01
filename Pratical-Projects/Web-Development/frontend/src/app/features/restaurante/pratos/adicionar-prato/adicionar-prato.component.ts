import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule, NgIf, NgFor } from '@angular/common';
import { PratoService } from '../../../../core/services/prato.service';
import { Prato } from '../../../../core/interfaces/prato.interface';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
	selector: 'app-adicionar-prato',
	standalone: true,
	templateUrl: './adicionar-prato.component.html',
	styleUrls: ['./adicionar-prato.component.scss'],
	imports: [CommonModule, FormsModule, NgIf, NgFor]
})
export class AdicionarPratoComponent implements OnInit {
	nomeInput$ = new Subject<string>();
	loadingVerificacao = false;
	imagemVemDaApi: boolean = false;
	prato: Partial<Prato> = {
		nome: '',
		descricao: '',
		categoria: '',
		subcategoria: '',
		infoNutricional: {
			calorias: null,
			proteinas: null,
			hidratos: null,
			gorduras: null
		}
	};

	pratoExisteNaApi = false;
	mostrarCamposManuais = false;

	doses: { nome: string; preco: number | null }[] = [{ nome: '', preco: null }];
	alergeneosSelecionados: string[] = [];
	alergeneoOutroTexto = '';
	imagemSelecionada: File | null = null;
	imagemPreview: string | null = null;
	pratoAdicionadoComSucesso = false;
	erro: string | null = null;

	categoriasPrincipais = ['Entrada', 'Prato Principal', 'Sobremesa', 'Bebidas'];
	subcategorias: Record<string, string[]> = {
		'Entrada': ['Vegetariano', 'Vegan', 'Carne', 'Peixe'],
		'Prato Principal': ['Carne', 'Peixe', 'Vegetariano', 'Vegan'],
		'Sobremesa': ['Doce', 'Fruta', 'Gelado'],
		'Bebidas': ['Sumo', 'Vinho', 'Cerveja', 'Água', 'Refrigerante', 'Cocktail']
	};

	categoriaSelecionada = '';
	subcategoriasDisponiveis: string[] = [];
	semAlergenios = false;
	modoEdicao = false;
	pratoId: string | null = null;

	constructor(
		private pratoService: PratoService,
		private router: Router,
		private route: ActivatedRoute,
	) { }

	ngOnInit() {
		document.addEventListener('keydown', this.teclaEscFecharModal.bind(this));

		if (!this.prato.infoNutricional) {
			this.prato.infoNutricional = { calorias: null, proteinas: null, hidratos: null, gorduras: null };
		}

		this.route.paramMap.subscribe(params => {
			this.pratoId = params.get('id');
			this.modoEdicao = !!this.pratoId;

			if (this.modoEdicao && this.pratoId) {
				this.pratoService.obterPratoPorId(this.pratoId).subscribe({
					next: (pratoData: Prato) => {
						this.prato.nome = pratoData.nome;
						if (!this.prato.infoNutricional) {
							this.prato.infoNutricional = { calorias: null, proteinas: null, hidratos: null, gorduras: null };
						}
						this.prato.descricao = pratoData.descricao || '';
						this.prato.categoria = pratoData.categoria;
						this.prato.subcategoria = pratoData.subcategoria || '';
						this.prato.infoNutricional = pratoData.infoNutricional || {
							calorias: null,
							proteinas: null,
							hidratos: null,
							gorduras: null
						};
						this.doses = pratoData.doses || [{ nome: '', preco: null }];
						this.alergeneosSelecionados = pratoData.alergenios || [];

						this.categoriaSelecionada = pratoData.categoria;
						this.atualizarSubcategorias();
						this.imagemPreview = pratoData.imagem
							? `http://localhost:3000${pratoData.imagem}`
							: null;
						this.imagemVemDaApi = !!(this.imagemPreview && this.imagemPreview.startsWith('http'));
					},
					error: (err: any) => {
						console.error('Erro ao carregar prato:', err);
					}
				});
			}
		});

		this.nomeInput$
			.pipe(
				debounceTime(500),
				distinctUntilChanged()
			)
			.subscribe((nome: string) => {
				this.verificarNomeApi(nome);
			});
	}


	voltarAoDashboard(): void {
		const id = localStorage.getItem('userId');
		if (id) {
			this.router.navigate([`/restaurante/dashboard/${id}`]);
		}
	}

	irParaListarPratos(): void {
		this.router.navigate(['/prato/listar']);
	}

	teclaEscFecharModal(event: KeyboardEvent): void {
		if (event.key === 'Escape') {
			this.pratoAdicionadoComSucesso = false;
		}
	}

	atualizarSubcategorias(): void {
		this.subcategoriasDisponiveis = this.subcategorias[this.categoriaSelecionada] || [];
	}

	adicionarDose(): void {
		this.doses.push({ nome: '', preco: null });
	}

	removerDose(index: number): void {
		this.doses.splice(index, 1);
	}

	onFileChange(event: any): void {
		const file = event.target.files?.[0];
		if (file) {
			this.imagemSelecionada = file;
			this.imagemPreview = URL.createObjectURL(file);
		}
	}

	toggleAlergeneo(valor: string): void {
		const index = this.alergeneosSelecionados.indexOf(valor);
		if (index > -1) {
			this.alergeneosSelecionados.splice(index, 1);
		} else {
			this.alergeneosSelecionados.push(valor);
		}
	}

	aoSelecionarSemAlergenios(): void {
		if (this.semAlergenios) {
			this.alergeneosSelecionados = [];
			this.alergeneoOutroTexto = '';
		}
	}

	get dosesInvalidas(): boolean {
		return this.doses.some(d => !d.nome || !d.preco || d.preco <= 0);
	}

	get infoNutricionalVazia(): boolean {
		const n = this.prato.infoNutricional;
		return !n?.calorias && !n?.proteinas && !n?.hidratos && !n?.gorduras;
	}

	get alergeneosVazios(): boolean {
		return !this.semAlergenios && this.alergeneosSelecionados.length === 0;
	}

	guardarPrato(form: NgForm): void {
		this.erro = null;

		if (form.invalid) {
			this.erro = 'Preenche todos os campos obrigatórios.';
			return;
		}

		if (!this.imagemSelecionada && !this.imagemVemDaApi && !this.modoEdicao) {
		this.erro = 'Seleciona uma imagem para o prato.';
		return;
}

	if (this.dosesInvalidas) {
		this.erro = 'Todas as doses devem ter nome e preço válido.';
		return;
	}

	// Validação condicional para campos manuais
	if (this.mostrarCamposManuais) {
		const n = this.prato.infoNutricional;
		if (
			!n?.calorias ||
			!n?.proteinas ||
			!n?.hidratos ||
			!n?.gorduras
		) {
			this.erro = 'Preenche todos os campos da informação nutricional.';
			return;
		}

		if (!this.semAlergenios && this.alergeneosSelecionados.length === 0) {
			this.erro = 'Indica pelo menos um alergénio ou seleciona "Sem Alergénios".';
			return;
		}
	}

	this.prato.categoria = this.categoriaSelecionada;

	const formData = new FormData();
	formData.append('nome', this.prato.nome || '');
	formData.append('descricao', this.prato.descricao || '');
	formData.append('categoria', this.prato.categoria || '');
	formData.append('subcategoria', this.prato.subcategoria || '');
	formData.append('doses', JSON.stringify(this.doses));

	// ✅ Envia sempre a info nutricional (manual ou da API)
	const n = this.prato.infoNutricional || {
		calorias: null,
		proteinas: null,
		hidratos: null,
		gorduras: null,
	};
	formData.append('infoNutricional', JSON.stringify(n));

	// ✅ Envia sempre alergénios (manuais ou da API)
	const todosAlergeneos = this.semAlergenios
		? ['Nenhum']
		: [...this.alergeneosSelecionados];

	if (this.alergeneosSelecionados.includes('outros') && this.alergeneoOutroTexto) {
		todosAlergeneos.push(this.alergeneoOutroTexto);
	}

	formData.append('alergenios', JSON.stringify(todosAlergeneos));

	if (this.imagemSelecionada) {
		formData.append('imagemPrato', this.imagemSelecionada);
} else if (this.imagemVemDaApi && this.imagemPreview) {
	formData.append('imagemUrl', this.imagemPreview);
}

	if (this.modoEdicao && this.pratoId) {
		this.pratoService.editarPrato(this.pratoId, formData).subscribe({
			next: () => this.router.navigate(['/prato/listar']),
			error: (err: any) => {
				console.error('Erro ao editar prato:', err);
				this.erro = err.error?.message || 'Erro ao editar prato.';
			}
		});
	} else {
		this.pratoService.adicionarPrato(formData).subscribe({
			next: () => {
				this.pratoAdicionadoComSucesso = true;
			},
			error: (err: any) => {
				console.error('Erro ao adicionar prato:', err);
				this.erro = err.error?.message || 'Erro ao adicionar prato.';
			}
		});
	}
}



	get calorias(): number | null {
		return this.prato.infoNutricional?.calorias ?? null;
	}

	set calorias(value: number | null) {
		if (!this.prato.infoNutricional) {
			this.prato.infoNutricional = { calorias: null, proteinas: null, hidratos: null, gorduras: null };
		}
		this.prato.infoNutricional.calorias = value;
	}

	get proteinas(): number | null {
		return this.prato.infoNutricional?.proteinas ?? null;
	}

	set proteinas(value: number | null) {
		if (!this.prato.infoNutricional) {
			this.prato.infoNutricional = { calorias: null, proteinas: null, hidratos: null, gorduras: null };
		}
		this.prato.infoNutricional.proteinas = value;
	}

	get hidratos(): number | null {
		return this.prato.infoNutricional?.hidratos ?? null;
	}

	set hidratos(value: number | null) {
		if (!this.prato.infoNutricional) {
			this.prato.infoNutricional = { calorias: null, proteinas: null, hidratos: null, gorduras: null };
		}
		this.prato.infoNutricional.hidratos = value;
	}

	get gorduras(): number | null {
		return this.prato.infoNutricional?.gorduras ?? null;
	}

	set gorduras(value: number | null) {
		if (!this.prato.infoNutricional) {
			this.prato.infoNutricional = { calorias: null, proteinas: null, hidratos: null, gorduras: null };
		}
		this.prato.infoNutricional.gorduras = value;
	}

	onNomeChange(nome: string) {
		this.loadingVerificacao = true;
		this.nomeInput$.next(nome);
	}

verificarNomeApi(nome: string) {
	if (!nome || nome.trim() === '') {
		this.pratoExisteNaApi = false;
		this.mostrarCamposManuais = false;
		this.loadingVerificacao = false;
		return;
	}

	this.pratoExisteNaApi = false;
	this.mostrarCamposManuais = false;
	this.loadingVerificacao = true;

	this.pratoService.checkIfExistsInApi(nome).subscribe({
		next: (pratoData) => {
			this.loadingVerificacao = false;

			if (pratoData) {
				this.pratoExisteNaApi = true;
				this.mostrarCamposManuais = false;

				// 🔁 Preencher campos com dados da API
				this.prato.infoNutricional = pratoData.infoNutricional || {
					calorias: null,
					proteinas: null,
					hidratos: null,
					gorduras: null,
				};

				this.alergeneosSelecionados = pratoData.alergenios || [];

				// Imagem (opcional)
				if (pratoData.imagem) {
					this.imagemPreview = pratoData.imagem;
					this.imagemVemDaApi = true;
				}
			} else {
				this.pratoExisteNaApi = false;
				this.mostrarCamposManuais = true;
			}
		},
		error: () => {
			this.loadingVerificacao = false;
			this.pratoExisteNaApi = false;
			this.mostrarCamposManuais = true;
		}
	});
}


}
