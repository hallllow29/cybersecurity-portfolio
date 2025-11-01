import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sucesso',
  templateUrl: './sucesso.component.html' // agora vais usar ficheiro HTML separado
})
export class SucessoComponent implements OnInit {
  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const sessionId = this.route.snapshot.queryParamMap.get('session_id');
    console.log('🧾 session_id recebido:', sessionId);

    if (!sessionId) {
      this.toastr.error('Sessão Stripe inválida.');
      this.router.navigate(['/']);
      return;
    }

    console.log('📤 A enviar POST para confirmar pagamento...');

    fetch('http://localhost:3000/api/stripe/confirmar', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      },
      body: JSON.stringify({ sessionId })
    })
    .then(res => res.json())
    .then(data => {
      console.log('✅ fetch confirmado:', data);

      if (data?.sucesso) {
        this.toastr.success('Pagamento confirmado!', 'Sucesso');
        // ✅ Espera 2 segundos e redireciona para o dashboard
        setTimeout(() => {
          this.router.navigate(['/cliente/dashboard']);
        }, 2000);
      } else {
        this.toastr.error('Falha ao confirmar pagamento.');
        this.router.navigate(['/login']);
      }
    })
    .catch(err => {
      console.error('❌ Erro no fetch:', err);
      this.toastr.error('Erro ao processar pagamento.');
      this.router.navigate(['/login']);
    });
  }
}
