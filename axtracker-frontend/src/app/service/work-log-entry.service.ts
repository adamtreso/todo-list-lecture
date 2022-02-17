import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../app.constants';
import { AuthenticationService } from './authentication.service';

export class WorkLogEntryDto{
  constructor(
    public id:number,
    public startDate: Date,
    public endDate:Date,
    public title:string,
  ){}
}

@Injectable({
  providedIn: 'root'
})
export class WorkLogEntryService {

  constructor(
    private http:HttpClient,
    private authService : AuthenticationService
  ){}

  getAll(){
    return this.http.get<WorkLogEntryDto[]>(`${API_URL}/users/${this.authService.getAuthenticatedUser().id}/worklogentrys`)
  }

  delete(id:number){
    return this.http.delete(`${API_URL}/users/${this.authService.getAuthenticatedUser().id}}/worklogentrys/${id}`)
  }

  get(id:number){
    return this.http.get<WorkLogEntryDto>(`${API_URL}/users/${this.authService.getAuthenticatedUser().id}}/worklogentrys/${id}`)
  }

  update(workLogEntry:WorkLogEntryDto){
    return this.http.put<WorkLogEntryDto>(`${API_URL}/users/${this.authService.getAuthenticatedUser().id}}/worklogentrys/${workLogEntry.id}`,workLogEntry)
  }

  add(workLogEntry:WorkLogEntryDto){
    return this.http.post(`${API_URL}/users/${this.authService.getAuthenticatedUser().id}}/worklogentrys`, workLogEntry)
  }
}
