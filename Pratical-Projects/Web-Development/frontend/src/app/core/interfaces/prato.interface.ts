export interface Prato {
    _id?: string;
    id?: string;
    nome: string;
    descricao?: string;
    categoria: string;
    subcategoria: string;
    infoNutricional: {
        calorias: number | null;
        proteinas: number | null;
        hidratos: number | null;
        gorduras: number | null;
    };
    alergenios?: string[];
    tags?: string[];
    ingredientes?: string[];
    preco: number;
    imagem?: string;
    ativo?: boolean;
    doses?: { nome: string; preco: number }[];
    disponivel?: boolean;
}

export interface Dose {
    nome: string;
    preco: number;
}
