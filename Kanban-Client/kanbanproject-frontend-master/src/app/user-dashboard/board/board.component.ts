import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Board } from 'src/app/models/kanban/board.model';
import { AddBoardComponent } from '../pop-up/add-board/add-board.component';
import { CdkDragDrop, transferArrayItem } from '@angular/cdk/drag-drop';
import { moveItemInArray } from '@angular/cdk/drag-drop';
import { Kanban } from 'src/app/models/kanban/kanban.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskStatus } from 'src/app/models/kanban/task-status.model';
import { Task } from 'src/app/models/kanban/task.model';
import { BoardService } from 'src/app/services/board.service';
import { TaskService } from 'src/app/services/task.service';
import { EditTaskComponent } from '../pop-up/edit-task/edit-task.component';
import { KanbanService } from 'src/app/services/kanban.service';
import { EditTaskStatusComponent } from '../pop-up/edit-task-status/edit-task-status.component';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css'],
})
export class BoardComponent implements OnInit {
  //@Input() kanban?: Kanban;
  @Input() selectedKanban?: Kanban;
  @Input() selectedBoard?: Board;

  taskStatusList?: any = [];
  taskStatus?: TaskStatus;
  // task?: Task;
  tasks?: any = [];

  taskForm!: FormGroup; //for task
  taskStatusForm!: FormGroup; //for task-status

  constructor(
    public dialog: MatDialog,
    private fb: FormBuilder,
    private kanbanService: KanbanService,
    private boardService: BoardService,
    private taskService: TaskService
  ) { }

  ngOnInit(): void {
    console.log('In ng On It');
    // Task Form
    this.taskForm = this.fb.group({
      title: [''],
    });

    // Task Status Form
    this.taskStatusForm = this.fb.group({
      taskStatusName: [''],
    });

    this.getAllTaskStatus();
  }

  ngOnChanges() {
    if (this.selectedKanban && this.selectedBoard) {
      this.taskStatusList = null;
      this.getAllTaskStatus();
    }
  }

  getBoard() {
    const kanbanId: any = this.selectedKanban?.id;
    const boardName: any = this.selectedBoard?.boardName;
    this.boardService.getBoardByName(kanbanId, boardName).subscribe({
      next: (response) => {
        this.selectedBoard = response;
      },
    });
  }

  addTask(taskStatusName: any) {
    const taskName = this.taskForm.value.title;
    const newTask: any = { title: taskName };
    const kanbanId: any = this.selectedKanban?.id;
    const boardName: any = this.selectedBoard?.boardName;

    if (taskName) {
      this.taskService
        .createTask(kanbanId, boardName, taskStatusName, newTask)
        .subscribe({
          next: (response) => {
            console.log(response);
            this.taskForm.setValue({ title: '' });
            this.getBoard();
          },
        });

    }
  }

  getAllTask(taskStatusName: any) {
    const kanbanId: any = this.selectedKanban?.id;
    const boardName: any = this.selectedBoard?.boardName;
    this.taskService.getAllTask(kanbanId, boardName, taskStatusName).subscribe({
      next: (response) => {
        console.log('Get all task');
        console.log(response);
        this.tasks = response;
      },
    });
  }

  openEditTaskDialog(board: Board, taskStatusName: string, task: Task) {
    const dialogRef = this.dialog.open(EditTaskComponent, {
      width: '300px',
      data: {
        kanbanId: this.selectedKanban?.id,
        board: board,
        taskStatusName: taskStatusName,
        task: {
          title: task.title,
          description: task.description,
          subtasks: task.subtasks,
          createDate: task.createDate,
          dueDate: task.dueDate,
          priority: task.priority,
          assignTo: task.assignTo,
          assignBy: task.assignBy,
          color: task.color,
        },
      },
      // disableClose: true,
    });

    dialogRef.afterClosed().subscribe({
      next: (response) => {
        this.getBoard();
      },
    });
  }

  createTaskStatus() {
    const statusName = this.taskStatusForm.value.taskStatusName;
    this.taskStatus = { taskStatusName: statusName };
    const kanbanId: any = this.selectedKanban?.id;
    const boardName: any = this.selectedBoard?.boardName;
    if (statusName) {
      this.boardService
        .createTaskStatus(kanbanId, boardName, this.taskStatus!)
        .subscribe({
          next: (response) => {
            this.taskStatusForm.setValue({
              taskStatusName: '',
            });
            this.taskStatusForm.setValue({ taskStatusName: '' });
            this.getBoard();
          },
        });
    }
  }

  getAllTaskStatus() {
    const kanbanId: any = this.selectedKanban?.id;
    const boardName: any = this.selectedBoard?.boardName;
    console.log('Board:' + boardName);

    this.boardService.getAllTaskStatus(kanbanId, boardName).subscribe({
      next: (response) => {
        console.log(response);
        this.taskStatusList = null;
        this.taskStatusList = response;
      },
      error: (err) => { },
    });
  }

  openEditTaskStatusDialog(board: Board, taskStatus: TaskStatus) {
    const dialogRef = this.dialog.open(EditTaskStatusComponent, {
      width: '300px',
      data: {
        kanbanId: this.selectedKanban?.id,
        board: board,
        taskStatus: taskStatus,
      },
      // disableClose: true,
    });

    dialogRef.afterClosed().subscribe({
      next: (response) => {
        this.getBoard();
      },
    });
  }

  drop(event: CdkDragDrop<Task[] | undefined>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(
        event.container.data!,
        event.previousIndex,
        event.currentIndex
      );
    } else {

      const taskToMove: Task = event.item.data;

      if(taskToMove.priority === 'Urgent' && event.previousContainer.id > event.container.id) {
        alert("Can't shift urgent task to previous status.");
        return;
      }

      transferArrayItem(
        event.previousContainer.data!,
        event.container.data!,
        event.previousIndex,
        event.currentIndex
      );
    }
    const kanbanId: any = this.selectedKanban?.id;
    const boardName: any = this.selectedBoard?.boardName;
    this.boardService
      .updateBoardByName(kanbanId, boardName, this.selectedBoard!)
      .subscribe({
        next: (response) => {
          console.log(response);
        },
      });
  }
}
