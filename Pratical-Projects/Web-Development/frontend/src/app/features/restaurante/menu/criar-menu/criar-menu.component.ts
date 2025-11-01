import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule, NgIf, NgFor } from '@angular/common';
import { MenuService } from '../../../../core/services/menu.service';
import { PratoService } from '../../../../core/services/prato.service';
import { ActivatedRoute } from '@angular/router';
import { Menu } from '../../../../core/services/menu.service';

@Component({
  selector: 'app-criar-menu',
  standalone: true,
  imports: [CommonModule, FormsModule, NgIf, NgFor],
  templateUrl: './criar-menu.component.html',
  styleUrls: ['./criar-menu.component.scss']
})
export class CriarMenuComponent implements OnInit {
  menu = {
    nome: '',
    descricao: '',
    pratosSelecionados: [] as string[]
  };

  pratos: any[] = [];
  imagemSelecionada: File | null = null;
  imagemPreview: string | null = null;
  mensagemSucesso: string | null = null;
  mensagemErro: string | null = null;
  menuCriadoComSucesso: boolean = false;
  modoEdicao: boolean = false;

  constructor(
    private menuService: MenuService,
    private pratoService: PratoService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    const menuId = this.route.snapshot.paramMap.get('id');
    console.log('ID do menu vindo da rota:', menuId);
    if (menuId) {
      this.modoEdicao = true;
      this.carregarMenu(menuId);
    }
    this.pratoService.listarPratos().subscribe({
      next: (res) => {
        this.pratos = res;
      },
      error: (err) => {
        console.error('Erro ao carregar pratos', err);
      }
    });
  }

  carregarMenu(id: string): void {
    this.menuService.obterMenuPorId(id).subscribe({
      next: (res: Menu) => {
        this.menu.nome = res.nome;
        this.menu.descricao = res.descricao;
        this.menu.pratosSelecionados = res.pratos.map((p: any) => p._id || p.id);
        this.imagemPreview = res.imagem ? 'http://localhost:3000' + res.imagem : null;
      },
      error: (err) => {
        console.error('Erro ao carregar menu:', err);
        this.mensagemErro = 'Erro ao carregar menu para edição.';
      }
    });
  }

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.imagemSelecionada = file;
      this.imagemPreview = URL.createObjectURL(file);
    }
  }

  submeterMenu(): void {
    this.mensagemSucesso = null;
    this.mensagemErro = null;
  
    if (!this.menu.nome || this.menu.pratosSelecionados.length === 0) {
      this.mensagemErro = 'Preenche todos os campos obrigatórios.';
      return;
    }

    if (this.menu.pratosSelecionados.length > 10) {
      this.mensagemErro = 'O menu não pode ter mais de 10 pratos.';
      return;
    }
  
    const formData = new FormData();
    formData.append('nome', this.menu.nome);
    formData.append('descricao', this.menu.descricao || '');
    formData.append('pratos', JSON.stringify(this.menu.pratosSelecionados));
    if (this.imagemSelecionada) {
      formData.append('imagemMenu', this.imagemSelecionada);
    }
  
    const menuId = this.route.snapshot.paramMap.get('id');
  
    const observable = this.modoEdicao
      ? this.menuService.editarMenu(menuId!, formData)
      : this.menuService.criarMenu(formData);
  
    observable.subscribe({
      next: () => {
        this.mensagemSucesso = this.modoEdicao
          ? 'Menu atualizado com sucesso.'
          : 'Menu criado com sucesso.';
        this.menu = { nome: '', descricao: '', pratosSelecionados: [] };
        this.imagemSelecionada = null;
        this.imagemPreview = null;
        this.menuCriadoComSucesso = true;
      },
      error: (err) => {
        console.error('Erro ao submeter menu:', err);
        this.mensagemErro = err.error?.message || 'Erro ao guardar menu.';
      }
    });
  }

  voltarAoDashboard(): void {
    const id = localStorage.getItem('userId');
    this.router.navigate([`/restaurante/dashboard/${id}`]);
  }

  togglePratoSelecionado(id: string): void {
    const index = this.menu.pratosSelecionados.indexOf(id);
  
    if (index > -1) {
      this.menu.pratosSelecionados.splice(index, 1);
    } else {
      if (this.menu.pratosSelecionados.length >= 10) {
        this.mensagemErro = 'Só podes selecionar no máximo 10 pratos por menu.';
        return;
      }
  
      this.menu.pratosSelecionados.push(id);
    }
  }
  irParaListarMenus(): void {
    this.router.navigate(['/menu/listar']);
  }
}
