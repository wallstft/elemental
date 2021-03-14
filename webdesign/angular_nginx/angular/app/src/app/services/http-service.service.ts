import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Post} from "../data_structures/post.model";
import {LayoutData} from "../framework/layout-template/layout-data";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  msg_list: Post[] = [];

  myGet ()
  {
    return this.http.get<Post>('/rest/hw');
  }

  getService<T> (url:string)
  {
    return this.http.get<T>(url);
  }

  postService ( url:string, data:object )
  {
    return this.http.post( url, data );
  }

  constructor ( private http: HttpClient ) {}
}
