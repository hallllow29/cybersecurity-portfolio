import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestauranteService } from '../../../../core/services/restaurante.service';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-editar',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.scss'],
})
export class EditarComponent implements OnInit {
  editarForm: FormGroup;
  restaurante: any = null;
  logoFile: File | null = null;
  capaFile: File | null = null;

  metodosDisponiveis = [
    'Dinheiro',
    'MB Way',
    'Cartão de Crédito/Débito',
    'Multibanco',
    'Outro'
  ];

  horarioFuncionamento = [
    { dia: 'Segunda-feira', abre: '', fecha: '', fechado: false },
    { dia: 'Terça-feira', abre: '', fecha: '', fechado: false },
    { dia: 'Quarta-feira', abre: '', fecha: '', fechado: false },
    { dia: 'Quinta-feira', abre: '', fecha: '', fechado: false },
    { dia: 'Sexta-feira', abre: '', fecha: '', fechado: false },
    { dia: 'Sábado', abre: '', fecha: '', fechado: false },
    { dia: 'Domingo', abre: '', fecha: '', fechado: false }
  ];

  constructor(
    private fb: FormBuilder,
    private restauranteService: RestauranteService,
    private router: Router
  ) {
    this.editarForm = this.fb.group({
      nome: ['', Validators.required],
      pessoaResponsavel: ['', Validators.required],
      telefone: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      nif: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      morada: ['', Validators.required],
      tipoCozinha: [''],
      tempoConfecao: [0],
      tempoEntrega: [0],
      raioEntregaKm: [0],
      maxEncomendasPorHora: [1],
      codigoPostal: [''],
      metodosDePagamento: [[]]
    });
  }

  ngOnInit(): void {
    this.restauranteService.obterPerfil().subscribe({
      next: (res) => {
        this.restaurante = res;
        this.editarForm.patchValue({
          nome: res.nome,
          pessoaResponsavel: res.pessoaResponsavel,
          telefone: res.telefone,
          nif: res.nif,
          morada: res.morada,
          tipoCozinha: res.tipoCozinha,
          tempoConfecao: res.tempoConfecao,
          tempoEntrega: res.tempoEntrega,
          raioEntregaKm: res.raioEntregaKm,
          maxEncomendasPorHora: res.maxEncomendasPorHora,
          codigoPostal: res.codigoPostal || '',
          metodosDePagamento: res.metodosPagamento || []
        });

        if (res.horarioFuncionamento?.length === 7) {
          this.horarioFuncionamento = res.horarioFuncionamento;
        }
      },
      error: (err) => console.error('Erro ao carregar restaurante:', err),
    });
  }

  onFileChange(event: any, tipo: 'logo' | 'capa') {
    const file = event.target.files[0];
    if (tipo === 'logo') this.logoFile = file;
    if (tipo === 'capa') this.capaFile = file;
  }

  onToggleFechado(index: number): void {
    if (this.horarioFuncionamento[index].fechado) {
      this.horarioFuncionamento[index].abre = '';
      this.horarioFuncionamento[index].fecha = '';
    }
  }

  onToggleMetodoPagamento(metodo: string): void {
    const lista = this.editarForm.value.metodosDePagamento || [];

    if (lista.includes(metodo)) {
      this.editarForm.patchValue({
        metodosDePagamento: lista.filter((m: string) => m !== metodo),
      });
    } else {
      this.editarForm.patchValue({
        metodosDePagamento: [...lista, metodo],
      });
    }
  }

  onSubmit(): void {
    if (this.editarForm.invalid) return;

    // Validação lógica
    for (let dia of this.horarioFuncionamento) {
      if (!dia.fechado && dia.abre && dia.fecha && dia.abre >= dia.fecha) {
        alert(`Horário inválido para ${dia.dia}: hora de abertura deve ser antes da de fecho.`);
        return;
      }
    }

    const formData = new FormData();
    Object.entries(this.editarForm.value).forEach(([key, val]) => {
      if (val !== null && val !== undefined) {
        if (key === 'metodosDePagamento') {
          // Corrigir o nome do campo para o backend/modelo
          formData.append('metodosPagamento', JSON.stringify(val));
        } else {
          formData.append(key, val.toString());
        }
      }
    });

    formData.append('horarioFuncionamento', JSON.stringify(this.horarioFuncionamento));

    if (this.logoFile) formData.append('logo', this.logoFile);
    if (this.capaFile) formData.append('capa', this.capaFile);

    this.restauranteService.atualizarPerfil(formData).subscribe({
      next: () => {
        alert('Perfil atualizado com sucesso!');
        this.router.navigate(['/perfil']);
      },
      error: (err) => {
        console.error('Erro ao atualizar:', err);
        alert('Erro ao atualizar perfil.');
      },
    });
  }

  cancelar() {
    this.router.navigate(['/perfil']);
  }
}