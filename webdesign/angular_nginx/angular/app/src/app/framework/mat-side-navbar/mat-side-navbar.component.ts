import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";
import {MenuItemNode} from "../top-nav-bar/menu-item-node";



@Component({
  selector: 'app-mat-side-navbar',
  templateUrl: './mat-side-navbar.component.html',
  styleUrls: ['./mat-side-navbar.component.css']
})
export class MatSideNavbarComponent implements OnInit {

  events: string[] = [];
  opened: boolean;

  side_menu_items : MenuItemNode [] = [
    {
      item_name: "Security Master",
      item_url : "/smf"
    },
    {
      item_name: "Syndicated Loans",
      item_url: "/syndicated_loan"
    },
    {
      item_name: "Wealth Management",
      item_url: "/wealth"
    },

    {
      item_name: "Document Management",
      item_url: "/docs"
    },
    {
      item_name: "Market Risk",
      item_url: "/market_risk"
    },
    {
      item_name: "Credit Risk",
      item_url: "/credit_risk"
    },
    {
      item_name: "Reconciliation",
      item_url: "/recon"
    },


    // <!--            <li class="active"><a routerLink="/smf">Security Master</a></li>-->
    // <!--            <li><a routerLink="/syndicated_loan">Syndicated Loans</a></li>-->
    // <!--            <li><a routerLink="/wealth">Wealth Management</a></li>-->
    // <!--            <li><a routerLink="/docs">Document Management</a></li>-->
    // <!--            <li><a routerLink="/market_risk">Market Risk</a></li>-->
    // <!--            <li><a routerLink="/credit_risk">Credit Risk</a></li>-->
    // <!--            <li><a routerLink="/recon">Reconciliation</a></li>-->
  ]

  shouldRun = true;

  constructor( public sideBarData: NavBarServiceService ) { }

  ngOnInit(): void {
  }

}


