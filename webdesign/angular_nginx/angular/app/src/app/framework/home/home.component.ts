import {Component, OnInit} from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";
import {nav_bar_items} from "../../data_structures/nav-bar-items.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  nb_items: nav_bar_items[] = [
    {
      name: 'Home',
      url: '/home'
    },
    {
      name: 'Explore',
      url: '/home'
    },
    {
      name: 'Create',
      url: '/home'
    },
    {
      name: 'Users',
      url: '/home'
    }
  ];

  getNavBarItems() {
    return this.nb_items;
  }

  constructor(private navBar: NavBarServiceService) {
    navBar.setNavBarItems(this.getNavBarItems());
  }

  ngOnInit(): void {
  }

}
