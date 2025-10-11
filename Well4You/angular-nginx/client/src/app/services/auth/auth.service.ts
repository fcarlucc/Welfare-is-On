import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private apiService: ApiService
  ) { }

  isAuthenthicated(): boolean {
    if (this.isAuthenthicatedUser() || this.isAuthenthicatedCoach() || this.isAuthenthicatedAdmin())
      return true
    return false
  }

  isAuthenthicatedUser(): boolean {
    // return true
    const role = sessionStorage.getItem('role')

    // console.log('role = ' + role)
    if (role && role === "ROLE_USER")
      return true
    return false
  }

  isAuthenthicatedCoach(): boolean {
    // return true
    const role = sessionStorage.getItem('role')

    if (role && role === "ROLE_COACH")
      return true
    return false
  }

  isAuthenthicatedAdmin(): boolean {
    // return true
    const role = sessionStorage.getItem('role')

    if (role && role === "ROLE_ADMIN")
      return true
    return false
  }

  setTokenInfo(accessToken: string) {
    const payload = JSON.parse(atob(accessToken.split('.')[1]))
    const email = payload.sub
    const userId = payload.userId
    const fullName = payload.fullName
    const role = payload.roles[0].authority

    sessionStorage.setItem('access_token', accessToken)
    sessionStorage.setItem('user_id', userId)
    sessionStorage.setItem('email', email)
    sessionStorage.setItem('full_name', fullName)
    sessionStorage.setItem('role', role)
  }


  signOut() {
    try {
      this.apiService.post('api/auth/logout', {}).toPromise();
      sessionStorage.removeItem('access_token')
      sessionStorage.removeItem('user_id')
      sessionStorage.removeItem('email')
      sessionStorage.removeItem('full_name')
      sessionStorage.removeItem('role')
    } catch (error) {
      console.error('logout error', error);
    }
  }

}
