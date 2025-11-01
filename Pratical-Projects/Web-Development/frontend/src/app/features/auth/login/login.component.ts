import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

import { AuthService } from '../../../core/services/auth.service';
import { buildLoginForm } from './login-form.helper';
import { LoginRequest } from './interfaces/login.model';

type UserRole = 'cliente' | 'restaurante' | 'admin';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
})
export class LoginComponent {
  loginForm: FormGroup;
  errors: Record<string, string> = {};
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.loginForm = buildLoginForm(this.fb);
  }

  get f() {
    return this.loginForm.controls;
  }

  

  onSubmit(): void {
    this.errors = {};

    if (this.loginForm.invalid) {
      this.validateFields();
      return;
    }

    this.isLoading = true;

    const credentials = this.loginForm.value as LoginRequest;

    this.authService.loginRequest(credentials).subscribe({
      next: (res) => {
        this.isLoading = false;
        this.authService.salvarLogin(res.token, res.userId, res.role);
        this.toastr.success('Login efetuado com sucesso!', 'Sucesso');
        this.redirectByRole(res.role, res.userId);
      },
      error: (err) => {
        this.isLoading = false;
        this.errors = err.error?.erros || {
          geral: 'Credenciais inválidas ou erro no servidor.',
        };
        this.toastr.error('Credenciais inválidas. Tenta novamente.', 'Erro de Autenticação');
      },
    });
  }

  private validateFields(): void {
    if (this.f['email'].invalid) {
      this.errors['email'] = this.f['email'].hasError('required')
        ? 'O email é obrigatório.'
        : 'Formato de email inválido.';
    }

    if (this.f['password'].hasError('required')) {
      this.errors['password'] = 'A password é obrigatória.';
    }
  }

  private redirectByRole(role: string, userId: string): void {
    const routes: Record<UserRole, string[]> = {
      cliente: ['/cliente/dashboard'],
      restaurante: [`/restaurante/dashboard/${userId}`],
      admin: ['/admin/dashboard'],
    };

    const targetRoute = routes[role as UserRole] ?? ['/'];
    this.router.navigate(targetRoute);
  }

}
