import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Board } from 'src/app/models/kanban/board.model';
import { BoardService } from 'src/app/services/board.service';
import { KanbanService } from 'src/app/services/kanban.service';
import { DialogData } from '../../dialog.data';

@Component({
  selector: 'app-add-board',
  templateUrl: './add-board.component.html',
  styleUrls: ['./add-board.component.css']
})
export class AddBoardComponent {

  boardName: string = '';

  constructor(
    private boardService: BoardService,
    private dialogRef: MatDialogRef<AddBoardComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) { }

  // kanbanId: any = this.kanbanService.selectedKanban?.id;

  onSubmit() {
    const newBoard: Board = {
      boardName: this.boardName
    };

    console.log(this.data.kanbanId);
    if (this.boardName) {
      this.boardService.createBoard(this.data.kanbanId, newBoard).subscribe({
        next: (response) => {
          console.log(response);
        }
      });
    }

    this.boardName = '';  //  empty field for next use


    this.dialogRef.close();
  }
}
