import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";

@Component({
  selector: 'app-security-master',
  templateUrl: './security-master.component.html',
  styleUrls: ['./security-master.component.css']
})
export class SecurityMasterComponent implements OnInit {

  constructor( private navBarServices: NavBarServiceService ) {
    navBarServices.setApplicationName("Security Master")
  }

  ngOnInit(): void {
  }

}
