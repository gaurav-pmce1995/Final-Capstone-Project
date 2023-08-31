import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Board } from '../models/kanban/board.model';
import { TaskStatus } from '../models/kanban/task-status.model';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  constructor(private httpClient:HttpClient) { }

  baseUrl = "http://localhost:9000/api/v1/kanbans"
  
  //change object to string

  createBoard(kanbanId:string, board: Board){
    const url = `${this.baseUrl}/${kanbanId}/boards`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, board, requestOptions);
  }

  getBoardByName(kanbanId:string, boardName: String) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }

  getAllBoards(kanbanId:string) {
    const url = `${this.baseUrl}/${kanbanId}/boards`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }


  updateBoardByName(kanbanId:string, boardName: string, board: Board) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.put(url, board, requestOptions);
  }

  deleteBoardByName(kanbanId: String, boardName: String) {
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.delete(url, requestOptions);
  }

  //---------->  for taskstatus

  

  createTaskStatus(kanbanId:String, boardName: string, taskStatus:TaskStatus){
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, taskStatus, requestOptions);
  }

  getTaskStatusByName(kanbanId: String, boardName: string, taskStatusName: String){
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${taskStatusName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }

  getAllTaskStatus(kanbanId: String, boardName: string){
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }


  updateTaskStatusByName(kanbanId: String, boardName: string, taskStatusName: string, taskStatus:TaskStatus){
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${taskStatusName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.put(url, taskStatus, requestOptions);
  }

  deleteTaskStatusByName(kanbanId: String, boardName: string, taskStatusName: String){
    const url = `${this.baseUrl}/${kanbanId}/boards/${boardName}/task-statuses/${taskStatusName}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.delete(url, requestOptions);
  }

  private getRequestOptions() {
    const httpHeaders = new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    });
    let requestOptions = {headers : httpHeaders}
    return requestOptions;
  }

}
