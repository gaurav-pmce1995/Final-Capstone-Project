import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { UserService } from '../services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { UserProfileService } from '../services/user-profile.service';
import { ImageUploadDialogComponent } from './image-upload-dialog/image-upload-dialog/image-upload-dialog.component';
import { UserProfile } from '../models/user-proflie/user-profile.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  userprofile?: any = {}; 
  notificationCount:any;

  constructor(private userService: UserService, private dialog:MatDialog , private userProfileService: UserProfileService, private router:Router) {
    this.getUserProfile();
    this.notificationCount= localStorage.getItem('noticount');
   }

   goToNotification(){
    localStorage.setItem("noticount","0");
    console.log(localStorage.getItem("noticount"));
    this.router.navigateByUrl('/notification');
   }
  
   get isAuthorize():boolean {
    return this.userService.authorize();
   }

  logout() {
    this.userService.logout();
  }
  

  openImageUploadDialog() {
    const dialogRef = this.dialog.open(ImageUploadDialogComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe((file: File) => {
      if (file) {
        this.getUserProfile();
        console.log(this.userprofile);
         //this.image = this.uploadImage(file);
      }
    });
  }
  
  getUserProfile(){
    this.userProfileService.getUserProfile().subscribe({
      next: (response) => {
        if (response !== null) {
          this.userprofile = response;
        }
      },
      error: (err) => {
        console.log("error: " + err);
      }
    });
  }

  

  // image?: any = '';
  // uploadImage(imageFile: File) {
  //   const formData = new FormData;
  //   formData.append("image", imageFile);
  //   this.userProfileService.uploadImage(formData).subscribe(
  //     (response) => {
  //       this.image = response;
  //       console.log("Imgae URL: ", response);
  //       console.log("Mera vala URL: ", this.image);
  //     },
  //     (error) => {
  //       console.log(this.image);
  //       console.log("Error: ", error);
  //     }
      
  //   );
  // }


}
