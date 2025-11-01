import { FormBuilder, Validators, FormGroup } from '@angular/forms';

export function buildLoginForm(fb: FormBuilder): FormGroup {
  return fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });
}
