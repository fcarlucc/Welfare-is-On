import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per la modifica del profilo utente.
 * 
 * @export
 * @class ModifyProfileComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-modify-profile',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './modify-profile.component.html',
  styleUrls: ['./modify-profile.component.scss']
})
export class ModifyProfileComponent implements OnInit {

  /**
   * Modulo di modifica del profilo utente.
   * 
   * @type {FormGroup}
   * @memberof ModifyProfileComponent
   */
  modifyProfileForm!: FormGroup;

  /**
   * Lista degli interessi selezionati dall'utente.
   * 
   * @type {string[]}
   * @memberof ModifyProfileComponent
   */
  selectedInterests: string[] = [];

  /**
   * Crea un'istanza di ModifyProfileComponent.
   * 
   * @param {FormBuilder} fb - Servizio per la costruzione di moduli reattivi.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {RedirectService} redirectService - Servizio per la gestione delle redirezioni.
   */
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  /**
   * Inizializza il modulo di modifica del profilo utente.
   * 
   * @memberof ModifyProfileComponent
   */
  ngOnInit(): void {
    // Fare una chiamata GET per recuperare le informazioni dell'utente se necessario.
    this.modifyProfileForm = this.fb.group({
      maritalStatusName: ['', Validators.required],
      children: [false, Validators.required],
      elderlyParents: ['', Validators.required],
      interests: ['']
    });
  }

  /**
   * Gestisce l'invio del modulo di modifica del profilo.
   * Se il modulo è valido, invia una richiesta PUT per aggiornare le informazioni dell'utente.
   * 
   * @memberof ModifyProfileComponent
   */
  async onSubmit() {
    const userId = sessionStorage.getItem('user_id');
    if (this.modifyProfileForm.valid) {
      try {
        await this.apiService.put('api/user/update-info', {
          userId: userId,
          interests: this.selectedInterests,
          maritalStatusName: this.modifyProfileForm.value.maritalStatusName,
          hasChildren: this.modifyProfileForm.value.children,
          hasElderlyParents: this.modifyProfileForm.value.elderlyParents
        }).toPromise();
      } catch (error) {
        console.error('Error updating profile', error);
      }
      this.navigateTo('');
    }
  }

  /**
   * Gestisce la selezione/deselezione di un interesse.
   * Aggiunge o rimuove l'interesse dalla lista degli interessi selezionati e aggiorna il campo del modulo.
   * 
   * @param {string} interest - L'interesse da aggiungere o rimuovere.
   * @memberof ModifyProfileComponent
   */
  toggleInterest(interest: string) {
    const index = this.selectedInterests.indexOf(interest);
    
    if (index === -1) {
      this.selectedInterests.push(interest);
    } else {
      this.selectedInterests.splice(index, 1);
    }
    this.modifyProfileForm.controls['interests'].setValue(this.selectedInterests.join(', '));
  }

  /**
   * Esegue una redirezione verso la route specificata.
   * 
   * @param {string} route - La route verso cui redirigere.
   * @memberof ModifyProfileComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
