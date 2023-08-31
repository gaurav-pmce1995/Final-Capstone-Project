import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { TaskComponentComponent } from './user-dashboard/task-component/task-component.component';
import { NotificationComponentComponent } from './user-dashboard/notification-component/notification-component.component';
import { UserProfileComponent } from './user-dashboard/user-profile/user-profile.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
 
  {
      path: "",
      component: LoginComponent, 
    },
  
    {
      path: "signup",
      component: SignupComponent
    },
  
    {
      path: "login",
      component: LoginComponent
    },
  
  
    {
      path: "dashboard",
      component: UserDashboardComponent, canActivate:[AuthGuard]
    },
  
    {
      path: "notification",
      component: NotificationComponentComponent, canActivate:[AuthGuard]
    },
  
    {
      path: "profile",
      component: UserProfileComponent, canActivate:[AuthGuard]
    },
  
    {
      path: "**",
      component: SignupComponent  //not found
    }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }



