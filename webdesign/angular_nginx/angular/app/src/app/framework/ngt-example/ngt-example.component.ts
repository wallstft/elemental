import { Component, ElementRef, Renderer2, ViewChild } from '@angular/core';

interface Tree {
  root: TreeNode;
}

interface TreeNode {
  label: string;
  children: TreeNode[];
}


@Component({
  selector: 'app-ngt-example',
  template: `<ul class="col-md-2">
                <li (click)="renderPage()" #addButton>Click here to add new button</li>
              </ul> 
              `
})
export class NgtExampleComponent {

  @ViewChild('addButton')
  private animateThis: ElementRef;
  constructor(private elRef: ElementRef, private renderer: Renderer2) {}


  renderPage() {
    this.formGroup();
  }

  formGroup() {
    const container = this.renderer.createElement('div');
    this.renderer.setAttribute(container, "class", "container py-5" );

    const container_row = this.renderer.createElement('div');
    this.renderer.setAttribute(container_row, "class", "row" );

    const container_col = this.renderer.createElement('div');
    this.renderer.setAttribute(container_col, "class", "col-md-10 mx-auto" );

    const form = this.renderer.createElement('form');

    const form_group = this.renderer.createElement('div');
    this.renderer.setAttribute(form_group, "class", "form_group row" );

    const colGroup1 = this.renderer.createElement('div');
    this.renderer.setAttribute(colGroup1, "class", "col-sm-6" );

    const colGroup2 = this.renderer.createElement('div');
    this.renderer.setAttribute(colGroup2, "class", "col-sm-6" );

    const FirstnameLabel = this.renderer.createElement( "label");
    this.renderer.setAttribute( FirstnameLabel, "for", "inputFirstname");


    const FirstnameText = this.renderer.createText("First Name");
    const FirstnameInput = this.renderer.createElement("input");
    this.renderer.setAttribute( FirstnameInput, "type", "text");
    this.renderer.setAttribute( FirstnameInput, "class", "form-control");
    this.renderer.setAttribute( FirstnameInput, "id", "inputFirstname");
    this.renderer.setAttribute( FirstnameInput, "placeholder", "First Name");

    const LastnameLabel = this.renderer.createElement("label");
    this.renderer.setAttribute( LastnameLabel, "for", "inputLastname");

    const LastnameText  = this.renderer.createText("Last Name");
    const LastnameInput = this.renderer.createElement("input");
    this.renderer.setAttribute( LastnameInput, "type", "text");
    this.renderer.setAttribute( LastnameInput, "class", "form-control");
    this.renderer.setAttribute( LastnameInput, "id", "inputLastname");
    this.renderer.setAttribute( LastnameInput, "placeholder", "Last Name");


    this.renderer.appendChild( container, container_row );
    this.renderer.appendChild( container_row, container_col );
    this.renderer.appendChild( container_col, form );
    this.renderer.appendChild( form, form_group );
    this.renderer.appendChild( form_group, colGroup1 );
    this.renderer.appendChild( form_group, colGroup2 );

    this.renderer.appendChild( FirstnameLabel, FirstnameText );
    this.renderer.appendChild( LastnameLabel,  LastnameText  );

    this.renderer.appendChild( colGroup1, FirstnameLabel );
    this.renderer.appendChild( colGroup1, FirstnameInput );


    this.renderer.appendChild( colGroup2, LastnameLabel );
    this.renderer.appendChild( colGroup2, LastnameInput );

    this.renderer.appendChild( this.elRef.nativeElement, container );


    // const form = this.renderer.createElement('form');
    // const button = this.renderer.createElement('button');
    // const buttonText = this.renderer.createText('This is a button');
    // const comment = this.renderer.createComment('createComment? Comment Created!');
    // const parent = this.elRef.nativeElement.parentNode;
    // const reference = this.elRef.nativeElement;
    // this.renderer.setAttribute(button, "class", "btn-primary" );
    // this.renderer.appendChild(button, buttonText);
    // this.renderer.insertBefore(parent, comment, reference )
    // this.renderer.appendChild(this.animateThis.nativeElement, button);
  }

  addBtn() {
    const button = this.renderer.createElement('button');
    const buttonText = this.renderer.createText('This is a button');
    const comment = this.renderer.createComment('createComment? Comment Created!');
    const parent = this.elRef.nativeElement.parentNode;
    const reference = this.elRef.nativeElement;
    this.renderer.setAttribute(button, "class", "btn-primary" );
    this.renderer.appendChild(button, buttonText);
    this.renderer.insertBefore(parent, comment, reference )
    this.renderer.appendChild(this.animateThis.nativeElement, button);
  }


}
