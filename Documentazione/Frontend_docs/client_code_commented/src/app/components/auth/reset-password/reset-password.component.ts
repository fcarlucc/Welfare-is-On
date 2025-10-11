import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';

/**
 * Componente per la reset della password.
 * Permette all'utente di impostare una nuova password dopo aver ricevuto un OTP (One-Time Password).
 * @export
 * @class ResetPasswordComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective, ErrorMessageApiDirective],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent implements OnInit {

  /**
   * OTP fornito dall'utente per la verifica.
   * @type {string}
   * @memberof ResetPasswordComponent
   */
  otp!: string;

  /**
   * Nuova password inserita dall'utente.
   * @type {string}
   * @memberof ResetPasswordComponent
   */
  password!: string;

  /**
   * Conferma della nuova password inserita dall'utente.
   * @type {string}
   * @memberof ResetPasswordComponent
   */
  confirmPassword!: string;

  /**
   * Form per la reset della password.
   * @type {FormGroup}
   * @memberof ResetPasswordComponent
   */
  resetPasswordForm!: FormGroup;

  /**
   * Crea un'istanza di ResetPasswordComponent.
   * @param {FormBuilder} fb
   * @param {ApiService} apiService
   * @param {RedirectService} redirectService
   * @param {ValidatorService} validatorService
   * @memberof ResetPasswordComponent
   */
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * Inizializza il form per la reset della password con i controlli e le validazioni necessarie.
   * @memberof ResetPasswordComponent
   */
  ngOnInit(): void {
    this.resetPasswordForm = this.fb.group({
      otp: ['', [this.validatorService.validateOtp()]],
      password: ['', [this.validatorService.validatePassword()]],
      confirmPassword: ['', [this.validatorService.validateConfirmPassword('password')]]
    });
  }

  /**
   * Metodo chiamato alla sottomissione del form per la reset della password.
   * Verifica la validità del form e invia una richiesta per la reset della password.
   * @memberof ResetPasswordComponent
   */
  onSubmit(): void {
    if (this.resetPasswordForm.valid) {
      this.otp = this.resetPasswordForm.value.otp;
      this.password = this.resetPasswordForm.value.password;
      this.confirmPassword = this.resetPasswordForm.value.confirmPassword;
      console.log('Submitted Password:', this.password, 'Confirm Password:', this.confirmPassword);

      this.apiService.post('api/auth/reset-password', { otp: this.otp, password: this.password, email: sessionStorage.getItem('email') })
        .subscribe({
          next: (response) => {
            console.log('Reset-password successful', response);
            this.navigateTo('sign-in');
            this.resetPasswordForm.reset();
            sessionStorage.removeItem('email');
          },
          error: (error) => {
            console.error('Reset-password error', error);
          }
        });
    } else {
      console.error('Form is invalid:', this.resetPasswordForm.errors);
    }
  }

  /**
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof ResetPasswordComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }

  /**
   * Richiede un nuovo OTP.
   * Utilizza l'email memorizzata nella sessione per inviare una nuova richiesta di OTP.
   * @memberof ResetPasswordComponent
   */
  resendOtp(): void {
    console.log('resent otp');
    const formData = new FormData();
    const email = sessionStorage.getItem('email');
    formData.append('email', email!);

    this.apiService.post('api/auth/forgot-password', formData)
      .subscribe({
        next: (response) => {
          console.log('Otp resent', response);
        },
        error: (error) => {
          console.error('Error resend otp', error);
        }
      });
  }
}
