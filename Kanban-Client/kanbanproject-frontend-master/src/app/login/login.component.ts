import { Component,OnInit } from '@angular/core';
import { FormControl, FormGroup , Validators} from '@angular/forms';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  constructor(private userService:UserService, private router:Router) { }
  ngOnInit(): void {  } 
  loginForm = new FormGroup({
    'usernameOrEmail': new FormControl('', [Validators.required]),
    'password': new FormControl('', [Validators.required])
  });


  responseData:any;

  sendLoginData(){
    console.log(this.loginForm.value);
    this.userService.loginCheck(this.loginForm.value).subscribe(
      response=>{
          this.responseData=response;
          this.userService.setToken(this.responseData.token);
       
          // localStorage.setItem("token",this.responseData.token);
            // Refer https://jscrambler.com/blog/working-with-angular-local-storage

          this.userService.login();
          this.router.navigateByUrl("/dashboard");
         
      })
  }
  
}