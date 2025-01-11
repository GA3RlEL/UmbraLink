import { Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { LandingLayoutComponent } from './layouts/landing-layout/landing-layout.component';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
import { MainAppComponent } from './main-app/main-app.component';
import { ConversationComponent } from './main-app/conversation/conversation.component';
import { authGuard } from './guard/auth.guard';
import { loginPageGuard } from './guard/login-page.guard';

export const routes: Routes = [
  {
    path: "",
    component: LandingLayoutComponent,
    children:
      [
        { path: '', component: LandingPageComponent, title: "UmbraLink | Home" },
        { path: 'login', component: LoginPageComponent, title: "UmbraLink | Login", canActivate: [loginPageGuard] }
      ]

  },
  {
    path: "app",
    component: AppLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', component: MainAppComponent, title: "UmbraLink | App" },
      { path: ':id', component: ConversationComponent, title: "UmbraLink | " }
    ]
  }
];
