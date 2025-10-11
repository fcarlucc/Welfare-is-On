import { Routes } from '@angular/router';

import { NotFoundComponent } from './components/not-found/not-found.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';

import { ProfileComponent } from './components/user/profile/profile.component';
import { ModifyProfileComponent } from './components/user/modify-profile/modify-profile.component';
import { ShowCaseDataComponent } from './components/user/show-case-data/show-case-data.component';
import { PurchasedDataComponent } from './components/user/purchased-data/purchased-data.component';
import { BookCallComponent } from './components/user/book-call/book-call.component';
import { MeetingBoardComponent } from './components/user/meeting-board/meeting-board.component';
import { WelfareCoachComponent } from './components/user/welfare-coach/welfare-coach.component';
import { EconomicComponent } from './components/user/pillars/economic/economic.component';
import { PhysicalComponent } from './components/user/pillars/physical/physical.component';
import { PsychologicalComponent } from './components/user/pillars/psychological/psychological.component';
import { FamilyComponent } from './components/user/pillars/family/family.component';

import { SignInComponent } from './components/auth/sign-in/sign-in.component';
import { ForgotPasswordComponent } from './components/auth/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './components/auth/reset-password/reset-password.component';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';
import { SignUpSurveyComponent } from './components/auth/sign-up-survey/sign-up-survey.component';
import { OtpVerificationComponent } from './components/auth/otp-verification/otp-verification.component';

import { CreateServiceComponent } from './components/admin/create-service/create-service.component';
import { CreateCoachComponent } from './components/admin/create-coach/create-coach.component';

import { BookingBoardComponent } from './components/coach/booking-board/booking-board.component';
import { DailyAvailabilityComponent } from './components/coach/daily-availability/daily-availability.component';

import { authGuard } from './guards/auth/auth.guard';
import { authCoachGuard } from './guards/auth-coach/auth-coach.guard';
import { authAdminGuard } from './guards/auth-admin/auth-admin.guard';
import { leaveGuard } from './guards/leave/leave.guard';
import { NearMeComponent } from './components/user/near-me/near-me.component';

export const routes: Routes = [
    {
        path: '',
        title: 'Home',
        component: HomeComponent
    },
    //auth
    {
        path: 'sign-in',
        title: 'SignIn',
        component: SignInComponent 
    },
    {
        path: 'sign-in/2FA',
        title: 'auth2FA',
        component: OtpVerificationComponent,
        data: { api: 'api/auth/2FA' ,path: '' }
    },
    {
        path: 'forgot-password',
        title: 'Forgot Password',
        component: ForgotPasswordComponent 
    },
    {
        path: 'reset-password',
        title: 'Reset Password',
        component: ResetPasswordComponent 
    },
    {
        path: 'sign-up',
        title: 'SignUp',
        component: SignUpComponent 
    },
    {
        path: 'sign-up/survey',
        title: 'Survey',
        component: SignUpSurveyComponent
        //canDeactivate: [leaveGuard]
    },
    {
        path: 'sign-up/2FA',
        title: 'verify-email',
        component: OtpVerificationComponent,
        data: { api: 'api/auth/verify-user', path: 'sign-in' }
    },
    //user
    {
        path: 'economic',
        title: 'Economic Pillar',
        component: EconomicComponent,
        canActivate: [authGuard]
    },
    {
        path: 'physical',
        title: 'Physical Pillar',
        component: PhysicalComponent,
        canActivate: [authGuard]
    },
    {
        path: 'psychological',
        title: 'Psychological Pillar',
        component: PsychologicalComponent,
        canActivate: [authGuard]
    },
    {
        path: 'family',
        title: 'Family Pillar',
        component: FamilyComponent,
        canActivate: [authGuard]
    },
    {
        path: 'welfare-coach',
        title: 'Welfare Coach',
        component: WelfareCoachComponent,
        canActivate: [authGuard]
    },
    {
        path: 'showcase',
        title: 'Showcase',
        component: ShowCaseDataComponent,
        canActivate: [authGuard]
    },
    {
        path: 'purchased',
        title: 'Purchased',
        component: PurchasedDataComponent,
        canActivate: [authGuard]
    },
    {
        path: 'near-me',
        title: 'Near Me',
        component: NearMeComponent,
        canActivate: [authGuard]
    },
    {
        path: 'meeting-board',
        title: 'Meeting Board',
        component: MeetingBoardComponent,
        canActivate: [authGuard]
    },
    // {
    //     path: 'card-service',
    //     title: 'Card',
    //     component: CardServiceComponent
    // },
    // {
    //     path: 'card-coach',
    //     title: 'Card',
    //     component: CardCoachComponent
    // },
    {
        path: 'dashboard',
        title: 'Dashboard',
        component: DashboardComponent
    },
    {
        path: 'book-call',
        title: 'Book call',
        component: BookCallComponent,
        canActivate: [authGuard]
    },
    {
        path: 'profile',
        title: 'Profile',
        component: ProfileComponent,
        canActivate: [authGuard]
    },
    {
        path: 'modify-profile',
        title: 'Profile',
        component: ModifyProfileComponent,
        canActivate: [authGuard]
    },
    //admin
    {
        path: 'admin/create-service',
        title: 'Creation Service',
        component: CreateServiceComponent,
        canActivate: [authAdminGuard]
    },
    {
        path: 'create-coach',
        title: 'Creation Coach',
        component: CreateCoachComponent,
        canActivate: [authAdminGuard]
    },
    //coach
    {
        path: 'booking-board',
        title: 'Booking Board',
        component: BookingBoardComponent,
        canActivate: [authCoachGuard]
    },
    {
        path: 'daily-availability',
        title: 'Daily Availability',
        component: DailyAvailabilityComponent
    },
    {
        path: 'coach/create-service',
        title: 'Creation Service',
        component: CreateServiceComponent,
        canActivate: [authCoachGuard]
    },
    {
        path: 'coach/economic',
        title: 'Economic Pillar',
        component: EconomicComponent,
        canActivate: [authCoachGuard]
    },
    {
        path: 'coach/physical',
        title: 'Physical Pillar',
        component: PhysicalComponent,
        canActivate: [authCoachGuard]
    },
    {
        path: 'coach/psychological',
        title: 'Psychological Pillar',
        component: PsychologicalComponent,
        canActivate: [authCoachGuard]
    },
    {
        path: 'coach/family',
        title: 'Family Pillar',
        component: FamilyComponent,
        canActivate: [authCoachGuard]
    },
    {
        path: '404',
        component: NotFoundComponent
    },
    {
        path: '**',
        redirectTo : '/404'
    }
];
