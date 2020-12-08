import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoggingService {

  constructor() { }

  logStatusChance( status: string )
  {
    console.log ('A service status change, new status : ' + status );
  }
}
