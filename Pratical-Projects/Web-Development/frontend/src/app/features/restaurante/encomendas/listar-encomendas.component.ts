import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { RestauranteService } from '../../../core/services/restaurante.service';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { io } from 'socket.io-client';

@Component({
  selector: 'app-listar-encomendas',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './listar-encomendas.component.html',
  styleUrls: ['./listar-encomendas.component.scss']

})
export class ListarEncomendasComponent implements OnInit {
  erro: string | null = null;
  encomendas: any[] = [];
  filtroEstado: string = '';
  estados: string[] = ['pendente', 'aceite', 'preparacao', 'enviado', 'entregue', 'cancelada'];
  modalAberto = false;
  modalEncomenda: any = null;
  userId: string | null = null;

  constructor(private restauranteService: RestauranteService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
    this.carregarEncomendas();
    this.iniciarSocket();
  }

  private socket: any;

  iniciarSocket(): void {
    const restauranteId = localStorage.getItem('userId');
    this.socket = io('http://localhost:3000');

    this.socket.emit('joinRestauranteRoom', restauranteId);

    this.socket.on('nova-encomenda', (data: any) => {
      this.toastr.info(
        `Nova encomenda de ${data.nomeCliente || 'cliente'}: ${data.total}€`,
        '📦 Encomenda Recebida'
      );

      this.carregarEncomendas();
    });
  }

  carregarEncomendas(): void {
    this.restauranteService.listarEncomendas(this.filtroEstado).subscribe({
      next: (res) => {
        this.encomendas = res.map(e => ({
          ...e,
          mostrarDetalhes: false
        }));
        this.erro = null;
      },
      error: (err) => {
        console.error('Erro ao buscar encomendas:', err);
        this.erro = 'Erro ao carregar encomendas.';
      }
    });
  }

  atualizarEstado(id: string, novoEstado: string) {
    this.restauranteService.atualizarEstadoEncomenda(id, novoEstado).subscribe({
      next: () => this.carregarEncomendas(),
      error: (err) => {
        console.error('Erro ao atualizar estado:', err);
        this.erro = 'Erro ao atualizar encomenda.';
      }
    });
  }

  getBadgeClass(estado: string): string {
    switch (estado) {
      case 'pendente': return 'bg-warning text-dark';
      case 'aceite': return 'bg-info text-dark';
      case 'preparacao': return 'bg-primary';
      case 'enviado': return 'bg-secondary';
      case 'entregue': return 'bg-success';
      case 'cancelada': return 'bg-danger';
      default: return 'bg-light text-dark';
    }
  }

  abrirModal(encomenda: any): void {
    this.modalEncomenda = encomenda;
    this.modalAberto = true;
  }

  fecharModal(): void {
    this.modalAberto = false;
    this.modalEncomenda = null;
  }
}