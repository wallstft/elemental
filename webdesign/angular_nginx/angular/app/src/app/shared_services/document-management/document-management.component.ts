import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";

@Component({
  selector: 'app-document-management',
  templateUrl: './document-management.component.html',
  styleUrls: ['./document-management.component.css']
})
export class DocumentManagementComponent implements OnInit {

  constructor( private navBarServices: NavBarServiceService ) {
    navBarServices.setApplicationName("Document Management");
  }

  ngOnInit(): void {
  }

}
