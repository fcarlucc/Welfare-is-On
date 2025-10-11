import { AfterViewInit, Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SectionsComponent } from './sections/sections.component';

import { RedirectService } from '../../services/redirect/redirect.service';

@Component({
  selector: 'app-pre-header',
  standalone: true,
  imports: [CommonModule, SectionsComponent],
  templateUrl: './pre-header.component.html',
  styleUrl: './pre-header.component.scss'
})
export class PreHeaderComponent implements AfterViewInit {

  isNavbarCollapsed = false;
  activeSection = '';
  isDropdownOpen = false;
  sections!: NodeListOf<HTMLElement>
  navLinks!: NodeListOf<HTMLElement>
  redirectChild: boolean = false

  constructor(
    private redirectService: RedirectService
  ) {}

  ngAfterViewInit(): void {
    this.sections = document.querySelectorAll('section');
    this.navLinks = document.querySelectorAll('.header a'); // Updated selector

    // Add event listeners to nav links for click events
    this.navLinks.forEach(link => {
      link.addEventListener('click', (event) => {
        event.preventDefault();
        const targetId = link.getAttribute('href')?.substring(1);
        if (targetId) {
          document.getElementById(targetId)?.scrollIntoView({ behavior: 'smooth' });
          this.activeSection = targetId;
          this.updateActiveLink();
        }
      });
    });

    const observerOptions = {
      root: null,
      rootMargin: '20px',
      threshold: 0.5 // Adjust threshold as needed
    };

    const observerCallback = (entries: IntersectionObserverEntry[]) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          this.activeSection = entry.target.id;
          this.updateActiveLink();
        }
      });
    };

    const observer = new IntersectionObserver(observerCallback, observerOptions);
    this.sections.forEach(section => observer.observe(section));

  }

  updateActiveLink() {
    this.navLinks.forEach(link => {
      if (link.getAttribute('href')?.substring(1) === this.activeSection) {
        link.classList.add('active')
      } else {
        link.classList.remove('active')
      }
    });
  }

  isSigning(): boolean {
    console.log('current path:', window.location.pathname)
    return [
      '/sign-in',
      '/sign-in/2FA',
      '/forgot-password',
      '/reset-password',
      '/sign-up',
      '/sign-up/survey',
      '/sign-up/2FA'
    ].includes(window.location.pathname)
  }

  goBack() {
    window.history.back()
    setTimeout(() => {
      window.location.reload()
    }, 100);
  }

  navigateTo(route: string) {
    this.redirectService.setRedirect(route)
  }

}