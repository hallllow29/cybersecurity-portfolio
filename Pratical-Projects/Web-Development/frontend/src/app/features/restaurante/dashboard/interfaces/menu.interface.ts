export interface Menu {
  _id?: string;
  id?: string; // <- adiciona isto
  nome: string;
  descricao: string;
  imagem: string;
  doses: Dose[];
  disponivel: boolean;
}

export interface Dose {
  nome: string;
  preco: number;
}
