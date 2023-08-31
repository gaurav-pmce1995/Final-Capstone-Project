import { Component } from '@angular/core';
import { Board } from '../models/kanban/board.model';
import { Kanban } from '../models/kanban/kanban.model';
import { MatDialog } from '@angular/material/dialog';
import { KanbanService } from '../services/kanban.service';
import { AddKanbanComponent } from './pop-up/add-kanban/add-kanban.component';
import { AddBoardComponent } from './pop-up/add-board/add-board.component';
import { AddMemberComponent } from './pop-up/add-member/add-member.component';
import { BoardService } from '../services/board.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent {

  selectedBoard?: Board;
  // kanban?: Kanban;
  newKanban?: Kanban;
  selectedKanban?: Kanban; // The selected Kanban
  createdKanban?: any = [];
  assignedKanban?: any = [];

  constructor(public dialog: MatDialog, private kanbanService: KanbanService, private boardService: BoardService) {
    
    this.getAllKanban();
    this.getAssignedKanban();
  }

  ngOnInit() {
    
  }

  getAllKanban() {
    this.kanbanService.getAllKanban().subscribe({
      next: (response) => {
        if (response !== null) {
          this.createdKanban = response;
        }
      },
      error: (err) => {
        console.log("error: " + err);
      }
    });
  }
  
  getAssignedKanban() {
    this.kanbanService.getAssignedKanban().subscribe({
      next: (response) => {
        console.log("assigned kanbans",response);
        this.assignedKanban = response;
      },
      error: (err) => {
        console.log("Error", err);
      }
    });
  }

  goToBoard(kanban: Kanban, board: Board): void {
    this.selectedKanban = kanban;
    this.selectedBoard = board;
  }

  openAddKanbanDialog() {
    const dialogRef = this.dialog.open(AddKanbanComponent, {
      width: '300px',
      data: {
        kanbanName: null,
        messageToDisplay: 'Add',
      },
      // disableClose: true,
    });
    dialogRef.afterClosed().subscribe({
      next: (result: string) => {
        this.getAllKanban();
      }
    });
  }

  openAddMemberDialog(kanban: Kanban) {
    const dialogRef = this.dialog.open(AddMemberComponent, {
      width: '300px',
      data: {
        kanbanId: kanban.id
      }
    });
    dialogRef.afterClosed().subscribe({
      next: (response) => {
        this.getAllKanban();
      }
    });
  }

  openAddBoardDialog(kanban: Kanban) {
    const dialogRef = this.dialog.open(AddBoardComponent, {
      width: '300px',
      data: { kanbanId: kanban.id,
        kanbanName: null,
        messageToDisplay: 'Add',
      },
      // disableClose: true,
    });
    dialogRef.afterClosed().subscribe({
      next: (result) => {
        this.getAllKanban();
      }
    });
  }

  deleteKanban(kanban: Kanban) {
    const kanbanId: any = kanban.id;

    this.kanbanService.deleteKanbanById(kanbanId).subscribe({
      next: (response) => {
        console.log(response);
        this.getAllKanban();
      },
      error: (error) => {
        this.getAllKanban();
        console.log(error)
      }
    });
  }

  deleteBoard(kanban: Kanban, board: Board) {
    const kanbanId: any = kanban.id;
    const boardName: any = board.boardName;

    this.boardService.deleteBoardByName(kanbanId, boardName).subscribe({
      next: (response) => {
          console.log(response);
          this.getAllKanban();
      },
      error: (error) => {
        console.log(error);
        this.getAllKanban();
      }
    })
  }

}
