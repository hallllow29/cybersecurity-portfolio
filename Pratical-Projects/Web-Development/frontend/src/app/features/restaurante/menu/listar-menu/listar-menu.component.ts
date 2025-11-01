import { Component, Inject, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { PratoService } from '../../../../core/services/prato.service';
import { MenuService } from '../../../../core/services/menu.service';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-listar-menu',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './listar-menu.component.html',
  styleUrls: ['./listar-menu.component.scss']
})
export class ListarMenuComponent implements OnInit {
  menus: any[] = [];
  pratos: any[] = [];
  selectedMenu: any;
  isBrowser: boolean;
  filtro = {
    nome: '',
    ordenarPor: '',
    localizacao: '',
  };

  constructor(
    private menuService: MenuService,
    private pratoService: PratoService,
    private router: Router,
    private toastr: ToastrService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  ngOnInit(): void {
    if (this.isBrowser) {
      this.menuService.listarMenusPrivado().subscribe({
        next: (data) => {
          console.log('Menus recebidos:', data);
          this.menus = data;
        },
        error: (err) => console.error('Erro ao buscar menus:', err)
      });
    }
  }

  verDetalhes(id: string): void {
    this.router.navigate(['/menu/detalhes', id]);
  }

  voltarAoDashboard(): void {
    const id = localStorage.getItem('userId');
    if (id) {
      this.router.navigate(['/restaurante/dashboard', id]);
    } else {
      console.error('ID do restaurante não encontrado no localStorage.');
    }
  }

  abrirModal(menu: any): void {
    this.selectedMenu = menu;
  }

  fecharModal(): void {
    this.selectedMenu = null;
  }

  editarMenu(id: string): void {
    this.router.navigate(['/menu/editar', id]);
  }

  apagarMenu(id: string): void {
    if (confirm('Tens a certeza que queres apagar este menu?')) {
      this.menuService.apagarMenu(id).subscribe({
        next: () => {
          this.toastr.success('Menu apagado com sucesso!', 'Sucesso');
          this.menus = this.menus.filter(menu => menu._id !== id);
        },
        error: (err) => {
          this.toastr.error('Erro ao apagar menu.', 'Erro');
          console.error('Erro ao apagar menu:', err);
        }
      });
    }
  }

  get menusFiltrados(): any[] {
    let resultado = [...this.menus];

    if (this.filtro.nome.trim()) {
      resultado = resultado.filter(menu =>
        menu.nome.toLowerCase().includes(this.filtro.nome.toLowerCase())
      );
    }

    if (this.filtro.localizacao.trim()) {
      resultado = resultado.filter(menu =>
        menu.restaurante?.localizacao?.toLowerCase().includes(this.filtro.localizacao.toLowerCase())
      );
    }

    if (this.filtro.ordenarPor === 'preco_asc') {
      resultado.sort((a, b) => (a.preco || 0) - (b.preco || 0));
    } else if (this.filtro.ordenarPor === 'preco_desc') {
      resultado.sort((a, b) => (b.preco || 0) - (a.preco || 0));
    }

    return resultado;
  }


}
