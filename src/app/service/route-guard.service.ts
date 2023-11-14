import { Injectable } from '@angular/core';
import { CanActivate, Router} from '@angular/router';
import { UserService } from './data/user.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService implements CanActivate {

  constructor(private userService: UserService, private router: Router) { }

  canActivate(): boolean {
    // Check if the user is logged in and has the 'MANAGER' role
    if (this.userService.isUserLoggedIn() && this.userService.getUserRole() === 'MANAGER') {
      return true; // Allow access to the route
    } else {
      // Redirect to the login page if the user is not logged in or does not have the 'MANAGER' role
      this.router.navigate(['/error']);
      return false;
    }
  }
}