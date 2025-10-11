import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Servizio per la gestione delle validazioni dei moduli.
 * Fornisce diversi validatori che possono essere utilizzati per convalidare
 * campi come email, password, e altro ancora.
 */
@Injectable({
  providedIn: 'root'
})
export class ValidatorService {

  /**
   * Crea un'istanza del servizio `ValidatorService`.
   */
  constructor() {}

  /**
   * Restituisce un validatore per i campi obbligatori.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se il valore del controllo è nullo, indefinito o una stringa vuota.
   */
  requiredField(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (value === null || value === undefined || value === '') {
        return { required: true };
      }
      return null;
    };
  }

  /**
   * Restituisce un validatore per il nome.
   * Verifica che il nome contenga solo lettere e spazi e abbia una lunghezza compresa tra 2 e 30 caratteri.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se il nome non soddisfa i criteri specificati.
   */
  validateFirstName(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const name = control.value;
      if (!name) return null;
      return /^[a-zA-Z\s]{2,30}$/.test(name)
        ? null
        : { invalidName: true };
    };
  }

  /**
   * Restituisce un validatore per il cognome.
   * Riutilizza il validatore `validateFirstName`.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se il cognome non soddisfa i criteri specificati.
   */
  validateLastName(): ValidatorFn {
    return this.validateFirstName(); // Riutilizza il validatore validateFirstName
  }

  /**
   * Restituisce un validatore per l'email.
   * Verifica che l'email sia valida, abbia una lunghezza compresa tra 6 e 75 caratteri
   * e non superi le specifiche del formato email.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se l'email non soddisfa i criteri specificati.
   */
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

  /**
   * Restituisce un validatore per la password.
   * Verifica che la password soddisfi vari requisiti di complessità,
   * inclusi la lunghezza, la presenza di maiuscole, minuscole, numeri e caratteri speciali.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se la password non soddisfa i requisiti specificati.
   */
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

  /**
   * Restituisce un validatore per confermare la password.
   * Verifica che la password di conferma corrisponda alla password originale.
   * @param passwordControlName - Il nome del controllo della password originale.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se la password di conferma non corrisponde alla password originale.
   */
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

  /**
   * Restituisce un validatore per l'età.
   * Verifica che la data di nascita inserita indichi un'età compresa tra 18 e 80 anni.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se l'età non è valida.
   */
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

  /**
   * Restituisce un validatore per il codice OTP.
   * Verifica che il codice OTP sia composto da esattamente 6 cifre.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se il codice OTP non è valido.
   */
  validateOtp(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const otpRegex = /^[0-9]{6}$/;
      return otpRegex.test(control.value) ? null : { invalidOtpCode: true };
    };
  }

  /**
   * Restituisce un validatore per il titolo.
   * Riutilizza il validatore `validateFirstName`.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se il titolo non soddisfa i criteri specificati.
   */
  validateTitle(): ValidatorFn {
    return this.validateFirstName(); // Riutilizza il validatore validateFirstName
  }

  /**
   * Restituisce un validatore per la descrizione.
   * Verifica che la descrizione contenga solo caratteri validi e abbia una lunghezza
   * compresa tra 10 e 100 caratteri.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se la descrizione non soddisfa i criteri specificati.
   */
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

  /**
   * Restituisce un validatore per l'indirizzo.
   * Verifica che l'indirizzo sia valido secondo un formato predefinito.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se l'indirizzo non soddisfa il formato specificato.
   */
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
  
  /**
   * Restituisce un validatore per il prezzo.
   * Verifica che il prezzo sia un numero positivo e non superiore a 10.000.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se il prezzo non è valido.
   */
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

  /**
   * Restituisce un validatore per lo sconto.
   * Verifica che lo sconto sia un numero compreso tra 5 e 95.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se lo sconto non è valido.
   */
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

  /**
   * Restituisce un validatore per il numero di telefono.
   * Verifica che il numero di telefono soddisfi un formato internazionale valido
   * e che la lunghezza sia compresa tra 10 e 14 caratteri.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se il numero di telefono non soddisfa i criteri specificati.
   */
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

  /**
   * Restituisce un validatore per la data.
   * Verifica che la data sia valida e non sia una data passata.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se la data non è valida o è nel passato.
   */
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

  /**
   * Restituisce un validatore per l'URL.
   * Verifica che l'URL sia valido secondo un formato predefinito.
   * @returns Una funzione `ValidatorFn` che restituisce un errore di validazione
   *          se l'URL non soddisfa il formato specificato.
   */
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
