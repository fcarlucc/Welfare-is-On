import { Directive, Input, OnChanges, SimpleChanges, ElementRef, Renderer2 } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

/**
 * Direttiva per la visualizzazione dei messaggi di errore dei controlli dei moduli.
 * 
 * Questa direttiva si iscrive ai cambiamenti di stato dei controlli del modulo forniti
 * e aggiorna il testo dell'elemento DOM associato con i messaggi di errore appropriati.
 * 
 * @export
 * @class ErrorMsgFormsDirective
 * @implements {OnChanges}
 */
@Directive({
  selector: '[appErrorMsgForms]',
  standalone: true
})
export class ErrorMsgFormsDirective implements OnChanges {

  /** Gruppo di controlli del modulo associato alla direttiva */
  @Input() formGroup!: FormGroup;

  /** Lista dei nomi dei campi per cui mostrare i messaggi di errore */
  @Input() fields!: string[];

  /** Sottoscrizioni agli stati dei controlli del modulo */
  private subscriptions: Subscription[] = [];

  /**
   * Crea un'istanza di ErrorMsgFormsDirective.
   * 
   * @param {ElementRef} el - Riferimento all'elemento DOM a cui è applicata la direttiva.
   * @param {Renderer2} renderer - Renderer per manipolare l'elemento DOM.
   * @memberof ErrorMsgFormsDirective
   */
  constructor(
    private el: ElementRef,
    private renderer: Renderer2
  ) {}

  /**
   * Reagisce ai cambiamenti delle proprietà di input. Si iscrive ai cambiamenti di stato
   * dei controlli del modulo e aggiorna i messaggi di errore.
   * 
   * @param {SimpleChanges} changes - Cambiamenti delle proprietà di input.
   * @memberof ErrorMsgFormsDirective
   */
  ngOnChanges(changes: SimpleChanges) {
    if (changes['formGroup'] || changes['fields']) {
      this.subscribeToFormErrors();
    }
  }

  /**
   * Si iscrive ai cambiamenti di stato dei controlli del modulo e aggiorna i messaggi di errore.
   * 
   * @private
   * @memberof ErrorMsgFormsDirective
   */
  private subscribeToFormErrors() {
    // Pulisce le sottoscrizioni precedenti
    this.subscriptions.forEach(sub => sub.unsubscribe());
    this.subscriptions = [];

    // Iscrive ai cambiamenti di stato per ciascun campo
    this.fields.forEach(field => {
      const formControl = this.formGroup.get(field);

      if (formControl) {
        const subscription = formControl.statusChanges.subscribe(() => this.updateErrorMessage(field));
        this.subscriptions.push(subscription);
      }
    });
  }

  /**
   * Aggiorna il messaggio di errore per un campo specifico e lo visualizza nell'elemento DOM.
   * 
   * @private
   * @param {string} field - Nome del campo per cui aggiornare il messaggio di errore.
   * @memberof ErrorMsgFormsDirective
   */
  private updateErrorMessage(field: string) {
    const formControl = this.formGroup.get(field);
    let errorMessage = '';

    if (formControl && formControl.errors) {
      const errorKey = Object.keys(formControl.errors)[0];
      errorMessage = this.getErrorMessage(errorKey, field);
    }

    this.renderer.setProperty(this.el.nativeElement, 'innerHTML', '');
    const textNode = this.renderer.createText(errorMessage);
    this.renderer.appendChild(this.el.nativeElement, textNode);
  }

  /**
   * Restituisce il messaggio di errore basato sulla chiave dell'errore e sul campo.
   * 
   * @private
   * @param {string} errorKey - Chiave dell'errore.
   * @param {string} field - Nome del campo per cui restituire il messaggio di errore.
   * @return {string} Messaggio di errore.
   * @memberof ErrorMsgFormsDirective
   */
  private getErrorMessage(errorKey: string, field: string): string {
    switch (errorKey) {
      case 'required':
        return `* ${field} is required.`;
      case 'invalidName':
        return `* ${field} must contain between 2 and 30 alphabetic characters.`;
      case 'maxLength':
        return `* ${field} exceeds the maximum allowed length.`;
      case 'minLength':
        return `* ${field} has not reached the minimum allowed length.`;
      case 'invalidEmail':
        return `* The email address entered in ${field} is not valid.`;
      case 'length':
        const passwordControl = this.formGroup.get('password');
        const minLength = 8;
        const maxLength = 128;
        
        if (passwordControl?.value.length < minLength) {
          return `* Password must be at least ${minLength} characters long.`;
        } else if (passwordControl?.value.length > maxLength) {
          return `* Password cannot exceed ${maxLength} characters.`;
        }
        return '';
      case 'missingUpperCase':
        return `* Password must contain at least one uppercase letter.`;
      case 'missingLowerCase':
        return `* Password must contain at least one lowercase letter.`;
      case 'missingNumber':
        return `* Password must contain at least one number.`;
      case 'missingSpecialChar':
        return `* Password must contain at least one special character.`;
      case 'disallowedSpecialChar':
        return `* Password contains disallowed special characters.`;
      case 'passwordsMismatch':
        return `* Confirm password does not match the entered password.`;
      case 'invalidAge':
        return `* The entered age is not valid. You must be at least 18 years old and no more than 80 years old.`;
      case 'invalidAddress':
        return `* The address is not valid. It must be in the format: 'Street Name 123, City, Country' or empty.`;
      case 'invalidOtpCode':
        return `* The entered OTP code is not valid. It must be a 6-digit numeric code.`;
      case 'invalidPhoneNumber':
        return `* The entered Phone Number is not valid. Example: '+393248765468'`;
      case 'pastDate':
        return `* Selected Date must be today or in the future.`;
      case 'invalidUrl':
        return `* The entered URL in ${field} is not valid. Please enter a valid URL.`;
      default:
        return `* Unknown error in ${field}.`;
    }
  }

  /**
   * Pulisce le sottoscrizioni agli stati dei controlli del modulo quando la direttiva viene distrutta.
   * 
   * @memberof ErrorMsgFormsDirective
   */
  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
