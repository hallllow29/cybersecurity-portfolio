import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../../core/services/cliente.service';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  imports: [CommonModule, RouterModule],
})
export class DashboardComponent implements OnInit {
  utilizador: any = null;
  totalEncomendas = 0;
  ultimasEncomendas: any[] = [];
  erro: string | null = null;
  restauranteSelecionado: any = null;

  restaurantes: any[] = [];
  tiposCozinha = ['Portuguesa', 'Italiana', 'Japonesa', 'Vegetariana', 'Outro'];
  filtroSelecionado: string = '';

  clienteLat: number | null = null;
  clienteLng: number | null = null;

  constructor(private clienteService: ClienteService, private router: Router) { }

  ngOnInit(): void {
    this.obterLocalizacaoCliente();
    this.carregarDashboard();
    this.carregarRestaurantes();
  }

  carregarDashboard(): void {
    this.clienteService.getDashboard().subscribe({
      next: (res: any) => {
        this.utilizador = res.utilizador;
        this.totalEncomendas = res.totalEncomendas;
        this.ultimasEncomendas = res.ultimasEncomendas;
      },
      error: (err) => {
        console.error('Erro ao carregar dashboard:', err);
        this.erro = 'Erro ao carregar informações do dashboard.';
      }
    });
  }

  carregarRestaurantes(): void {
    this.clienteService.getRestaurantes().subscribe({
      next: (res: any) => {
        console.table(res);
        console.log('RESTAURANTES:', res);
        this.restaurantes = res.filter((r: any) => r.validado);
      },
      error: (err) => {
        console.error('Erro ao carregar restaurantes:', err);
      }
    });
  }

  obterLocalizacaoCliente(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.clienteLat = position.coords.latitude;
          this.clienteLng = position.coords.longitude;
        },
        (error) => {
          console.warn('Não foi possível obter localização do cliente:', error);
          this.clienteLat = null;
          this.clienteLng = null;
        }
      );
    } else {
      this.clienteLat = null;
      this.clienteLng = null;
    }
  }

  restaurantesFiltrados(): any[] {
    let lista = this.restaurantes;
    if (this.filtroSelecionado) {
      lista = lista.filter(r => r.tipoCozinha === this.filtroSelecionado);
    }
    if (this.clienteLat !== null && this.clienteLng !== null) {
      lista = lista.filter(r => {
        if (!r.localizacao?.coordinates || typeof r.raioEntregaKm !== 'number') return false;
        const [restLng, restLat] = r.localizacao.coordinates; 
        const distancia = this.calcularDistanciaKm(this.clienteLat!, this.clienteLng!, restLat, restLng);
        return distancia <= r.raioEntregaKm;
      });
    }
    return lista;
  }

  calcularDistanciaKm(lat1: number, lng1: number, lat2: number, lng2: number): number {
    const R = 6371; 
    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLng = (lng2 - lng1) * Math.PI / 180;
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(lat1 * Math.PI / 180) *
        Math.cos(lat2 * Math.PI / 180) *
        Math.sin(dLng / 2) * Math.sin(dLng / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    this.router.navigate(['/login']);
  }

  getImagemCompleta(urlRelativa: string): string {
    const baseUrl = 'http://localhost:3000';
    return urlRelativa?.startsWith('/') ? `${baseUrl}${urlRelativa}` : urlRelativa;
  }

  abrirDetalhes(restaurante: any): void {
    this.clienteService.getRestaurantePorId(restaurante._id).subscribe({
      next: (res: any) => {
        this.restauranteSelecionado = res;
        const modal = new (window as any).bootstrap.Modal(document.getElementById('modalDetalhes'));
        modal.show();
      },
      error: (err) => {
        console.error('Erro ao buscar detalhes do restaurante:', err);
      }
    });
  }

  isAbertoAgora(restaurante: any): boolean {
    if (!restaurante || !restaurante.horarioFuncionamento) return false;

    const agora = new Date();
    const diaSemana = agora.toLocaleDateString('pt-PT', { weekday: 'long' });

    const hoje = restaurante.horarioFuncionamento.find(
      (h: any) => h.dia.toLowerCase() === diaSemana.toLowerCase()
    );

    if (!hoje || hoje.fechado) return false;

    const [hAbre, mAbre] = hoje.abre.split(':').map(Number);
    const [hFecha, mFecha] = hoje.fecha.split(':').map(Number);

    const horaAtual = agora.getHours() + agora.getMinutes() / 60;
    const horaAbertura = hAbre + mAbre / 60;
    const horaFecho = hFecha + mFecha / 60;

    return horaAtual >= horaAbertura && horaAtual <= horaFecho;
  }
}
