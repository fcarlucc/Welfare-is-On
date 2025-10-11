import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { ApiService } from '../../../services/api/api.service';
import { MapsService } from '../../../services/maps/maps.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-create-service',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective],
  templateUrl: './create-service.component.html',
  styleUrl: './create-service.component.scss'
})
export class CreateServiceComponent implements OnInit {
  @ViewChild('fileInput', { static: false }) fileInput!: ElementRef
  createServiceForm!: FormGroup
  showMap = false
  selectedFile: File | null = null
  coordinates: { lat: number; lng: number } | null = null

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private mapsService: MapsService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

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

  async onSubmit(): Promise<void> {
    if (this.createServiceForm.valid) {
      console.log('location = ' + this.createServiceForm.value.location)
      if (this.createServiceForm.value.location != null) {
        this.coordinates = await this.mapsService.getLatLong(this.createServiceForm.value.location);
      }
      // console.log('Coordinates:', this.coordinates);
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
        // console.log('File to upload:', this.selectedFile);
        const formData: FormData = new FormData();
        formData.append('service', new Blob([JSON.stringify(serviceDto)], { type: 'application/json' }));
        formData.append('file', this.selectedFile);
        const headers = new HttpHeaders({
          'Accept': 'application/json'
        });
        console.log('sending data = ' + serviceDto)
        try {
          await this.apiService.post3('api/services/create', formData, headers).toPromise();
          this.navigateTo('')
        } catch (error) {
          console.error('Create service error', error);
        }
      } else {
        console.error('File input is required');
      }
    } else {
      // Mark form controls as touched to display errors
      this.createServiceForm.markAllAsTouched();
    }
  }

  isVisible() {
    this.showMap = !this.showMap
    return this.showMap
  }

  triggerFileInput() {
    this.fileInput.nativeElement.click();
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      // Debugging statement to check the file object
      // console.log('File selected:', this.selectedFile);
    }
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }
}
