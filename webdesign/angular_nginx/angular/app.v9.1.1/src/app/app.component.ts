import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Post} from './post.model';
import {HttpService} from "./http-service.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'NeoStorm';
  msg_list: Post[] = [];
  error_msg = '';

  active = 1;

  ngOnInit(): void {
  }

  constructor(private http: HttpService) {
  }

  onCreateGet() {

    this.http.myGet()
      .subscribe(
        (data) => {
          this.title = data.msg;
          this.msg_list.push(data);
        },
        error => {
          this.error_msg = error.message;
        });
  }
}
