import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';

import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';
import { MapsService } from '../../../services/maps/maps.service';
import { ValidatorService } from '../../../services/validator/validator.service';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMessageApiDirective],
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  email!: string
  password!: string
  signInForm!: FormGroup

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private mapsService: MapsService,
    private authService: AuthService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  ngOnInit(): void {
    this.signInForm = this.fb.group({
      email: ['', [this.validatorService.requiredField()]],
      password: ['', [this.validatorService.requiredField()]]
    });
    // this.mapsService.getAddress(41.901610, 12.503200)  prova
    // this.mapsService.getLatLong('Via Marsala 29h, Roma')
  }

  onSubmit(): void {
    if (this.signInForm.valid) {
      this.email = this.signInForm.value.email
      this.password = this.signInForm.value.password
      console.log('Form submitted:', this.email, this.password)
      const formData = new FormData()
      formData.append('username', this.email)
      formData.append('password', this.password)
      this.apiService.post('api/auth/sign-in', formData)
        .subscribe({
          next: (response : any) => {
            console.log('SignIn successful', response)
            // const accessToken = response.access_token
            // if (accessToken) {
            //   this.authService.setTokenInfo(accessToken)
            //   this.mapsService.getUserPosition(this.email)
            //   this.navigateTo("/")
            // } else {
              sessionStorage.setItem('email', this.email)
              this.navigateTo('sign-in/2FA');
              this.signInForm.reset();
            // }
          },
          error: (error) => {
            console.error('SignIn error', error);
          }
        });
    } else {
      console.error('Form is invalid:', this.signInForm.errors)
    }
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }

  redirectToGoogle() {
    window.location.href = "https://localhost:8443/oauth2/authorization/google"
  }
}
