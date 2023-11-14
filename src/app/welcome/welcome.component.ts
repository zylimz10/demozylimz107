//import org.springframework.boot.SpringApplication;
import { Component, OnInit } from '@angular/core';//'./app.component';
import { UserDto } from '../shared/models/user.dto';
import { UserService } from '../service/data/user.service';
import { ActivatedRoute } from '@angular/router';
//import { AppComponent } from '../app.component';

//@ComponentScan(
//      value = "com.in28minutes.springboot.web")
@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})

//public class SpringBootFirstWebApplication implements SomeInterface{
export class WelcomeComponent implements OnInit {

  user: UserDto | undefined;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Assuming you are retrieving the userName from the route parameters
    const userName = this.route.snapshot.paramMap.get('userName');

    if (userName) {
      this.userService.getUser(userName).subscribe(
        (data: UserDto) => {
          this.user = data;
        },
        (error) => {
          console.error('Error fetching user details:', error);
        }
      );
    } else {
      console.error('User name is not available in the route parameters.');
    }
  }
}

