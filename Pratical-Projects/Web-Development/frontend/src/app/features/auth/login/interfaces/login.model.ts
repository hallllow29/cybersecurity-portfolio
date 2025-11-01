export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userId: string;
  role: 'cliente' | 'restaurante' | 'admin';
}
