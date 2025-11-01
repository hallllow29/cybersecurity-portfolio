import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';
import { ToastService } from '../../../core/services/toast.service';
import { buildRegistoForm } from './registo-form.helper';
import { RegistoFormModel, DiaHorario } from './interfaces/registo.model';

@Component({
  standalone: true,
  selector: 'app-registo',
  templateUrl: './registo.component.html',
  styleUrls: ['./registo.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
})
export class RegistoComponent {
  registoForm: FormGroup;
  errors: any = {};
  files: Record<string, File> = {};
  metodosSelecionados: string[] = [];

  metodosPagamento: string[] = [
    'Dinheiro',
    'MB Way',
    'Cartão de Crédito/Débito',
    'Multibanco',
    'Outro',
  ];

  diasDaSemana = [
    'Segunda-feira',
    'Terça-feira',
    'Quarta-feira',
    'Quinta-feira',
    'Sexta-feira',
    'Sábado',
    'Domingo',
  ];

  horarioFuncionamento: DiaHorario[] = this.diasDaSemana.map(dia => ({
    dia,
    abre: '',
    fecha: '',
    fechado: false,
  }));

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private toast: ToastService,
    private router: Router
  ) {
    this.registoForm = buildRegistoForm(this.fb);
    this.registoForm.get('tipo')?.valueChanges.subscribe(() => this.onTipoChange());
  }

  onTipoChange() {
    const tipo = this.registoForm.get('tipo')?.value;

    const camposRestaurante = [
      'restaurante_nome',
      'restaurante_nomeResponsavel',
      'restaurante_telefone',
      'restaurante_nif',
      'restaurante_morada',
      'restaurante_codigoPostal',
      'restaurante_tipoCozinha',
      'restaurante_tempoConfecao',
      'restaurante_tempoEntrega',
      'restaurante_raioEntrega',
      'restaurante_maxEncomendas',
    ];

    const camposCliente = [
      'cliente_nome',
      'cliente_telefone',
      'cliente_nif',
      'cliente_morada',
    ];

    if (tipo === 'cliente') {
      camposRestaurante.forEach(campo => {
        this.registoForm.get(campo)?.clearValidators();
        this.registoForm.get(campo)?.updateValueAndValidity();
      });
    }

    if (tipo === 'restaurante') {
      camposCliente.forEach(campo => {
        this.registoForm.get(campo)?.clearValidators();
        this.registoForm.get(campo)?.updateValueAndValidity();
      });
    }
  }

  onFileChange(event: Event, field: string) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.files[field] = input.files[0];
    }
  }

  onPagamentoChange(event: any) {
    const valor = event.target.value;
    if (event.target.checked) {
      this.metodosSelecionados.push(valor);
    } else {
      this.metodosSelecionados = this.metodosSelecionados.filter(v => v !== valor);
    }
  }

  onToggleFechado(index: number) {
    if (this.horarioFuncionamento[index].fechado) {
      this.horarioFuncionamento[index].abre = '';
      this.horarioFuncionamento[index].fecha = '';
    }
  }

  onSubmit() {
    const tipo = this.registoForm.get('tipo')?.value;

    if (this.registoForm.get('password')?.value !== this.registoForm.get('conf_password')?.value) {
      this.toast.error('A confirmação de senha não coincide.');
      return;
    }

    if (this.registoForm.invalid) {
      this.registoForm.markAllAsTouched();
      this.toast.warning('Preenche todos os campos obrigatórios.');
      return;
    }

    if (tipo === 'restaurante') {
      for (const dia of this.horarioFuncionamento) {
        if (!dia.fechado && (!dia.abre || !dia.fecha)) {
          this.toast.warning(`Preenche os horários de ${dia.dia}`);
          return;
        }
        if (!dia.fechado && dia.abre >= dia.fecha) {
          this.toast.warning(`Horário inválido em ${dia.dia}: abre depois de fechar`);
          return;
        }
      }
    }

    const formData = new FormData();

    Object.entries(this.registoForm.value).forEach(([key, valor]) => {
      if (valor !== null && valor !== undefined && valor !== '') {
        formData.append(key, valor.toString().trim());
      }
    });

    for (const key in this.files) {
      formData.append(key, this.files[key]);
    }

    if (tipo === 'restaurante') {
      formData.append('horarioFuncionamento', JSON.stringify(this.horarioFuncionamento));
    }

    this.metodosSelecionados.forEach(metodo => {
      formData.append('metodosPagamento[]', metodo);
    });

    this.auth.registarUtilizador(formData).subscribe({
      next: () => {
        this.toast.success('Conta criada com sucesso!');
        this.registoForm.reset();
        this.files = {};
        this.metodosSelecionados = [];
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.toast.error('Erro no registo. Verifica os dados.');
        this.errors = err.error?.erros || {};
      },
    });
  }

  trackByMetodo = (index: number, item: string) => item;

  get f() {
    return this.registoForm.controls;
  }
}
