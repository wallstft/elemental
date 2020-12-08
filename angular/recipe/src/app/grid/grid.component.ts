import {Component, OnInit} from '@angular/core';
import {GridDataServiceService} from '../grid-data-service.service';


/**
 * @title Basic use of `<table mat-table>`
 */
@Component({
  selector: 'app-grid',
  styleUrls: ['grid.component.css'],
  templateUrl: 'grid.component.html'
})
export class GridComponent implements  OnInit {
  constructor( private gridDataService: GridDataServiceService ) {}
  displayedColumns: string[] = [];
  dataSource = [];
  ngOnInit () {
    this.displayedColumns = this.gridDataService.getGridColumns();
    this.dataSource = this.gridDataService.getGridData();
  }
}


