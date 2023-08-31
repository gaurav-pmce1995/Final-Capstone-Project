import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Task } from '../models/kanban/task.model';


@Injectable({
  providedIn: 'root'
})
export class TaskService {

  baseUrl = "http://localhost:9000/api/v1/kanbans";

  constructor(private httpClient: HttpClient) { }

  createTask(kanbanId: string, boardName: string , statusName: string, task: Task) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, task, requestOptions);
  }

  getTaskByName(kanbanId: string, boardName: string, statusName: string, taskName: string) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks/${taskName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }

  getAllTask(kanbanId: string, boardName: string, statusName: string) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks/all`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }

  updateTask(kanbanId: string, boardName: string, statusName: string, taskName: string, task: Task) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks/${taskName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.put(url, task, requestOptions);
  }

  
  deleteTask(kanbanId: string, boardName: string, statusName: string, taskName: string) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks/${taskName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.delete(url, requestOptions);
  }

  assignTaskToUser(kanbanId: string, boardName: string, statusName: string, taskName: string, assigneeEmail: string) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks/assign-task/${assigneeEmail}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, requestOptions);
  }

  getAssignedTask(kanbanId: string, boardName: string, statusName: string, taskName: string) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks/assigned-task`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }

  moveTask(kanbanId: string, boardName: string, statusName: string, toStatusName: string, taskTitle: string) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${statusName}/tasks/move/${toStatusName}/${taskTitle}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, requestOptions);
  }
  

  private getRequestOptions() {
    const httpHeaders = new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    });
    let requestOptions = {headers : httpHeaders}
    return requestOptions;
  }
}
