import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";

@Component({
  selector: 'app-syndicated-loan',
  templateUrl: './syndicated-loan.component.html',
  styleUrls: ['./syndicated-loan.component.css']
})
export class SyndicatedLoanComponent implements OnInit {

  constructor( private navBarServices: NavBarServiceService ) {
    navBarServices.setApplicationName("Syndicated Loan");
  }

    ngOnInit(): void {
  }

}
