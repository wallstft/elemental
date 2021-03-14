import { Component, OnInit } from '@angular/core';
import {Post} from "../../data_structures/post.model";

@Component({
  selector: 'app-unordered-list',
  templateUrl: './unordered-list.component.html',
  styleUrls: ['./unordered-list.component.css']
})
export class UnorderedListComponent implements OnInit {

  msg_list: Post[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
