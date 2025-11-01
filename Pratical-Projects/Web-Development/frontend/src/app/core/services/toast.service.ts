import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({ providedIn: 'root' })
export class ToastService {
  private Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 2500,
    timerProgressBar: true,
  });

  showInfo(msg: string, title?: string) {
    this.Toast.fire({
      icon: 'info',
      title: title ? `${title}: ${msg}` : msg,
    });
  }

  success(msg: string) {
    this.Toast.fire({ icon: 'success', title: msg });
  }

  error(msg: string) {
    this.Toast.fire({ icon: 'error', title: msg });
  }

  info(msg: string) {
    this.Toast.fire({ icon: 'info', title: msg });
  }

  warning(msg: string) {
    this.Toast.fire({ icon: 'warning', title: msg });
  }
}
