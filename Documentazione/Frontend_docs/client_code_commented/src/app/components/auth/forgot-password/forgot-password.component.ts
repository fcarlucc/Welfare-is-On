import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';

/**
 * Componente per la gestione del reset della password.
 * @export
 * @class ForgotPasswordComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective, ErrorMessageApiDirective],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.scss'
})
export class ForgotPasswordComponent implements OnInit {

  /**
   * Email dell'utente.
   * @type {string}
   * @memberof ForgotPasswordComponent
   */
  email!: string;

  /**
   * Form per il reset della password.
   * @type {FormGroup}
   * @memberof ForgotPasswordComponent
   */
  forgotPasswordForm!: FormGroup;

  /**
   * Crea un'istanza di ForgotPasswordComponent.
   * @param {FormBuilder} fb
   * @param {ApiService} apiService
   * @param {RedirectService} redirectService
   * @param {ValidatorService} validatorService
   * @memberof ForgotPasswordComponent
   */
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * @memberof ForgotPasswordComponent
   */
  ngOnInit(): void {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [this.validatorService.validateEmail()]]
    });
  }

  /**
   * Metodo chiamato alla sottomissione del form.
   * @memberof ForgotPasswordComponent
   */
  onSubmit(): void {
    if (this.forgotPasswordForm.valid) {
      this.email = this.forgotPasswordForm.value.email;
      const formData = new FormData();
      formData.append('email', this.email);
      console.log('Submitted Email:', this.email);
      this.apiService.post('api/auth/forgot-password', formData)
        .subscribe({
          next: (response) => {
            console.log('Forgot-password successful', response);
            sessionStorage.setItem('email', this.email);
            this.navigateTo('reset-password');
            this.forgotPasswordForm.reset();
          },
          error: (error) => {
            console.error('Forgot-password error', error);
          }
        });
    } else {
      console.error('Form is invalid:', this.forgotPasswordForm.errors);
    }
  }

  /**
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof ForgotPasswordComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
