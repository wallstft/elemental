import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";

@Component({
  selector: 'app-reconciliation',
  templateUrl: './reconciliation.component.html',
  styleUrls: ['./reconciliation.component.css']
})
export class ReconciliationComponent implements OnInit {

  constructor( private navBarServices: NavBarServiceService ) {
    navBarServices.setApplicationName("Reconciliation");
  }

  ngOnInit(): void {
  }

}
