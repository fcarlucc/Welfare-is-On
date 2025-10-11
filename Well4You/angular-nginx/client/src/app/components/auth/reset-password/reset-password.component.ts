import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective, ErrorMessageApiDirective],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent implements OnInit {

  otp!: string
  password!: string
  confirmPassword!: string
  resetPasswordForm!: FormGroup

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  ngOnInit(): void {
    this.resetPasswordForm = this.fb.group({
      otp: ['', [this.validatorService.validateOtp()]],
      password: ['', [this.validatorService.validatePassword()]],
      confirmPassword: ['', [this.validatorService.validateConfirmPassword('password')]]
    });
  }

  onSubmit(): void {
    if (this.resetPasswordForm.valid) {
      this.otp = this.resetPasswordForm.value.otp
      this.password = this.resetPasswordForm.value.password
      this.confirmPassword = this.resetPasswordForm.value.confirmPassword
      console.log('Submitted Password:', this.password, 'Confirm Password:', this.confirmPassword)
      this.apiService.post('api/auth/reset-password', { otp: this.otp, password: this.password, email: sessionStorage.getItem('email') })
        .subscribe({
          next: (response) => {
            console.log('Reset-password successful', response)
            this.navigateTo('sign-in')
            this.resetPasswordForm.reset()
            sessionStorage.removeItem('email')
          },
          error: (error) => {
            console.error('Reset-password error', error);
          }
        });
    } else {
      console.error('Form is invalid:', this.resetPasswordForm.errors)
    }
  }

  navigateTo(route: string) {
    this.redirectService.setRedirect(route)
  }

  resendOtp() {
    console.log('resent otp')
    const formData = new FormData();
    const email = sessionStorage.getItem('email')
    formData.append('email', email!);
    this.apiService.post('api/auth/forgot-password', formData )
      .subscribe({
        next: (response) => {
          console.log('Otp resent', response)
        },
        error: (error) => {
          console.error('Error resend otp', error)
        }
      });
  }
  
}