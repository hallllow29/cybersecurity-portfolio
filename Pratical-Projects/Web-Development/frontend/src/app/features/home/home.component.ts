import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { RestauranteService } from '../../core/services/restaurante.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  categorias = [
    { nome: 'Portuguesa', icon: '🇵🇹' },
    { nome: 'Italiana', icon: '🍕' },
    { nome: 'Japonesa', icon: '🍣' },
    { nome: 'Vegetariana', icon: '🥗' },
    { nome: 'Outro', icon: '🍲' }
  ];
  restaurantes: any[] = [];
  filtro: string = '';
  loading = true;
  erro: string | null = null;
  restauranteSelecionado: any = null;
  menusSelecionado: any[] = [];
  mostrarModalDetalhes = false;

  constructor(private restauranteService: RestauranteService) {}

  ngOnInit(): void {
    this.loading = true;
    this.restauranteService.obterRestaurantesPublicos().subscribe({
      next: (res) => {
        this.restaurantes = res;
        this.loading = false;
        this.erro = null;
      },
      error: (err) => {
        console.error('Erro ao carregar restaurantes públicos:', err);
        this.loading = false;
        this.erro = 'Erro ao carregar restaurantes. Tenta novamente mais tarde.';
      }
    });
  }

  restaurantesFiltrados() {
    if (!this.filtro) return this.restaurantes;
    return this.restaurantes.filter(r => r.tipoCozinha === this.filtro);
  }

  getImagemCompleta(urlRelativa: string): string {
    const baseUrl = 'http://localhost:3000';
    return urlRelativa?.startsWith('/') ? `${baseUrl}${urlRelativa}` : urlRelativa;
  }

  abrirDetalhes(restaurante: any): void {
    this.restauranteSelecionado = restaurante;
    this.menusSelecionado = [];
    this.mostrarModalDetalhes = true;
    fetch(`http://localhost:3000/api/menus?restaurante=${restaurante._id}`)
      .then(res => res.json())
      .then(menus => this.menusSelecionado = menus)
      .catch(() => this.menusSelecionado = []);
  }

  fecharModalDetalhes(): void {
    this.mostrarModalDetalhes = false;
    this.restauranteSelecionado = null;
    this.menusSelecionado = [];
  }
}
