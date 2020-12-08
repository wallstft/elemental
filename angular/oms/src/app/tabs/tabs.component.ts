import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tabs',
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.css']
})
export class TabsComponent implements OnInit {
	
	Message = "This is a Message";

  constructor() { }

  ngOnInit(): void {
  }

	onClick () {
		this.Message = "The button has been clicked";
	}

}
