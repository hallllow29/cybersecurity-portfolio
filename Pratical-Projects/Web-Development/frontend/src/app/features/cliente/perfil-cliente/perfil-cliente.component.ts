import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../core/services/cliente.service';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-perfil-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './perfil-cliente.component.html',
  styleUrls: ['./perfil-cliente.component.scss'],
})
export class PerfilClienteComponent implements OnInit {
  utilizador: any = null;
  erro: string | null = null;
  secaoAtual: string = 'casa';
  novoEmail: string = '';
  novaPassword: string = '';
  encomendas: any[] = [];
  encomendasAgrupadas: { data: string, encomendas: any[] }[] = [];
  encomendaAbertaId: string | null = null;
  encomendasExpandida: { [id: string]: boolean } = {};

  constructor(private clienteService: ClienteService, private location: Location, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      if (params['secao']) {
        this.secaoAtual = params['secao'];
      }
      this.clienteService.getDashboard().subscribe({
        next: (res: any) => {
          this.utilizador = res.utilizador;

          if (this.utilizador?.dataRegisto) {
            this.utilizador.dataRegisto = new Date(this.utilizador.dataRegisto);
          }
        },
        error: (err) => {
          console.error('Erro ao carregar perfil:', err);
          this.erro = 'Erro ao carregar dados do perfil.';
        },
      });

      this.carregarEncomendas(); 
    });
  }

  voltar(): void {
    this.location.back();
  }

  mudarSecao(secao: string): void {
    this.secaoAtual = secao;
  }

  alterarCredenciais(): void {
    this.clienteService.alterarCredenciais({
      novoEmail: this.novoEmail,
      novaPassword: this.novaPassword
    }).subscribe({
      next: () => alert('Credenciais atualizadas com sucesso.'),
      error: (err) => console.error('Erro ao alterar credenciais:', err)
    });
  }


  carregarEncomendas(): void {
    this.clienteService.getHistoricoEncomendas().subscribe({
      next: (dados) => {
        this.encomendas = dados;
        this.processarEncomendas(this.encomendas);
      },
      error: (err) => {
        console.error('Erro ao carregar encomendas:', err);
      }
    });
  }

  toggleDetalhes(id: string): void {
    this.encomendaAbertaId = this.encomendaAbertaId === id ? null : id;
  }
  processarEncomendas(raw: any[]): void {
    const agrupadas: { [key: string]: any[] } = {};

    raw.forEach((e) => {
      const data = new Date(e.criadoEm).toLocaleDateString('pt-PT');
      if (!agrupadas[data]) agrupadas[data] = [];
      agrupadas[data].push(e);
    });

    this.encomendasAgrupadas = Object.entries(agrupadas)
      .map(([data, encomendas]) => ({ data, encomendas }))
      .sort((a, b) => new Date(b.data).getTime() - new Date(a.data).getTime());
  }
}
