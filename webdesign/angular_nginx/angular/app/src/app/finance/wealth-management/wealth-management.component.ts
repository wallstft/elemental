import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";

@Component({
  selector: 'app-wealth-management',
  templateUrl: './wealth-management.component.html',
  styleUrls: ['./wealth-management.component.css']
})
export class WealthManagementComponent implements OnInit {

  constructor( private navBarServices: NavBarServiceService ) {
    navBarServices.setApplicationName("Wealth Management");
  }

  ngOnInit(): void {
  }

}
