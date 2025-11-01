import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';
import { HomeComponent } from './features/home/home.component';

export const appRoutes: Routes = [
	{
		path: '',
		loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent)
	},
	{ path: 'login', loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent) },
	{ path: 'registo', loadComponent: () => import('./features/auth/registo/registo.component').then(m => m.RegistoComponent) },

	// Cliente
	{
		path: 'sucesso',
		loadComponent: () => import('./features/cliente/sucesso.component').then(m => m.SucessoComponent), 
	},

	{
		path: 'cliente/dashboard',
		loadComponent: () => import('./features/cliente/dashboard/dashboard.component').then(m => m.DashboardComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'cliente' }
	},
	{
		path: 'cliente/perfil',
		loadComponent: () => import('./features/cliente/perfil-cliente/perfil-cliente.component').then(m => m.PerfilClienteComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'cliente' }
	},
		{
		path: 'cliente/mapa',
		loadComponent: () => import('./features/cliente/restaurantes-mapa/restaurantes-mapa.component').then(m => m.RestaurantesMapaComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'cliente' }
	},

	// Restaurante (rotas específicas primeiro!)
	{
		path: 'restaurante/encomendas',
		loadComponent: () => import('./features/restaurante/encomendas/listar-encomendas.component').then(m => m.ListarEncomendasComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'restaurante/dashboard/:id',
		loadComponent: () => import('./features/restaurante/dashboard/dashboard.component').then(m => m.DashboardRestauranteComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'restaurante/editar',
		loadComponent: () => import('./features/restaurante/perfil/editar/editar.component').then(m => m.EditarComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'perfil',
		loadComponent: () => import('./features/restaurante/perfil/perfil.component').then(m => m.PerfilComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},

	// Pratos
	{
		path: 'prato/adicionar',
		loadComponent: () => import('./features/restaurante/pratos/adicionar-prato/adicionar-prato.component').then(m => m.AdicionarPratoComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'prato/editar/:id',
		loadComponent: () => import('./features/restaurante/pratos/adicionar-prato/adicionar-prato.component').then(m => m.AdicionarPratoComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'prato/listar',
		loadComponent: () => import('./features/restaurante/pratos/listar-prato/listar-prato.component').then(m => m.ListarPratoComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'prato/detalhes/:id',
		loadComponent: () => import('./features/restaurante/pratos/detalhes-prato/detalhes-prato.component').then(m => m.DetalhesPratoComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},

	// Menus
	{
		path: 'menu/adicionar',
		loadComponent: () => import('./features/restaurante/menu/criar-menu/criar-menu.component').then(m => m.CriarMenuComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'menu/editar/:id',
		loadComponent: () => import('./features/restaurante/menu/criar-menu/criar-menu.component').then(m => m.CriarMenuComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'menu/listar',
		loadComponent: () => import('./features/restaurante/menu/listar-menu/listar-menu.component').then(m => m.ListarMenuComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},
	{
		path: 'menu/detalhes/:id',
		loadComponent: () => import('./features/restaurante/menu/menu-detalhes/menu-detalhes.component').then(m => m.MenuDetalhesComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'restaurante' }
	},

	// Admin
	{
		path: 'admin/dashboard',
		loadComponent: () => import('./features/admin/dashboard/dashboard.component').then(m => m.DashboardComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'admin' }
	},

	{
		path: 'restaurante/:id',
		loadComponent: () => import('./features/cliente/restaurante-menu/restaurante-menu.component').then(m => m.RestauranteMenuComponent),
		canActivate: [authGuard, roleGuard],
		data: { role: 'cliente' }
	},

	// Wildcard
	{ path: '**', redirectTo: 'login' }
];
