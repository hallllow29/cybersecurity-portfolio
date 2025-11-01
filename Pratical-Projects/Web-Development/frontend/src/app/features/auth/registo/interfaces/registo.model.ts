export type TipoUtilizador = 'cliente' | 'restaurante';

export interface ClienteInfo {
  nome: string;
  telefone: string;
  nif: string;
  morada: string;
}

export interface RestauranteInfo {
  nome: string;
  nomeResponsavel: string;
  telefone: string;
  nif: string;
  morada: string;
  codigoPostal: string;
  tipoCozinha: string;
  tempoConfecao: number;
  tempoEntrega: number;
  raioEntrega: number;
  maxEncomendas: number;
}

export interface DiaHorario {
  dia: string;
  abre: string;
  fecha: string;
  fechado: boolean;
}

export interface RegistoFormModel {
  email: string;
  password: string;
  conf_password: string;
  tipo: TipoUtilizador;

  // Cliente
  cliente_nome?: string;
  cliente_telefone?: string;
  cliente_nif?: string;
  cliente_morada?: string;

  // Restaurante
  restaurante_nome?: string;
  restaurante_nomeResponsavel?: string;
  restaurante_telefone?: string;
  restaurante_nif?: string;
  restaurante_morada?: string;
  restaurante_codigoPostal?: string;
  restaurante_tipoCozinha?: string;
  restaurante_tempoConfecao?: number;
  restaurante_tempoEntrega?: number;
  restaurante_raioEntrega?: number;
  restaurante_maxEncomendas?: number;

  // Extras
  horarioFuncionamento?: DiaHorario[];
  metodosPagamento?: string[];
}
