import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';
import { AuthService } from '../../../services/auth/auth.service';
import { MapsService } from '../../../services/maps/maps.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';

/**
 * Componente per la verifica dell'OTP (One-Time Password).
 * @export
 * @class OtpVerificationComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-otp-verification',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective, ErrorMessageApiDirective],
  templateUrl: './otp-verification.component.html',
  styleUrl: './otp-verification.component.scss'
})
export class OtpVerificationComponent implements OnInit {

  /**
   * OTP inserito dall'utente.
   * @type {number}
   * @memberof OtpVerificationComponent
   */
  otp!: number;

  /**
   * URL dell'API per la verifica dell'OTP.
   * @type {string}
   * @memberof OtpVerificationComponent
   */
  api!: string;

  /**
   * Path della route di navigazione.
   * @type {string}
   * @memberof OtpVerificationComponent
   */
  path!: string;

  /**
   * Form per la verifica dell'OTP.
   * @type {FormGroup}
   * @memberof OtpVerificationComponent
   */
  otpVerificationForm!: FormGroup;

  /**
   * Crea un'istanza di OtpVerificationComponent.
   * @param {FormBuilder} fb
   * @param {ActivatedRoute} route
   * @param {ApiService} apiService
   * @param {MapsService} mapsService
   * @param {AuthService} authService
   * @param {RedirectService} redirectService
   * @param {ValidatorService} validatorService
   * @memberof OtpVerificationComponent
   */
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private apiService: ApiService,
    private mapsService: MapsService,
    private authService: AuthService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * Recupera il path e l'API dalle route data e inizializza il form.
   * @memberof OtpVerificationComponent
   */
  ngOnInit(): void {
    this.otpVerificationForm = this.fb.group({
      otp: ['', [this.validatorService.validateOtp()]]
    });

    this.route.data.subscribe(data => {
      this.path = data['path'];
      if (this.path) {
        console.log('Path:', this.path);
      } else {
        console.log('No path retrieved from ActivatedRoute data');
      }
    });

    this.route.data.subscribe(data => {
      this.api = data['api'];
      if (this.api) {
        console.log('Api:', this.api);
      } else {
        console.log('No api retrieved from ActivatedRoute data');
      }
    });
  }

  /**
   * Metodo chiamato alla sottomissione del form per la verifica dell'OTP.
   * @memberof OtpVerificationComponent
   */
  onSubmit(): void {
    if (this.otpVerificationForm.valid) {
      this.otp = this.otpVerificationForm.value.otp;
      const email = sessionStorage.getItem('email');
      console.log('Otp', this.otp);
      console.log('current path:', this.route.snapshot.routeConfig?.path ?? '');

      this.apiService.post(this.api, {
        otp: this.otp,
        email: email
      }).subscribe({
        next: (response: any) => {
          console.log('Full Response:', response);
          console.log('api otp2Fa', this.api);

          if (this.api === 'api/auth/2FA') {
            const accessToken = response.access_token;
            console.log(email, accessToken);

            if (accessToken) {
              this.authService.setTokenInfo(accessToken);
              this.mapsService.getUserPosition(email!);
            }
          }

          this.navigateTo(this.path);
          this.otpVerificationForm.reset();
        },
        error: (error) => {
          console.error('Otp-verification error', error);
        }
      });
    } else {
      console.error('Form is invalid:', this.otpVerificationForm.errors);
    }
  }

  /**
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof OtpVerificationComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }

  /**
   * Richiede un nuovo OTP.
   * @memberof OtpVerificationComponent
   */
  resendOtp(): void {
    console.log('resent otp');
    const formData = new FormData();
    const email = sessionStorage.getItem('email');
    formData.append('email', email!);

    this.apiService.post('api/auth/refresh-otp', formData)
      .subscribe({
        next: (response) => {
          console.log('Otp successful', response);
          sessionStorage.removeItem('email');
          this.otpVerificationForm.reset();
        },
        error: (error) => {
          console.error('Otp-verification error', error);
        }
      });
  }
}
