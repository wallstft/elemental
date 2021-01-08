import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Post} from './post.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor ( private http: HttpClient )
  {}

  onCreateGet ()
  {
    this.http.get('/rest/hw')
    .subscribe( (data:Post) => {
      this.title = data.msg;
    } )
  }
}
