import { UserService } from '../service/data/user.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginRequest: any = { userName: '', password: '' };
  invalidLogin = false;
  errorMessage = '';

  constructor(private userService: UserService, private router: Router) {}

  login() {
    this.userService.login(this.loginRequest).subscribe(
      (response) => {
        console.log('Login successful');
        this.router.navigate(['welcome', this.loginRequest.userName])
        // Redirect or perform any other actions on successful login
      },
      (error) => {
        console.error('Login failed', error);
        this.invalidLogin = true;
        this.errorMessage = 'Invalid username or password';
        // Handle login failure, display error message, etc.
      }
    );
  }
}
