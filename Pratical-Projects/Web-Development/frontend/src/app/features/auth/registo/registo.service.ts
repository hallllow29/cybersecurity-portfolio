import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class RegistoService {
  constructor(private http: HttpClient) {}

  registar(formData: FormData) {
    return this.http.post('http://localhost:3000/api/auth/registar', formData);
  }
}
function Injectable(arg0: { providedIn: string; }): (target: typeof RegistoService) => void | typeof RegistoService {
    throw new Error("Function not implemented.");
}

