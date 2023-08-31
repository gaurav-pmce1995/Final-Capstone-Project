
import { Component, OnInit } from '@angular/core';
import { Notifications } from 'src/app/models/user-proflie/notifications';
import { NotificationService } from 'src/app/services/notification.service';
// import { JSONObject } from 'your-model-path'; // Import the JSONObject type


@Component({
  selector: 'app-notification-component',
  templateUrl: './notification-component.component.html',
  styleUrls: ['./notification-component.component.css']
})

export class NotificationComponentComponent implements OnInit{
 
  notifications?:any=[];
  


  constructor(public notificationService:NotificationService){
    this.notificationService.getAllNotification();
    
  }

  ngOnInit(): void {
    this.notificationService.getAllNotification().subscribe({
    next: (response) => {
      if (response !== null) {
        console.log(response);
        this.notifications= response;
        localStorage.setItem("noticount", this.notifications.length);
        console.log(this.notifications.length);
      }
    },
    error: (err) => {
      console.log("error: " + err);
    }
  });
    
  }
  }


