import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PratoService } from '../../../../core/services/prato.service';
import { Prato } from '../../../../core/interfaces/prato.interface'; // Importa a interface correta
import { NgIf, NgFor, CurrencyPipe } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
	selector: 'app-detalhes-prato',
	standalone: true,
	templateUrl: './detalhes-prato.component.html',
	styleUrls: ['./detalhes-prato.component.scss'],
	imports: [NgIf, NgFor, CurrencyPipe, CommonModule]
})
export class DetalhesPratoComponent implements OnInit {
	prato: Prato | null = null;
	abaAtiva: 'nutri' | 'alerg' = 'nutri';

	constructor(
		private route: ActivatedRoute,
		private router: Router,
		private pratoService: PratoService
	) { }

	ngOnInit(): void {
		const id = this.route.snapshot.paramMap.get('id');
		if (id) {
			this.pratoService.obterPratoPublicoPorId(id).subscribe({
				next: (prato) => this.prato = prato,
				error: (err) => console.error('Erro ao obter prato:', err)
			});
		}
	}

	voltar(): void {
		this.router.navigate(['/prato/listar']);
	}

	getImagemSrc(imagem: string | undefined | null): string {
	if (!imagem) return '';
	return imagem.startsWith('http') ? imagem : `http://localhost:3000${imagem}`;
}
}


