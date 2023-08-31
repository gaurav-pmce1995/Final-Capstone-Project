import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { UserProfileService } from 'src/app/services/user-profile.service';

@Component({
  selector: 'app-image-upload-dialog',
  templateUrl: './image-upload-dialog.component.html',
  styleUrls: ['./image-upload-dialog.component.css']
})
export class ImageUploadDialogComponent {
  /* This component is created by Gaurav */
  constructor(public dialogRef: MatDialogRef<ImageUploadDialogComponent>, private userProfileService:UserProfileService) {}

  onFileSelected(event: any) {
    // Handle the selected image file here
   
    const file: File = event.target.files[0];
    if (file) {
      // Do something with the selected image file
      // console.log('Selected file:', file);
      this.image = this.uploadImage(file);
      this.image='';
      this.dialogRef.close(file)
    }
  }
  closeDialog() {
    this.dialogRef.close();
  }

  image?: any = '';
   
  uploadImage(imageFile: File) {
    const formData = new FormData;
    formData.append("image", imageFile);
    this.userProfileService.uploadImage(formData).subscribe(
      (response) => {
        this.image = response;
        console.log("Imgae URL: ", response);
      },
      (error) => {
        console.log(this.image);
        console.log("Error: ", error);
      }
      
    );
  }
}

