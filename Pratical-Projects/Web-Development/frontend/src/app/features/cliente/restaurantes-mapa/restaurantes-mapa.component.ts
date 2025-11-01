import { Component, AfterViewInit, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ClienteService } from '../../../core/services/cliente.service';
import * as L from 'leaflet';

@Component({
  selector: 'app-restaurantes-mapa',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [ClienteService],
  templateUrl: './restaurantes-mapa.component.html',
  styleUrl: './restaurantes-mapa.component.scss',
})
export class RestaurantesMapaComponent implements OnInit, AfterViewInit {
  filtroNome: string = '';
  filtroDistancia: number = 100;
  userLat = 41.36701806145696;
  userLng = -8.194759189227414;

  mapa!: L.Map;
  marcadores: L.Marker[] = [];
  restaurantes: any[] = [];

  modalAberto = false;
  restauranteSelecionado: any = null;

  constructor(private clienteService: ClienteService, private router: Router) {
    // Configurar ícones do Leaflet
    this.configurarIconesLeaflet();
  }

  ngOnInit(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.userLat = position.coords.latitude;
          this.userLng = position.coords.longitude;
          this.carregarRestaurantes(); // só carrega depois de obter localização
          if (this.mapa) {
            this.atualizarMapa();
          }
        },
        (error) => {
          // Se não permitir, usa o valor default
          this.carregarRestaurantes();
          if (this.mapa) {
            this.atualizarMapa();
          }
        }
      );
    } else {
      this.carregarRestaurantes();
    }
  }

  ngAfterViewInit(): void {
    // Usar setTimeout para garantir que o DOM esteja renderizado
    setTimeout(() => {
      this.inicializarMapa();
    }, 100);
  }

  configurarIconesLeaflet(): void {
    // Corrigir problema dos ícones do Leaflet
    const iconRetinaUrl =
      'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png';
    const iconUrl =
      'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png';
    const shadowUrl =
      'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png';

    const iconDefault = L.icon({
      iconRetinaUrl,
      iconUrl,
      shadowUrl,
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      tooltipAnchor: [16, -28],
      shadowSize: [41, 41],
    });

    L.Marker.prototype.options.icon = iconDefault;
  }

  carregarRestaurantes(): void {
    this.clienteService.getRestaurantes().subscribe({
      next: (res: any) => {
        console.log('Restaurantes recebidos:', res);
        this.restaurantes = res.filter((r: any) => r.validado);
        this.atualizarDistancias();
        // Só atualizar o mapa se ele já estiver inicializado
        if (this.mapa) {
          this.atualizarMapa();
        }
      },
      error: (err) => {
        console.error('Erro ao carregar restaurantes:', err);
      },
    });
  }

  inicializarMapa(): void {
    // Verificar se o elemento existe
    const mapElement = document.getElementById('map');
    if (!mapElement) {
      console.error('Elemento do mapa não encontrado!');
      return;
    }

    try {
      // Inicializar o mapa
      this.mapa = L.map('map').setView([this.userLat, this.userLng], 14);

      // Adicionar camada de tiles
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
        maxZoom: 19,
      }).addTo(this.mapa);

      // Adicionar marcador do usuário
      L.marker([this.userLat, this.userLng])
        .addTo(this.mapa)
        .bindPopup('Você está aqui')
        .openPopup();

      // Forçar renderização do mapa
      setTimeout(() => {
        this.mapa.invalidateSize();
        // Atualizar marcadores dos restaurantes se já carregados
        if (this.restaurantes.length > 0) {
          this.atualizarMapa();
        }
      }, 200);

      console.log('Mapa inicializado com sucesso');
    } catch (error) {
      console.error('Erro ao inicializar mapa:', error);
    }
  }

  atualizarDistancias(): void {
    for (let restaurante of this.restaurantes) {
      if (
        restaurante.localizacao &&
        restaurante.localizacao.coordinates?.length === 2
      ) {
        const [lat, lng] = restaurante.localizacao.coordinates;
        const d = this.calcularDistancia(this.userLat, this.userLng, lat, lng);
        restaurante.lat = lat;
        restaurante.lng = lng;
        restaurante.distancia = Math.round(d);
      } else {
        console.warn('Restaurante sem localização válida:', restaurante);
      }
    }
  }

  atualizarMapa(): void {
    if (!this.mapa) {
      console.warn('Mapa não está inicializado');
      return;
    }

    console.log(
      'Atualizando mapa com restaurantes:',
      this.restaurantesFiltrados()
    );
    this.removerMarcadores();

    const restaurantesFiltrados = this.restaurantesFiltrados();

    for (let restaurante of restaurantesFiltrados) {
      if (restaurante.lat && restaurante.lng) {
        try {
          const marker = L.marker([restaurante.lat, restaurante.lng])
            .addTo(this.mapa)
            .bindPopup(
              `<strong>${restaurante.nome}</strong><br>${restaurante.distancia} km`
            );
          this.marcadores.push(marker);
        } catch (error) {
          console.error('Erro ao adicionar marcador:', error, restaurante);
        }
      }
    }
  }

  removerMarcadores(): void {
    for (let marker of this.marcadores) {
      if (this.mapa) {
        this.mapa.removeLayer(marker);
      }
    }
    this.marcadores = [];
  }

  restaurantesFiltrados() {
    return this.restaurantes.filter(
      (r) =>
        r.nome &&
        r.nome.toLowerCase().includes(this.filtroNome.toLowerCase()) &&
        r.distancia <= this.filtroDistancia
    );
  }

  calcularDistancia(
    lat1: number,
    lon1: number,
    lat2: number,
    lon2: number
  ): number {
    const R = 6371; // Raio da Terra em km
    const dLat = (lat2 - lat1) * (Math.PI / 180);
    const dLon = (lon2 - lon1) * (Math.PI / 180);
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(lat1 * (Math.PI / 180)) *
        Math.cos(lat2 * (Math.PI / 180)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // em KM
  }

  voltar(): void {
    this.router.navigate(['/cliente/dashboard']);
  }

  abrirModal(restaurante: any) {
    this.restauranteSelecionado = restaurante;
    this.modalAberto = true;
  }

  fecharModal() {
    this.modalAberto = false;
    this.restauranteSelecionado = null;
  }
}
