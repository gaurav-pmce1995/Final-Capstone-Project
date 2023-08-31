import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Kanban } from 'src/app/models/kanban/kanban.model';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-add-kanban',
  templateUrl: './add-kanban.component.html',
  styleUrls: ['./add-kanban.component.css']
})
export class AddKanbanComponent {
  kanbanName: string = '';

  constructor(
    public dialogRef: MatDialogRef<AddKanbanComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private kanbanService: KanbanService
  ) { }

  onSubmit() {
    const newKanban: Kanban = {
      kanbanName: this.kanbanName,
    };
    if(this.kanbanName){
    this.kanbanService.createKanban(newKanban).subscribe({
      next: (response) => {
      }
    });
  }
    //this.kanbanService.addKanban(newKanban);
    this.kanbanName = ''; // clear input field

    this.dialogRef.close();
  }
}
