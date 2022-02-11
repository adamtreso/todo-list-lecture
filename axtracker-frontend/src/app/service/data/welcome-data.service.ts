import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { API_URL } from 'src/app/app.constants';

//we are expecting this data form the response
export class HelloWorldBean{
  constructor(public message : string){}
}


@Injectable({
  providedIn: 'root'
})
export class WelcomeDataService {

  constructor(private http:HttpClient) { }  //to use HttpClient u need HttpClientModule in the app.module.ts in the imports (after browser module)

  executeService(){
    return this.http.get<HelloWorldBean>(`${API_URL}/hello-world-bean`)  //get the observable<HelloWoldBean> form the web service
  }
  executeServiceWithParamater(name){  //get the observable<HelloWoldBean> form the web service
    return this.http.get<HelloWorldBean>(`${API_URL}/hello-world/path-variable/${name}`)
  }

}
