import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { TaskService } from 'src/app/services/task.service';
import { FormControl, FormGroup , Validators} from '@angular/forms';
import { Task } from 'src/app/models/kanban/task.model';

@Component({
  selector: 'app-task-component',
  templateUrl: './task-component.component.html',
  styleUrls: ['./task-component.component.css']
})
export class TaskComponentComponent implements OnInit{
    constructor(private taskService:TaskService, private router:Router) { }
    ngOnInit(): void {  } 

    myForm = new FormGroup({
      description: new FormControl(''),
      priority: new FormControl(''),
      assignTo: new FormControl('')
    });
    
    taskdata!:Task;

    saveForm() {
     
    // this.taskService.updateTask(this.myForm.value).subscribe(
    //   response => {
    //     console.log(response);
    //     setTimeout(() => {
    //       alert('Registration is a success');
    //       this.router.navigateByUrl('/taskStatusView')
    //     }, 1000);
        
    //   }
    // );
  }

    
      }

    
