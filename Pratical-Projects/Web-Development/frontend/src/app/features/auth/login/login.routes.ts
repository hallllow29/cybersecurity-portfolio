import { Routes } from '@angular/router';

export const loginRoutes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./login.component').then(m => m.LoginComponent),
  },
];
