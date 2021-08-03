import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { User } from '../model/user';
import { Service } from '../policy-service/service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  message: any;
  //private token:string;
  constructor(private service:Service, private router:Router, private appComponent:AppComponent) { }

  ngOnInit() {
      
  }

  onSubmit(user: User){
    this.service.authenticate(user).subscribe(data=>{
      this.router.navigate(["/home"])},
      error => {
        console.log(error.error.message)
        this.message=error.error.message;
        //this.message = "INVALID CREDENTIALS"
        //console.log(this.message)
    })
  }
  


}