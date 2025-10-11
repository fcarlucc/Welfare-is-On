import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RedirectService {

  private redirectTo: string | undefined
  private redirectExtras: any | undefined
  
  constructor(
    private router: Router
  ) {}

  setRedirect(route:string) {
    this.redirectTo = route
    console.log(`Redirect to: ${route}`)
    this.router.navigate([route])
  }

  getRedirect(): string | undefined {
    return this.redirectTo
  }

  setRedirectPlus(route: string, extras: any): void { 
    this.redirectTo = route
    this.redirectExtras = extras
    console.log(`Redirect to: ${route} with data:`, extras)
    this.router.navigate([route], this.redirectExtras) 
  }

  getRedirectPlus(key: string): any | undefined {
    const navigation = this.router.getCurrentNavigation()
    
    if (navigation?.extras?.state) {
      return navigation.extras.state[key]
    }
    return undefined
  }
  
  navigate(): void { 
    if (this.redirectTo) {
      this.router.navigate([this.redirectTo], this.redirectExtras)
      this.redirectTo = undefined
      this.redirectExtras = undefined
    }
  }
}