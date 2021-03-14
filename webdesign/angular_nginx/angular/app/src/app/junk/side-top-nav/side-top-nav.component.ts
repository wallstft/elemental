import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-side-top-nav',
  templateUrl: './side-top-nav.component.html',
  styleUrls: ['./side-top-nav.component.css']
})
export class SideTopNavComponent implements OnInit {

  title = 'SideTop';

  constructor() { }

  ngOnInit(): void {
  }

}
