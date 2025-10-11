import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidatorService {

  constructor() {}

  // Validator for required field
  requiredField(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (value === null || value === undefined || value === '') {
        return { required: true };
      }
      return null;
    };
  }

  validateFirstName(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const name = control.value;
      if (!name) return null;
      return /^[a-zA-Z\s]{2,30}$/.test(name)
        ? null
        : { invalidName: true };
    };
  }

  validateLastName(): ValidatorFn {
    return this.validateFirstName(); // Reuse validateFirstName validator
  }

  validateEmail(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const email = control.value;
      const emailRegex = /^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      if (!email) {
        return { required: true };
      } else if (email.length > 75) {
        return { maxLength: true };
      } else if (email.length < 6) {
        return { minLength: true };
      } else if (!emailRegex.test(email)) {
        return { invalidEmail: true };
      }
      return null;
    };
  }

  validatePassword(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.value;
      const minLength = 8;
      const maxLength = 128;
      const hasUpperCase = /[A-Z]/.test(password);
      const hasLowerCase = /[a-z]/.test(password);
      const hasNumber = /[0-9]/.test(password);
      const hasSpecialChar = /[!@#$%^&*]/.test(password);
      const disallowedSpecialChars = /[<>{}[\]\\/]/.test(password);

      if (!password) {
        return { required: true };
      } else if (password.length < minLength) {
        return { minLength: true };
      } else if (password.length > maxLength) {
        return { maxLength: true };
      } else if (!hasUpperCase) {
        return { missingUpperCase: true };
      } else if (!hasLowerCase) {
        return { missingLowerCase: true };
      } else if (!hasNumber) {
        return { missingNumber: true };
      } else if (!hasSpecialChar) {
        return { missingSpecialChar: true };
      } else if (disallowedSpecialChars) {
        return { disallowedSpecialChar: true };
      }
      return null;
    };
  }

  validateConfirmPassword(passwordControlName: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const passwordControl = control.parent?.get(passwordControlName);
      const confirmPassword = control.value;

      if (!confirmPassword) {
        return { required: true };
      } else if (passwordControl && passwordControl.value !== confirmPassword) {
        return { passwordsMismatch: true };
      }
      return null;
    };
  }

  validateAge(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const dobString = control.value;

      if (!dobString) {
        return { required: true };
      }

      const dob = new Date(dobString);
      const today = new Date();
      let age = today.getFullYear() - dob.getFullYear();

      if (today < new Date(today.getFullYear(), dob.getMonth(), dob.getDate())) {
        age--;
      }

      return age >= 18 && age <= 80 ? null : { invalidAge: true };
    };
  }

  validateOtp(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const otpRegex = /^[0-9]{6}$/;
      return otpRegex.test(control.value) ? null : { invalidOtpCode: true };
    };
  }

  validateTitle(): ValidatorFn {
    return this.validateFirstName(); // Reuse validateFirstName validator
  }

  validateDescription(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const description = control.value;
      
      if (!description) {
        return { required: true };
      } else if (description.length < 10) {
        return { minLength: true };
      } else if (description.length > 100) {
        return { maxLength: true };
      } else if (!/^[a-zA-Z0-9\s.,;:?!()&'"\-]+$/.test(description)) {
        return { invalidCharacters: true };
      }
      
      return null;
    };
  }

  validateAddress(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const address = control.value;
      const addressRegex = /^(\s*|(\b[a-zA-Z\s]+\b\s*\d+,\s*\b[a-zA-Z\s]+\b,\s*\b[a-zA-Z\s]+\b))$/;

      if (!address) {
        return null;
      } else if (!addressRegex.test(address)) {
        return { invalidAddress: true };
      }

      return null;
    };
  }
  
  validatePrice(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const price = control.value;
      
      if (price === null || price === undefined) {
        return { required: true };
      } else if (typeof price !== 'number') {
        return { invalidType: true };
      } else if (price <= 0) {
        return { minPrice: true };
      } else if (price > 10000) {
        return { maxPrice: true };
      }
      
      return null;
    };
  }

  validateDiscount(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const discount = control.value;
      
      if (discount === null || discount === undefined) {
        return { required: true };
      } else if (typeof discount !== 'number') {
        return { invalidType: true };
      } else if (discount < 5 || discount > 95) {
        return { invalidDiscount: true };
      }
      
      return null;
    };
  }

  validatePhoneNumber(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const phoneNumber = control.value;
      const phoneNumberRegex = /^\+?[1-9]\d{1,14}$/;

      if (!phoneNumber) {
        return { required: true };
      } else if (!phoneNumberRegex.test(phoneNumber)) {
        return { invalidPhoneNumber: true };
      } else if (phoneNumber.length < 10) {
        return { minLength: { requiredLength: 10, actualLength: phoneNumber.length } };
      } else if (phoneNumber.length > 14) {
        return { maxLength: { requiredLength: 14, actualLength: phoneNumber.length } };
      }
      
      return null;
    };
  }

  validateDate(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const selectedDate = new Date(control.value);

      if (!selectedDate || isNaN(selectedDate.getTime())) {
        return { invalidDate: true };
      }

      const today = new Date();
      today.setHours(0, 0, 0, 0);

      return selectedDate >= today ? null : { pastDate: true };
    };
  }

  validateUrl(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const url = control.value;
      const urlRegex = /^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$/;

      if (!url) {
        return { required: true };
      } else if (!urlRegex.test(url)) {
        return { invalidUrl: true };
      }

      return null;
    };
  }
}
