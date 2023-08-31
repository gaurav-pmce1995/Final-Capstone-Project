import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserProfile } from 'src/app/models/user-proflie/user-profile.model';
import { UserProfileService } from 'src/app/services/user-profile.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  userProfile?: any = {};

  signupForm: FormGroup; // Declare your form group
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  address: string = '';
  mobile: string = '';


  constructor(private userProfileService: UserProfileService, private fb: FormBuilder, private router: Router,) {
    this.signupForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(3)]],
      lastName: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      address: ['', Validators.required],
      mobile: ['', [Validators.required, Validators.minLength(10)]],
      // profilePictureUrl:['']
    });
  }

  ngOnInit(): void {
    this.getUserProfile();
  }

  getUserProfile() {
    this.userProfileService.getUserProfile().subscribe({
      next: (response) => {
        if (response !== null) {
          console.log("User Profile Response: ")
          console.log(response);
          this.userProfile = response;
          // this.userProfile! = response;
          this.signupForm.patchValue({
            firstName: this.userProfile?.firstName,
            lastName: this.userProfile?.lastName,
            // email: this.userProfile.email,
            address: this.userProfile?.address,
            mobile: this.userProfile?.mobile,
            // profilePictureUrl:this.userProfile.profilePictureUrl
          });
          // this.signupForm.get('email')?.setValue(this.userProfile?.email);
          this.signupForm.get('firstName')?.updateValueAndValidity(this.userProfile.firstName);
          this.signupForm.get('lastName')?.updateValueAndValidity(this.userProfile.lastName);
          this.signupForm.get('email')?.updateValueAndValidity(this.userProfile.email);
          this.signupForm.get('address')?.updateValueAndValidity(this.userProfile.address);
          this.signupForm.get('mobile')?.updateValueAndValidity(this.userProfile.mobile);
          console.log(this.userProfile?.email);
        }
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  sendUpdatedData() {
    console.log(this.signupForm.value);
    this.userProfileService.updateUserProfile(this.userProfile).subscribe({
      next: (response) => {
        console.log("Gauravvalaresponse" + response);
        // this.userProfile = response;
        console.log(this.userProfile);
        alert('Updation successfull!!');
        this.router.navigateByUrl('/dashboard');
      },
      error: (err) => {
        console.log(err);
        alert('Try Again')
      },
    });
  }
}
