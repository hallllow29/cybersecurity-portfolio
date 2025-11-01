export interface Encomenda {
  _id: string;
  nomeCliente: string;
  total: number;
  status: 'pendente' | 'em preparação' | 'entregue' | 'cancelada';
  criadoEm: Date;
}
