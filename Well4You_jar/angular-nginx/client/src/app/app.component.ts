import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { AuthService } from './services/auth/auth.service';

import { HeaderComponent } from './components/user/header/header.component';
import { HeaderCoachComponent } from './components/coach/header/header.component';
import { HeaderAdminComponent } from './components/admin/header/header.component';
import { PreHeaderComponent } from './components/pre-header/pre-header.component';
import { FooterComponent } from './components/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PreHeaderComponent, HeaderComponent, HeaderCoachComponent, HeaderAdminComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'client'

  constructor(
    private authService: AuthService
  ) {}

  isAuth() {
    // console.log(this.authService.isAuthenthicated())
    return this.authService.isAuthenthicated()
  }

  isAuthUser() {
    // console.log(this.authService.isAuthenthicatedUser())
    return this.authService.isAuthenthicatedUser()
  }

  isAuthCoach() {
    // console.log(this.authService.isAuthenthicatedCoach())
    return this.authService.isAuthenthicatedCoach()
  }

  isAuthAdmin() {
    // console.log(this.authService.isAuthenthicatedAdmin())
    return this.authService.isAuthenthicatedAdmin()
  }

}
