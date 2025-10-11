import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpHeaders } from '@angular/common/http';

import { ValidatorService } from '../../../services/validator/validator.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';
import { MapsService } from '../../../services/maps/maps.service';

@Component({
  selector: 'app-create-coach',
  standalone: true,
  imports: [ReactiveFormsModule, ErrorMsgFormsDirective],
  templateUrl: './create-coach.component.html',
  styleUrl: './create-coach.component.scss'
})
export class CreateCoachComponent implements OnInit {
  @ViewChild('fileInput', { static: false }) fileInput!: ElementRef
  createCoachForm!: FormGroup
  selectedFile: File | null = null;
  coordinates: { lat: number; lng: number } | null = null;

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService,
    private mapsService: MapsService
  ) {}

  ngOnInit(): void {
    this.createCoachForm = this.fb.group({
      firstName: ['', [this.validatorService.validateFirstName()]],
      lastName: ['', [this.validatorService.validateLastName()]],
      email: ['', [this.validatorService.validateEmail()]],
      password: ['', [this.validatorService.validatePassword()]],
      confirmPassword: ['', [this.validatorService.validateConfirmPassword('password')]],
      phoneNumber: ['', [this.validatorService.validatePhoneNumber()]],
      location: ['', [this.validatorService.requiredField()]],
      specialization: ['', [this.validatorService.requiredField()]],
      division: ['', [this.validatorService.requiredField()]],
      image: [null, [this.validatorService.requiredField()]]
    });
  }

  async onSubmit(): Promise<void> {
    if (this.createCoachForm.valid) {
      this.coordinates = await this.mapsService.getLatLong(this.createCoachForm.value.location);
      const coachDto = {
        firstName: this.createCoachForm.value.firstName,
        lastName: this.createCoachForm.value.lastName,
        email: this.createCoachForm.value.email,
        password: this.createCoachForm.value.password,
        phoneNumber: this.createCoachForm.value.phoneNumber.toString(),
        longitude : this.coordinates?.lng,
        latitude : this.coordinates?.lat,
        specialization: this.createCoachForm.value.specialization,
        division: this.createCoachForm.value.division
      };

      if (this.selectedFile) {
        console.log('File to upload:', this.selectedFile);
        console.log('File to upload:', this.selectedFile);
        const formData: FormData = new FormData();
        formData.append('coach', new Blob([JSON.stringify(coachDto)], { type: 'application/json' }));
        formData.append('file', this.selectedFile);
        const headers = new HttpHeaders({
          'Accept': 'application/json'
        });
        try {
          await this.apiService.post3('secure/api/auth/create-coach', formData, headers).toPromise();
          this.navigateTo('')
        } catch (error) {
          console.error('Create coach error', error);
        }
      } else {
        console.error('File input is required');
      }
    } else {
      this.createCoachForm.markAllAsTouched();
    }
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      // Debugging statement to check the file object
      // console.log('File selected:', this.selectedFile);
    }
  }

  navigateTo(route: string) {
    this.redirectService.setRedirect(route)
  }
}