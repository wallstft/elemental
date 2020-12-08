import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-users-component',
  templateUrl: './users-component.component.html',
  styleUrls: ['./users-component.component.css']
})
export class UsersComponentComponent implements OnInit {

  user_name = "";

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.user_name = this.route.snapshot.params['id'];
    this.route.params.subscribe(
      (params: Params) => { this.user_name = params['id']; }
    )
  }

}
