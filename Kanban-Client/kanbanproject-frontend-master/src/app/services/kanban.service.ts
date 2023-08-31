import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Kanban } from '../models/kanban/kanban.model';

@Injectable({
  providedIn: 'root'
})
export class KanbanService {

  selectedKanban?: Kanban;


  //---------------------------

  baseUrl = "http://localhost:9000/api/v1/kanbans"

  constructor(private httpClient:HttpClient) { }

  createKanban(kanban: Kanban){
    const url = `${this.baseUrl}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, kanban, requestOptions);
  }

  getKanbanById(id: string) {
    const url = `${this.baseUrl}/${id}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }
  

  // User Specific Kanban ---> for fetching assigned kanbans
  getAllKanban(){
    const url = `${this.baseUrl}/user-specific-kanban`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }

  updateKanbanById(kanban: Kanban) {
    
    const url = `${this.baseUrl}/${kanban.id}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.put(url, kanban, requestOptions);
  }

  deleteKanbanById(id: string) {
    const url = `${this.baseUrl}/${id}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.delete(url, requestOptions);
  }

  assignKanbanToUser(id: string, user: any) {
    const url = `${this.baseUrl}/${id}/assign-kanban`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, user, requestOptions);
  }

  getAssignedKanban() {
    const url = `${this.baseUrl}/assigned-kanban`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }

  private getRequestOptions() {
    const httpHeaders = new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    });
    let requestOptions = {headers : httpHeaders}
    return requestOptions;
  }
}
