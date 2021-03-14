import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Post} from "./post.model";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  msg_list: Post[] = [];

  myGet ()
  {
    return this.http.get<Post>('/rest/hw');

  }
  constructor ( private http: HttpClient ) {}
}
