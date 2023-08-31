import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { FormControl, FormGroup , Validators} from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(
    private userService: UserService,
    private router: Router,
    // private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
  }
// signup data : userName, emailId, password
signupForm = new FormGroup({
  'email': new FormControl('', [Validators.required, Validators.email]),
  'username': new FormControl('', [Validators.required, Validators.minLength(3), Validators.pattern(/^[a-zA-Z]*$/)]),
  'password': new FormControl('', [Validators.required, Validators.minLength(6)]),
});

sendSignupData() {
  console.log(this.signupForm.value);
  this.userService.registerUser(this.signupForm.value).subscribe({
    next: (response) => {
      console.log(response);
        alert('Registration is a success');
        this.router.navigateByUrl('/login');
    },
    error: (err) => {
      console.log(err);
      alert('Try Again')
    },
  });
}

}