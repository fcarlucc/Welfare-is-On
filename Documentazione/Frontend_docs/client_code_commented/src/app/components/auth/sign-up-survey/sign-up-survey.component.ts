import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { User } from '../../../interfaces/user';
import { Survey } from '../../../interfaces/survey';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';
import { MapsService } from '../../../services/maps/maps.service';
import { AuthService } from '../../../services/auth/auth.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { ErrorMessageApiDirective } from '../../../directives/error-msg-api/error-msg-api.directive';

/**
 * Componente per la pagina del sondaggio durante la registrazione dell'utente.
 * Gestisce la visualizzazione del modulo di sondaggio e l'invio delle risposte.
 * @export
 * @class SignUpSurveyComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-sign-up-survey',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective, ErrorMessageApiDirective],
  templateUrl: './sign-up-survey.component.html',
  styleUrls: ['./sign-up-survey.component.scss']
})
export class SignUpSurveyComponent implements OnInit {

  /**
   * Oggetto utente contenente le informazioni dell'utente.
   * @type {User}
   * @memberof SignUpSurveyComponent
   */
  user!: User;

  /**
   * Oggetto sondaggio contenente le risposte del sondaggio.
   * @type {Survey}
   * @memberof SignUpSurveyComponent
   */
  survey!: Survey;

  /**
   * Form per la pagina del sondaggio.
   * @type {FormGroup}
   * @memberof SignUpSurveyComponent
   */
  surveyForm!: FormGroup;

  /**
   * Lista degli interessi selezionati dall'utente.
   * @type {string[]}
   * @memberof SignUpSurveyComponent
   */
  selectedInterests: string[] = [];

  /**
   * Crea un'istanza di SignUpSurveyComponent.
   * @param {FormBuilder} fb
   * @param {ActivatedRoute} route
   * @param {ApiService} apiService
   * @param {MapsService} mapsService
   * @param {AuthService} authService
   * @param {RedirectService} redirectService
   * @memberof SignUpSurveyComponent
   */
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private apiService: ApiService,
    private mapsService: MapsService,
    private authService: AuthService,
    private redirectService: RedirectService
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * Inizializza il form del sondaggio e gestisce il recupero del token di accesso di Google.
   * @memberof SignUpSurveyComponent
   */
  ngOnInit(): void {
    this.surveyForm = this.fb.group({
      title: ['', Validators.required],
      maritalStatusName: ['', Validators.required],
      children: [false, Validators.required],
      elderlyParents: ['', Validators.required],
      interests: ['']
    });

    // Recupera il token di accesso di Google dai parametri della query.
    this.route.queryParams
      .subscribe(params => {
        if (params["token"] !== undefined) {
          sessionStorage.setItem('google_access_token', params["token"]);
          console.log(sessionStorage.getItem('google_access_token')); 
        }
      });
  }

  /**
   * Metodo chiamato alla sottomissione del modulo di sondaggio.
   * Verifica la validità del form e, a seconda della disponibilità del token di Google o delle informazioni dell'utente,
   * invia i dati al server e gestisce la risposta.
   * @memberof SignUpSurveyComponent
   */
  onSubmit(): void {
    const jsonString = sessionStorage.getItem('user');
    if (jsonString) {
      this.user = JSON.parse(jsonString);
      console.log('User from session storage ', this.user);
    }

    if (this.surveyForm.valid) {
      this.survey = {
        title: this.surveyForm.value.title,
        maritalStatusName: this.surveyForm.value.maritalStatusName,
        children: this.surveyForm.value.children,
        elderlyParents: this.surveyForm.value.elderlyParents,
        interests: this.surveyForm.value.interests
      };

      console.log('Ultimate user', this.user, sessionStorage.getItem('google_access_token'));

      if (sessionStorage.getItem('google_access_token') !== null) {
        console.log('Google token found');
        const googleAccessToken = sessionStorage.getItem('google_access_token');
        if (googleAccessToken) {
          const payload = JSON.parse(atob(googleAccessToken.split('.')[1]));
          const email = payload.sub;
          console.log(email);

          this.apiService.post('api/user/survey-google', 
            {
              email: email,
              titleName: this.survey.title.toUpperCase(),
              maritalStatusName: this.survey.maritalStatusName.toUpperCase(),
              hasChildren: this.survey.children,
              hasElderlyParents: this.survey.elderlyParents,
              interests: this.selectedInterests
            }
          ).subscribe({
            next: (response) => {
              console.log('Survey submitted successfully', response);
              this.surveyForm.reset();
              sessionStorage.setItem('access_token', googleAccessToken);
              sessionStorage.removeItem('google_access_token');
              this.authService.setTokenInfo(googleAccessToken);
              this.mapsService.getUserPosition(email);
              this.navigateTo('');
            },
            error: (error) => {
              console.error('Error submitting survey', error);
            }
          });
        }
      } else if (this.user) {
        console.log('User found');
        this.user.survey = this.survey;
        this.apiService.post('api/auth/sign-up',
          {
            firstName: this.user.firstName,
            lastName: this.user.lastName,
            dob: this.user.dob,
            email: this.user.email,
            password: this.user.password,
            titleName: this.user.survey.title.toUpperCase(),
            maritalStatusName: this.user.survey.maritalStatusName.toUpperCase(),
            hasChildren: this.user.survey.children,
            hasElderlyParents: this.user.survey.elderlyParents,
            interests: this.selectedInterests
          }
        ).subscribe({
          next: (response) => {
            sessionStorage.setItem('email', this.user.email);
            sessionStorage.removeItem('user');
            console.log('SignUp successful', response, 'email', sessionStorage.getItem('email'));
            this.surveyForm.reset();
            this.navigateTo('sign-up/2FA');
          },
          error: (error) => {
            console.error('SignUp error', error);
          }
        });
      } else {
        console.error('User is undefined');
        this.navigateTo('sign-in');
      }
    } else {
      console.error('Form is invalid:', this.surveyForm.errors);
    }
  }

  /**
   * Aggiunge o rimuove un interesse dalla lista degli interessi selezionati.
   * @param {string} interest
   * @memberof SignUpSurveyComponent
   */
  toggleInterest(interest: string): void {
    const index = this.selectedInterests.indexOf(interest);
    
    if (index === -1) {
      this.selectedInterests.push(interest);
    } else {
      this.selectedInterests.splice(index, 1);
    }
    this.surveyForm.controls['interests'].setValue(this.selectedInterests.join(', '));
  }

  /**
   * Verifica se il modulo può essere disattivato, basandosi sulla validità del modulo.
   * @returns {boolean}
   * @memberof SignUpSurveyComponent
   */
  canDeactivate(): boolean {
    console.log('canDeactivate called');
    console.log('Form status:', this.surveyForm.valid);
    return this.surveyForm.valid;
  }

  /**
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof SignUpSurveyComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
