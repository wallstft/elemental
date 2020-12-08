import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponentComponent} from './home-component/home-component.component';
import {UsersComponentComponent} from './users-component/users-component.component';
import {ServersComponentComponent} from './servers-component/servers-component.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';


const appRoutes: Routes = [
  {path: '', component: HomeComponentComponent },
  {path: 'users', component: UsersComponentComponent},
  {path: 'users/:id', component: UsersComponentComponent},
  {path: 'servers', component: ServersComponentComponent},
  {path: 'not-found', component: PageNotFoundComponent},
  {path: '**', redirectTo: 'not-found'},
];


@NgModule ({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
