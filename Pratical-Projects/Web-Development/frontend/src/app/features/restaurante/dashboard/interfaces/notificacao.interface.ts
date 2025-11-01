export interface Notificacao {
  encomendaId: string | undefined;
  _id?: string;
  id?: string; // fallback
  nomeCliente?: string;
  total?: number;
  tipo?: string;
  data?: Date;
  lida?: boolean;
  timestamp?: string | Date;
}
