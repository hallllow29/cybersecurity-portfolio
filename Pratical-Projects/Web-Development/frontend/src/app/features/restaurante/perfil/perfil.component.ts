import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RestauranteService } from '../../../core/services/restaurante.service';
import { Router } from '@angular/router';
import 'leaflet/dist/leaflet.css';
import * as L from 'leaflet';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.scss'
})
export class PerfilComponent implements OnInit {
  restaurante: any = null;

  constructor(private restauranteService: RestauranteService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.restauranteService.obterPerfil().subscribe({
      next: (res) => {
        this.restaurante = res.restaurante || res; // funciona nos dois casos
        this.restaurante.email = res.email || res.email || 'Não disponível';
        console.log('RESPOSTA DO PERFIL:', res);

        setTimeout(() => this.inicializarMapa(), 100); // espera pelo DOM
      },
      error: (err) => {
        console.error('Erro ao obter perfil:', err);
      }
    });
  }

  formatarHorario(dia: any): string {
    if (dia.fechado) return `${dia.dia} - Fechado`;
    if (!dia.abre || !dia.fecha) return `${dia.dia} - Horário não definido`;
    return `${dia.dia} : ${dia.abre} - ${dia.fecha}`;
  }

  voltar() {
    const id = localStorage.getItem('userId');
    this.router.navigate([`/restaurante/dashboard/${id}`]);
  }

  editar(): void {
    this.router.navigate(['/restaurante/editar']);
  }

  trackByMetodo(index: number, item: string): string {
    return item;
  }

  inicializarMapa(): void {
    if (!this.restaurante?.localizacao?.coordinates) return;

    const [lat, lng] = this.restaurante.localizacao.coordinates; // invertido aqui

    const map = L.map('map').setView([lat, lng], 15);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap',
    }).addTo(map);

    L.marker([lat, lng])
      .addTo(map)
      .bindPopup(`<b>${this.restaurante.nome}</b><br>${this.restaurante.morada}`)
      .openPopup();
  }

  getCapaUrl(): string {
    return this.restaurante?.capaUrl
      ? 'http://localhost:3000' + this.restaurante.capaUrl
      : '/assets/default-cover.jpg';
  }
}
