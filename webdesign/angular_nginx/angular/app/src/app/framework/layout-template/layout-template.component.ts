import {Component, ElementRef, Input, OnInit, Renderer2} from '@angular/core';
import {LayoutData} from "./layout-data";
import {HttpService} from "../../services/http-service.service";
import {Post} from "../../data_structures/post.model";
import {AttrNode} from "./attr-node";

@Component({
  selector: 'app-layout-template',
  templateUrl: './layout-template.component.html',
  styleUrls: ['./layout-template.component.css']
})
export class LayoutTemplateComponent implements OnInit {

  @Input() layout_data : LayoutData[] ;
  @Input() data : object;

  constructor(private httpService: HttpService, private elRef: ElementRef, private renderer: Renderer2)
  {
    // this.httpService.getService<LayoutData[]>("/rest/layout").subscribe( (data:LayoutData[]) => {
    //   this.layout_data = data;
    //   this.render_layout(elRef, this.layout_data);
    // });
  }

  createElement ( node : LayoutData ) {
    var element = null;
    if ( node != null ) {
      element = this.renderer.createElement( node.tag );
      if ( element != null && node.attributes != null ) {
        var i =0;
        for ( i=0; i<node.attributes.length; i++ ) {
          let anode : AttrNode = node.attributes[i];
          this.renderer.setAttribute( element, anode.name, anode.value );
        }
      }
    }
    return element;
  }

  render_layout( parent: any, node_list : LayoutData[] )
  {
    if ( node_list != null && node_list.length> 0 ) {
      var i;
      for (i = 0; i < node_list.length; i++) {
        var node = node_list[i];
        if ( node != null ) {
          let element = this.createElement(node);
          if ( node.tag_content != null ) {
            var text = this.renderer.createText(node.tag_content );
            this.renderer.appendChild( element, text );
          }
          if (parent != null && parent instanceof ElementRef ) {
            this.renderer.appendChild(parent.nativeElement, element);
          }
          else {
            this.renderer.appendChild(parent, element);
          }
          if (node.children != null) {
            this.render_layout(element, node.children );
          }
        }
      }
    }
  }

  public renderPage()
  {
      this.render_layout(this.elRef, this.layout_data);
  }

  ngOnInit(): void {
  }


}
