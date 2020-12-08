import { Component, OnInit } from '@angular/core';
import { Recipe } from '../recipe/recipes.model';
import {LoggingService} from '../logging.service';
import {GridDataServiceService} from '../grid-data-service.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css'],
  providers: [LoggingService]
})
export class RecipeListComponent implements OnInit {

  recipes: Recipe[] = [
    new Recipe (
      'Kevin First Recipe',
      'This is very yummy',
      'https://food.fnr.sndimg.com/content/dam/images/food/fullset/2018/9/26/0/FNK_Tuscan-Chicken-Skillet_H2_s4x3.jpg.rend.hgtvcom.826.620.suffix/1537973085542.jpeg'
    ),
    new Recipe (
      'Kevin First Recipe',
      'This is very yummy',
      'https://food.fnr.sndimg.com/content/dam/images/food/fullset/2018/9/26/0/FNK_Tuscan-Chicken-Skillet_H2_s4x3.jpg.rend.hgtvcom.826.620.suffix/1537973085542.jpeg'
    )
  ];

  constructor(private loggingService: LoggingService, private gridDataService: GridDataServiceService  ) { }


  onClick() {

    this.gridDataService.updateHydrogen()
  }
  ngOnInit(): void {
  }

}
