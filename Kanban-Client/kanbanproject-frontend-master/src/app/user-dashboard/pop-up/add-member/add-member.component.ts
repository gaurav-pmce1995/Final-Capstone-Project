import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { KanbanService } from 'src/app/services/kanban.service';
import { DialogData } from '../../dialog.data';
import { AddBoardComponent } from '../add-board/add-board.component';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-add-member',
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.css']
})
export class AddMemberComponent {

  memberEmail: string = '';

  constructor(private kanbanService: KanbanService, private dialogRef: MatDialogRef<AddBoardComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData){}


  onSubmit() {
    const user: any = {
      email: this.memberEmail
    };
    console.log(user);
    this.kanbanService.assignKanbanToUser(this.data.kanbanId, user).subscribe({
      next: (response) => {
        console.log("Assign kanban to user--->",response);
      }
    });

    this.memberEmail = '';

    this.dialogRef.close();
  }

}
