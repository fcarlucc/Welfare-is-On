import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpHeaders } from '@angular/common/http';

import { ApiService } from '../../../services/api/api.service';
import { MapsService } from '../../../services/maps/maps.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';

/**
 * Componente per creare un nuovo servizio.
 * @export
 * @class CreateServiceComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-create-service',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective],
  templateUrl: './create-service.component.html',
  styleUrl: './create-service.component.scss'
})
export class CreateServiceComponent implements OnInit {
  /**
   * Input per il file.
   * @type {ElementRef}
   * @memberof CreateServiceComponent
   */
  @ViewChild('fileInput', { static: false }) fileInput!: ElementRef;

  /**
   * Form per creare un nuovo servizio.
   * @type {FormGroup}
   * @memberof CreateServiceComponent
   */
  createServiceForm!: FormGroup;

  /**
   * Flag per mostrare/nascondere la mappa.
   * @type {boolean}
   * @memberof CreateServiceComponent
   */
  showMap = false;

  /**
   * File selezionato dall'input file.
   * @type {(File | null)}
   * @memberof CreateServiceComponent
   */
  selectedFile: File | null = null;

  /**
   * Coordinate geografiche.
   * @type {({ lat: number; lng: number } | null)}
   * @memberof CreateServiceComponent
   */
  coordinates: { lat: number; lng: number } | null = null;

  /**
   * Crea un'istanza di CreateServiceComponent.
   * @param {FormBuilder} fb
   * @param {ApiService} apiService
   * @param {MapsService} mapsService
   * @param {RedirectService} redirectService
   * @param {ValidatorService} validatorService
   * @memberof CreateServiceComponent
   */
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private mapsService: MapsService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * @memberof CreateServiceComponent
   */
  ngOnInit(): void {
    this.createServiceForm = this.fb.group({
      category: ['', Validators.required],
      title: ['', this.validatorService.validateTitle()],
      description: ['', this.validatorService.validateDescription()],
      location: [null, this.validatorService.validateAddress()],
      price: ['', this.validatorService.validatePrice()],
      discount: ['', this.validatorService.validateDiscount()],
      image: [null, this.validatorService.requiredField()],
      url: ['', this.validatorService.validateUrl()]
    });
  }

  /**
   * Metodo chiamato alla sottomissione del form.
   * @returns {Promise<void>}
   * @memberof CreateServiceComponent
   */
  async onSubmit(): Promise<void> {
    if (this.createServiceForm.valid) {
      this.coordinates = await this.mapsService.getLatLong(this.createServiceForm.value.location);
      const serviceDto = {
        description : this.createServiceForm.value.description,
        title : this.createServiceForm.value.title,
        price : this.createServiceForm.value.price,
        discount : this.createServiceForm.value.discount,
        url : this.createServiceForm.value.url,
        longitude : this.coordinates?.lng,
        latitude : this.coordinates?.lat,
        pillarName : this.createServiceForm.value.category
      };

      if (this.selectedFile) {
        const formData: FormData = new FormData();
        formData.append('service', new Blob([JSON.stringify(serviceDto)], { type: 'application/json' }));
        formData.append('file', this.selectedFile);
        const headers = new HttpHeaders({
          'Accept': 'application/json'
        });
        console.log('sending data = ' + serviceDto);
        try {
          await this.apiService.post3('api/services/create', formData, headers).toPromise();
          this.navigateTo('');
        } catch (error) {
          console.error('Create service error', error);
        }
      } else {
        console.error('File input is required');
      }
    } else {
      // Segna tutti i controlli del form come toccati per mostrare gli errori
      this.createServiceForm.markAllAsTouched();
    }
  }

  /**
   * Cambia la visibilità della mappa.
   * @returns {boolean} Lo stato aggiornato della visibilità della mappa.
   * @memberof CreateServiceComponent
   */
  isVisible(): boolean {
    this.showMap = !this.showMap;
    return this.showMap;
  }

  /**
   * Attiva l'input file.
   * @memberof CreateServiceComponent
   */
  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }

  /**
   * Metodo chiamato al cambiamento del file nell'input.
   * @param {Event} event
   * @memberof CreateServiceComponent
   */
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  /**
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof CreateServiceComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
