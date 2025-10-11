import { Directive, Input, OnChanges, SimpleChanges, ElementRef, Renderer2 } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

@Directive({
  selector: '[appErrorMsgForms]',
  standalone: true
})
export class ErrorMsgFormsDirective implements OnChanges {

  @Input() formGroup!: FormGroup
  @Input() fields!: string[]

  private subscriptions: Subscription[] = []

  constructor(
    private el: ElementRef,
    private renderer: Renderer2
  ) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['formGroup'] || changes['fields']) {
      this.subscribeToFormErrors()
    }
  }

  private subscribeToFormErrors() {
    this.subscriptions.forEach(sub => sub.unsubscribe())
    this.subscriptions = []
    this.fields.forEach(field => {
      const formControl = this.formGroup.get(field)

      if (formControl) {
        const subscription = formControl.statusChanges.subscribe(() => this.updateErrorMessage(field));
        this.subscriptions.push(subscription)
      }
    });
  }

  private updateErrorMessage(field: string) {
    const formControl = this.formGroup.get(field)
    let errorMessage = ''

    if (formControl && formControl.errors) {
      const errorKey = Object.keys(formControl.errors)[0]
      console.log(`Error Key: ${errorKey}`)
      errorMessage = this.getErrorMessage(errorKey, field)
    }
    this.renderer.setProperty(this.el.nativeElement, 'innerHTML', '')
    const textNode = this.renderer.createText(errorMessage)
    this.renderer.appendChild(this.el.nativeElement, textNode)
  }

  private getErrorMessage(errorKey: string, field: string): string {
    switch (errorKey) {
      case 'required':
        return `* ${field} is required.`
      case 'invalidName':
        return `* ${field} must contain between 2 and 30 alphabetic characters.`
      case 'maxLength':
        return `* ${field} exceeds the maximum allowed length.`
      case 'minLength':
        return `* ${field} has not reached the minimum allowed length.`
      case 'invalidEmail':
        return `* The email address entered in ${field} is not valid.`
      case 'length':
        const passwordControl = this.formGroup.get('password')
        const minLength = 8
        const maxLength = 128
        
        if (passwordControl?.value.length < minLength) {
          return `* Password must be at least ${minLength} characters long.`
        } else if (passwordControl?.value.length > maxLength) {
          return `* Password cannot exceed ${maxLength} characters.`
        }
        return ''
      case 'missingUpperCase':
        return `* Password must contain at least one uppercase letter.`
      case 'missingLowerCase':
        return `* Password must contain at least one lowercase letter.`
      case 'missingNumber':
        return `* Password must contain at least one number.`
      case 'missingSpecialChar':
        return `* Password must contain at least one special character.`
      case 'disallowedSpecialChar':
        return `* Password contains disallowed special characters.`
      case 'passwordsMismatch':
        return `* Confirm password does not match the entered password.`
      case 'invalidAge':
        return `* The entered age is not valid. You must be at least 18 years old and no more than 80 years old.`
      case 'invalidAddress':
        return `* The address is not valid. It must be in the format: 'Street Name 123, City, Country' or empty.`;
      case 'invalidOtpCode':
        return `* The entered OTP code is not valid. It must be a 6-digit numeric code.`
      case 'invalidPhoneNumber':
        return `* The entered Phone Number is not valid. Example: '+393248765468'`
      case 'pastDate':
        return `* Selected Date must be today or in the future.`
      case 'invalidUrl':
        return `* The entered URL in ${field} is not valid. Please enter a valid URL.`;
      default:
        return `* Unknown error in ${field}.`
    }
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe())
  }
}