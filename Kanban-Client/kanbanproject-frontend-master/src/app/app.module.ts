import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { UserProfileComponent } from './user-dashboard/user-profile/user-profile.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { TaskComponentComponent } from './user-dashboard/task-component/task-component.component';

import { NotificationComponentComponent } from './user-dashboard/notification-component/notification-component.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule, _MatTableDataSource } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClientModule } from '@angular/common/http';
import { MatRadioModule } from '@angular/material/radio';
import { MatExpansionModule } from '@angular/material/expansion';
import { HeaderComponent } from './header/header.component';
import { MatDialogModule } from "@angular/material/dialog";
import { AddKanbanComponent } from './user-dashboard/pop-up/add-kanban/add-kanban.component';
import { AddBoardComponent } from './user-dashboard/pop-up/add-board/add-board.component';
import { BoardComponent } from './user-dashboard/board/board.component';

import { MatMenuModule } from '@angular/material/menu';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { AddMemberComponent } from './user-dashboard/pop-up/add-member/add-member.component';
import { EditTaskComponent } from './user-dashboard/pop-up/edit-task/edit-task.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatBadgeModule } from '@angular/material/badge';


import {MatNativeDateModule} from '@angular/material/core';
import { EditTaskStatusComponent } from './user-dashboard/pop-up/edit-task-status/edit-task-status.component';
import { ImageUploadDialogComponent } from './header/image-upload-dialog/image-upload-dialog/image-upload-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    UserProfileComponent,
    UserDashboardComponent,
    TaskComponentComponent,
    NotificationComponentComponent,
    HeaderComponent,
    AddKanbanComponent,
    AddBoardComponent,
    BoardComponent,
    AddMemberComponent,
    EditTaskComponent,
    EditTaskStatusComponent,
    ImageUploadDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSortModule,
    MatCardModule,
    MatRadioModule,
    MatExpansionModule,
    MatDialogModule,
    DragDropModule,
    MatMenuModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatBadgeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
