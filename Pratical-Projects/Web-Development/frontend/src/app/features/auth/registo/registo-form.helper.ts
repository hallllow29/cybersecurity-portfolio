import { FormBuilder, Validators } from '@angular/forms';

export function buildRegistoForm(fb: FormBuilder) {
  return fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    conf_password: ['', Validators.required],
    tipo: ['', Validators.required],

    cliente_nome: [''],
    cliente_telefone: [''],
    cliente_nif: [''],
    cliente_morada: [''],

    restaurante_nome: [''],
    restaurante_nomeResponsavel: [''],
    restaurante_telefone: [''],
    restaurante_nif: [''],
    restaurante_morada: [''],
    restaurante_codigoPostal: [''],
    restaurante_tipoCozinha: [''],
    restaurante_tempoConfecao: [0],
    restaurante_tempoEntrega: [0],
    restaurante_raioEntrega: [0],
    restaurante_maxEncomendas: [1],
  });
}
