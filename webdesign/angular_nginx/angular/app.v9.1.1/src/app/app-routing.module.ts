import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UsersComponent} from "./users/users.component";
import {HomeComponent} from "./home/home.component";
import {ServersComponent} from "./servers/servers.component";


const routes: Routes = [
  { path: '',  component: HomeComponent },
  { path: 'users',  component: UsersComponent },
  { path: 'servers',  component: ServersComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
