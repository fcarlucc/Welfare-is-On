import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { User } from '../../../interfaces/user';

import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';

/**
 * Componente per la pagina di registrazione (Sign-Up).
 * Gestisce la visualizzazione del form di registrazione e le operazioni di registrazione utente.
 * @export
 * @class SignUpComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.scss'
})
export class SignUpComponent implements OnInit {

  /**
   * Oggetto utente contenente le informazioni dell'utente registrato.
   * @type {User}
   * @memberof SignUpComponent
   */
  user!: User;

  /**
   * Form per la pagina di registrazione.
   * @type {FormGroup}
   * @memberof SignUpComponent
   */
  signUpForm!: FormGroup;

  /**
   * Crea un'istanza di SignUpComponent.
   * @param {FormBuilder} fb
   * @param {RedirectService} redirectService
   * @param {ValidatorService} validatorService
   * @memberof SignUpComponent
   */
  constructor(
    private fb: FormBuilder,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * Inizializza il form di registrazione con i controlli e le validazioni necessarie.
   * @memberof SignUpComponent
   */
  ngOnInit(): void {
    this.signUpForm = this.fb.group({
      firstName: ['', [this.validatorService.validateFirstName()]],
      lastName: ['', [this.validatorService.validateLastName()]],
      dob: ['', this.validatorService.validateAge()],
      email: ['', [this.validatorService.validateEmail()]],
      password: ['', [this.validatorService.validatePassword()]],
      confirmPassword: ['', [this.validatorService.validateConfirmPassword('password')]]
    });
  }

  /**
   * Metodo chiamato alla sottomissione del form di registrazione.
   * Verifica la validità del form e, se valido, memorizza i dati dell'utente nella sessione e naviga alla pagina del sondaggio.
   * @memberof SignUpComponent
   */
  onSubmit(): void {
    if (this.signUpForm.valid) {
      this.user = {
        firstName: this.signUpForm.value.firstName,
        lastName: this.signUpForm.value.lastName,
        dob: this.signUpForm.value.dob,
        email: this.signUpForm.value.email,
        password: this.signUpForm.value.password,
        confirmPassword: this.signUpForm.value.confirmPassword,
        isVerifiedEmail: false
      };
      console.log('Form submitted:', this.user);

      const jsonString = JSON.stringify(this.user);
      sessionStorage.setItem('user', jsonString);
      console.log(jsonString);

      this.redirectService.setRedirect('sign-up/survey');
      this.signUpForm.reset();
    } else {
      console.error('Form is invalid:', this.signUpForm.errors);
    }
  }

  /**
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof SignUpComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }

  /**
   * Naviga verso una determinata route e passa un oggetto utente come stato.
   * @param {string} route
   * @param {User} user
   * @memberof SignUpComponent
   */
  navigateToPlus(route: string, user: User): void {
    this.redirectService.setRedirectPlus(route, { user });
  }
}
