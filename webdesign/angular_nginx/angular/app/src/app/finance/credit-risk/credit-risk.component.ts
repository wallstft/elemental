import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";

@Component({
  selector: 'app-credit-risk',
  templateUrl: './credit-risk.component.html',
  styleUrls: ['./credit-risk.component.css']
})
export class CreditRiskComponent implements OnInit {

  constructor( private navBarServices: NavBarServiceService ) {
    navBarServices.setApplicationName("Credit Risk");
  }

  ngOnInit(): void {
  }

}
