import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BoardService } from 'src/app/services/board.service';
import { TaskService } from 'src/app/services/task.service';

@Component({
  selector: 'app-edit-task-status',
  templateUrl: './edit-task-status.component.html',
  styleUrls: ['./edit-task-status.component.css']
})
export class EditTaskStatusComponent {
  selectedBoard: any;
  selectedTaskStatus: any;
  taskStatusName: string = '';

  constructor(private boardService: BoardService, private dialogRef: MatDialogRef<EditTaskStatusComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.selectedTaskStatus = data.taskStatus;
    console.log("Task Status: ", data.taskStatus);
    this.taskStatusName = data.taskStatus.taskStatusName;
  }

  onSubmit() {
    const kanbanId = this.data.kanbanId;
    const boardName: any = this.data.board.boardName;
    const taskStatusName: any = this.taskStatusName;
    const taskStatus: any = this.data.taskStatus;

    this.boardService.updateTaskStatusByName(kanbanId, boardName, taskStatusName, taskStatus).subscribe({
      next: (response) => {
        console.log("Response ", response);
      }
    });

    this.dialogRef.close();

  }

  deleteTaskStatus() {
    const kanbanId = this.data.kanbanId;
    const boardName: any = this.data.board.boardName;
    const taskStatusName: any = this.data.taskStatus.taskStatusName;

    this.boardService.deleteTaskStatusByName(kanbanId, boardName, taskStatusName).subscribe({
      next: (result: any) => {
        console.log("Deleted");
      }
    });

    this.dialogRef.close();
  }

}
