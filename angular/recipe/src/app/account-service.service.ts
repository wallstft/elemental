import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccountServiceService {
  accouns = [
    {
      name: 'Master Account',
      status: 'active'
    },
    {
      name: 'Master Account',
      status: 'active'
    },
    {
      name: 'Master Account',
      status: 'active'
    },
  ];

  constructor() { }

  addAccount( name: string, status: string ) {
    this.accouns.push ( { name: name, status : status });
  }

  updateStatus( id: number, status: string )
  {
    this.accouns[id].status = status;
  }
}
