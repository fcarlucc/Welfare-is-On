import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { User } from '../../../interfaces/user';

import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.scss'
})
export class SignUpComponent implements OnInit {

  user!: User
  signUpForm!: FormGroup

  constructor(
    private fb: FormBuilder,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

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
      console.log('Form submitted:', this.user)
      const jsonString = JSON.stringify(this.user);
      sessionStorage.setItem('user', jsonString);
      console.log(jsonString)
      this.redirectService.setRedirect('sign-up/survey')
      this.signUpForm.reset()
    } else {
      console.error('Form is invalid:', this.signUpForm.errors)
    }
  }

  navigateTo(route: string) {
    this.redirectService.setRedirect(route)
  }

  navigateToPlus(route: string, user: User) {
    this.redirectService.setRedirectPlus(route, { user })
  }

}
