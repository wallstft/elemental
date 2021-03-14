import { Injectable } from '@angular/core';
import {nav_bar_items} from "../data_structures/nav-bar-items.model";

@Injectable({
  providedIn: 'root'
})
export class NavBarServiceService {

  application_name: string = 'Home';
  public sidebar_open:boolean;
  private nav_items : nav_bar_items[];

  public toggle_sidebar ()
  {
    this.sidebar_open = !this.sidebar_open;
  }

  public getApplicationName () {
    return this.application_name;
  }

  public getNavBarItems () {
    return this.nav_items;
  }

  public setNavBarItems ( nbi : nav_bar_items[] ) {
    this.nav_items = nbi;
  }

  public setApplicationName( name :string ) {
    this.application_name = name;
  }

  constructor() { }
}
