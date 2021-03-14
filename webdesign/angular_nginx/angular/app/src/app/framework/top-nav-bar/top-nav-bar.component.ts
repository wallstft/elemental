import { Component, OnInit } from '@angular/core';
import {Post} from "../../data_structures/post.model";
import {HttpService} from "../../services/http-service.service";
import {NavBarServiceService} from "../../services/nav-bar-service.service";
import {MenuItemNode} from "./menu-item-node";

@Component({
  selector: 'app-top-nav-bar',
  templateUrl: './top-nav-bar.component.html',
  styleUrls: ['./top-nav-bar.component.css']
})
export class TopNavBarComponent implements OnInit {
  brand_name = 'NeoStorm';
  application_name :string;
  message : string;
  msg_list: Post[] = [];
  menu_items : MenuItemNode[] = [
      {
        item_name : "Market-Risk",
        item_url : "/market_risk"
      },
      {
        item_name : "Documentation",
        item_url : "/docs"
      },
      {
        item_name : "Credit-Risk",
        item_url : "/credit_risk"
      },
      {
        item_name : "Wealth",
        item_url : "/wealth"
      },
    ];
  error_msg = '';

  active = 1;

  ngOnInit(): void {
  }

  constructor(private http: HttpService, public navBarData: NavBarServiceService) {
  }

  onCreateGet() {

    this.http.myGet()
      .subscribe(
        (data) => {
          this.message = data.data;
          this.msg_list.push(data);
        },
        error => {
          this.error_msg = error.message;
        });
  }
}
