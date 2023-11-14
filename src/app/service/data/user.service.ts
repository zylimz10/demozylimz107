import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { UserDto } from 'src/app/shared/models/user.dto';

export const TOKEN = 'token'
export const AUTHENTICATED_USER = 'authenticaterUser'
export const USER_ROLE = 'role'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/user'; // Update with your Spring Boot API URL

  constructor(private http: HttpClient) { }

  login(loginRequest: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, loginRequest).pipe(
      tap(response => {
        if (response && response.token) {
          sessionStorage.setItem(AUTHENTICATED_USER, response.username);
          sessionStorage.setItem(TOKEN, response.token);
          sessionStorage.setItem(USER_ROLE, response.role); // Store the user role
        }
      })
    );
  }

  getUser(userName: string): Observable<UserDto> {
    return this.http.get<UserDto>(`${this.apiUrl}/user/${userName}`);
  }

  getAuthenticatedUser(): string | null {
    return sessionStorage.getItem(AUTHENTICATED_USER);
  }

  getAuthToken() {
    return sessionStorage.getItem(TOKEN);
  }

  isUserLoggedIn() {
    return !!sessionStorage.getItem(AUTHENTICATED_USER);
  }
  
  getUserRole(): string | null {
    return sessionStorage.getItem(USER_ROLE);
  }
  logout() {
    sessionStorage.removeItem(AUTHENTICATED_USER);
    sessionStorage.removeItem(TOKEN);
    sessionStorage.removeItem(USER_ROLE);
  }

}