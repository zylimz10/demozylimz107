import { ActivatedRoute } from '@angular/router';
import { UserService } from '../service/data/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit{

  constructor(public userService
    : UserService, private route: ActivatedRoute) { }

  ngOnInit() {
    
  }

}
