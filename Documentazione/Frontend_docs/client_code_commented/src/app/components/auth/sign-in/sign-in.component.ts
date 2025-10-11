import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';

import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';
import { MapsService } from '../../../services/maps/maps.service';
import { ValidatorService } from '../../../services/validator/validator.service';
import { AuthService } from '../../../services/auth/auth.service';

/**
 * Componente per la pagina di accesso (Sign-In).
 * Gestisce la visualizzazione del form di accesso e le relative operazioni di autenticazione.
 * @export
 * @class SignInComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMessageApiDirective],
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  /**
   * Email inserita dall'utente per l'accesso.
   * @type {string}
   * @memberof SignInComponent
   */
  email!: string;

  /**
   * Password inserita dall'utente per l'accesso.
   * @type {string}
   * @memberof SignInComponent
   */
  password!: string;

  /**
   * Form per la pagina di accesso.
   * @type {FormGroup}
   * @memberof SignInComponent
   */
  signInForm!: FormGroup;

  /**
   * Crea un'istanza di SignInComponent.
   * @param {FormBuilder} fb
   * @param {ApiService} apiService
   * @param {MapsService} mapsService
   * @param {AuthService} authService
   * @param {RedirectService} redirectService
   * @param {ValidatorService} validatorService
   * @memberof SignInComponent
   */
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private mapsService: MapsService,
    private authService: AuthService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * Inizializza il form di accesso con i controlli e le validazioni necessarie.
   * @memberof SignInComponent
   */
  ngOnInit(): void {
    this.signInForm = this.fb.group({
      email: ['', [this.validatorService.requiredField()]],
      password: ['', [this.validatorService.requiredField()]]
    });

    // Commenti di debug (non necessari in produzione)
    // this.mapsService.getAddress(41.901610, 12.503200); // Prova
    // this.mapsService.getLatLong('Via Marsala 29h, Roma');
  }

  /**
   * Metodo chiamato alla sottomissione del form di accesso.
   * Verifica la validità del form e invia una richiesta per l'accesso.
   * Se l'accesso ha successo, memorizza l'email nella sessione e naviga alla pagina di verifica 2FA.
   * @memberof SignInComponent
   */
  onSubmit(): void {
    if (this.signInForm.valid) {
      this.email = this.signInForm.value.email;
      this.password = this.signInForm.value.password;
      console.log('Form submitted:', this.email, this.password);

      const formData = new FormData();
      formData.append('username', this.email);
      formData.append('password', this.password);

      this.apiService.post('api/auth/sign-in', formData)
        .subscribe({
          next: (response: any) => {
            console.log('SignIn successful', response);
            // Codice commentato per gestione accesso e token (non in uso)
            // const accessToken = response.access_token;
            // if (accessToken) {
            //   this.authService.setTokenInfo(accessToken);
            //   this.mapsService.getUserPosition(this.email);
            //   this.navigateTo('/');
            // } else {
              sessionStorage.setItem('email', this.email);
              this.navigateTo('sign-in/2FA');
              this.signInForm.reset();
            // }
          },
          error: (error) => {
            console.error('SignIn error', error);
          }
        });
    } else {
      console.error('Form is invalid:', this.signInForm.errors);
    }
  }

  /**
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof SignInComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }

  /**
   * Reindirizza l'utente alla pagina di autenticazione Google.
   * @memberof SignInComponent
   */
  redirectToGoogle(): void {
    window.location.href = "https://localhost:8443/oauth2/authorization/google";
  }
}
