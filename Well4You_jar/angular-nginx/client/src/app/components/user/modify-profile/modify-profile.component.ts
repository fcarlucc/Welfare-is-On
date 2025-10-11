import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';


@Component({
  selector: 'app-modify-profile',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './modify-profile.component.html',
  styleUrl: './modify-profile.component.scss'
})
export class ModifyProfileComponent implements OnInit {

  modifyProfileForm!: FormGroup
  selectedInterests: string[] = []

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    //fare get per sapere info utente
    this.modifyProfileForm = this.fb.group({
      maritalStatusName: ['', Validators.required],
      children: [false, Validators?.required],
      elderlyParents: ['', Validators.required],
      interests: ['']
    });
  }

  async onSubmit() {
    const userId = sessionStorage.getItem('user_id');
    if (this.modifyProfileForm.valid) {
      try {
        this.apiService.put('api/user/update-info', {
          userId: userId,
          interests: this.selectedInterests,
          maritalStatusName: this.modifyProfileForm.value.maritalStatusName,
          hasChildren: this.modifyProfileForm.value.children,
          hasElderlyParents: this.modifyProfileForm.value.elderlyParents
        }).toPromise();
      } catch (error) {
        console.error('logout error', error);
      }
      this.navigateTo('')
    }
  }

  toggleInterest(interest: string) {
    const index = this.selectedInterests.indexOf(interest)
    
    if (index === -1) {
      this.selectedInterests.push(interest);
    } else {
      this.selectedInterests.splice(index, 1);
    }
    this.modifyProfileForm.controls['interests'].setValue(this.selectedInterests.join(', '));
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }

}
