import { Component, OnInit } from '@angular/core';
import {NavBarServiceService} from "../../services/nav-bar-service.service";
import {LayoutData} from "../../framework/layout-template/layout-data";
import {HttpService} from "../../services/http-service.service";

@Component({
  selector: 'app-market-risk',
  templateUrl: './market-risk.component.html',
  styleUrls: ['./market-risk.component.css']
})
export class MarketRiskComponent implements OnInit {

  data: any = {
    test : 'Hello World',
    sub : {
      test : 'FooBar'
    }
  }
  layout_data:LayoutData[] ;

  // layout_data:LayoutData[] = [
  //   {
  //     tag : 'ul',
  //     data_field_name : 'test',
  //     object_name : 'sub',
  //     click_action :'this-is-some-name',
  //     click_url : '/post',
  //     children : [
  //       {
  //         tag: 'li',
  //         data_field_name : 'test',
  //         style :'{"background-color":"grey"}',
  //         // class : 'bg-primary',
  //         children : [
  //           {
  //             tag: 'ul',
  //             data_field_name : 'test',
  //             children: [
  //               {
  //                 tag: 'li',
  //                 data_field_name : 'test',
  //                 children : [
  //                   {
  //                     tag : 'ul',
  //                     children: [
  //                       {
  //                         tag: 'li',
  //                         data_field_name : 'test',
  //                       }
  //                     ]
  //                   }
  //                 ]
  //               },
  //               {
  //                 tag: 'li',
  //                 data_field_name : 'test',
  //                 children : []
  //               }
  //               ]
  //           }
  //         ]
  //       },
  //       {
  //         tag: 'li',
  //         data_field_name : 'test',
  //         children : [
  //           {
  //             tag: 'ul',
  //             children: [
  //               {
  //                 tag : 'li',
  //                 data_field_name : 'test'
  //               }
  //             ]
  //           }
  //         ]
  //       }
  //     ]
  //   },{
  //     tag:'button',
  //     tag_content: 'Press Me',
  //     click_url : '/rest/post'
  //   }
  // ]

  constructor( private navBarServices: NavBarServiceService, private http : HttpService ) {
    navBarServices.setApplicationName("Market Risk");
  }

  public loadLayout() {
    this.http.getService('/rest/data').subscribe( (data=>{
      this.data = data;
    }));
    this.http.getService<LayoutData[]>('/rest/layout').subscribe( (responseData:LayoutData[]) => {
      this.layout_data = responseData;
    });

  }

  ngOnInit(): void {
  }

}
