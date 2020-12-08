import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'oms';
  name="Kevin";


	Message = "This is a Message";
	onClick () {
		this.Message = "The button has been clicked";
	}
	
	onUpdateServerName ( event :Event ) {
		this.Message = (<HTMLInputElement>event.target).value;
	}
	
	
}
