import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AdminService, User, Restaurant, ActivityLog } from '../../../core/services/admin.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  utilizadores: User[] = [];
  restaurantes: Restaurant[] = [];
  logs: ActivityLog[] = [];
  showProfileMenu = false;
  loading = false;
  mostrarLogs = false;

  constructor(
    private router: Router,
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.adminService.obterDashboard().subscribe({
      next: (data) => {
        this.utilizadores = data.utilizadores;
        this.restaurantes = data.restaurantes;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar dados:', error);
        this.loading = false;
      }
    });
  }

  get pendentesCount(): number {
    return this.restaurantes.filter(r => !r.validado).length;
  }

  toggleProfileDropdown(): void {
    this.showProfileMenu = !this.showProfileMenu;
  }

  closeProfileMenu(): void {
    this.showProfileMenu = false;
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  removeUser(userId: string): void {
    if (confirm('Tens a certeza?')) {
      this.adminService.removerUtilizador(userId).subscribe({
        next: (response) => {
          console.log('Utilizador removido:', response.message);
          this.utilizadores = this.utilizadores.filter(u => u._id !== userId);
        },
        error: (error) => {
          console.error('Erro ao remover utilizador:', error);
        }
      });
    }
  }

  validateRestaurant(restaurantId: string): void {
    this.adminService.validarRestaurante(restaurantId).subscribe({
      next: (response) => {
        console.log('Restaurante validado:', response.message);
        const restaurant = this.restaurantes.find(r => r._id === restaurantId);
        if (restaurant) {
          restaurant.validado = true;
        }
      },
      error: (error) => {
        console.error('Erro ao validar restaurante:', error);
      }
    });
  }

  removeRestaurant(restaurantId: string): void {
    if (confirm('Tens a certeza?')) {
      this.adminService.removerRestaurante(restaurantId).subscribe({
        next: (response) => {
          console.log('Restaurante removido:', response.message);
          this.restaurantes = this.restaurantes.filter(r => r._id !== restaurantId);
        },
        error: (error) => {
          console.error('Erro ao remover restaurante:', error);
        }
      });
    }
  }

  carregarLogs(): void {
    this.adminService.obterLogs().subscribe({
      next: (logs) => this.logs = logs,
      error: (err) => console.error('Erro ao carregar logs:', err)
    });
  }

  toggleMostrarLogs(): void {
    this.mostrarLogs = !this.mostrarLogs;
    if (this.mostrarLogs && this.logs.length === 0) {
      this.carregarLogs();
    }
  }
}