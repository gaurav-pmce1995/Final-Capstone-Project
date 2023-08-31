import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BoardService } from 'src/app/services/board.service';
import { TaskService } from 'src/app/services/task.service';
// import { DialogData } from '../../dialog.data';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent {
  selectedTask: any;
  taskName: string = "";

  constructor(private boardService: BoardService, private taskService: TaskService, private dialogRef: MatDialogRef<EditTaskComponent>, @Inject(MAT_DIALOG_DATA) public  data: any) {
    this.selectedTask = data.task;
    this.taskName = data.task.title;
  }

  ngOnInIt() {
  }

  onSubmit() {
    const kanbanId = this.data.kanbanId;
    const boardName: any = this.data.board.boardName;
    const taskStatusName: any = this.data.taskStatusName;
    const taskName: any = this.taskName;

    this.taskService.updateTask(kanbanId, boardName, taskStatusName, taskName, this.selectedTask).subscribe({
      next: (response) => {
        console.log("Updated Task: ", response);
      }
    });

    this.dialogRef.close();
  }

  deleteTask() {
    const kanbanId = this.data.kanbanId;
    const boardName: any = this.data.board.boardName;
    const taskStatusName: any = this.data.taskStatusName;
    const taskName: any = this.taskName;

    this.taskService.deleteTask(kanbanId, boardName, taskStatusName, taskName).subscribe(
      (response) => {
      }
    );
      
    this.dialogRef.close();
  }
}
