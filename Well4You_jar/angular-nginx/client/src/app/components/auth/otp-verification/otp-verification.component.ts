import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';
import { MapsService } from '../../../services/maps/maps.service';


import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-otp-verification',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective, ErrorMessageApiDirective],
  templateUrl: './otp-verification.component.html',
  styleUrl: './otp-verification.component.scss'
})
export class OtpVerificationComponent implements OnInit {

  otp!: number
  api!: string
  path!: string
  otpVerificationForm!: FormGroup

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute, //vedere se si puo spostare in redirectService gia provato ma non va
    private apiService: ApiService,
    private mapsService: MapsService,
    private authService: AuthService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  ngOnInit(): void {  //provare aftherViewInit per provare a spostare la funzione route.data.subscribe in service
    this.otpVerificationForm = this.fb.group({
      otp: ['', [this.validatorService.validateOtp()]]
    });
    this.route.data.subscribe(data => {
      this.path = data['path']
      if (this.path) {
        console.log('Path:', this.path)
      } else {
        console.log('No path retrieved from ActivatedRoute data')
      }
    });
    this.route.data.subscribe(data => {
      this.api = data['api']
      if (this.path) {
        console.log('Api:', this.api)
      } else {
        console.log('No api retrieved from ActivatedRoute data')
      }
    });
  }

  onSubmit(): void {
    if (this.otpVerificationForm.valid) {
      this.otp = this.otpVerificationForm.value.otp
      const email = sessionStorage.getItem('email')
      console.log('Otp', this.otp)
      console.log('current path:', this.route.snapshot.routeConfig?.path ?? '');
      this.apiService.post(this.api,
        {
          otp: this.otp,
          email: email
        }
      ).subscribe({
        next: (response: any) => {
          console.log('Full Response:', response)
          console.log('api otp2Fa', this.api)
          if (this.api === 'api/auth/2FA') {
            const accessToken = response.access_token
            console.log(email, accessToken)            
            if (accessToken) {
              this.authService.setTokenInfo(accessToken)
              this.mapsService.getUserPosition(email!)
            }
          }
          this.navigateTo(this.path)
          this.otpVerificationForm.reset()
        },
        error: (error) => {
          console.error('Otp-verification error', error)
        }
      });
    } else {
      console.error('Form is invalid:', this.otpVerificationForm.errors)
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
    this.apiService.post('api/auth/refresh-otp', formData)
      .subscribe({
        next: (response) => {
          console.log('Otp successful', response)
          sessionStorage.removeItem('email')
          // this.navigateTo(this.path)
          this.otpVerificationForm.reset()
        },
        error: (error) => {
          console.error('Otp-verification error', error)
        }
      });
  }
  
}
