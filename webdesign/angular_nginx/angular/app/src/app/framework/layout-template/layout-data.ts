import {AttrNode} from "./attr-node";

export interface LayoutData {
  tag           : string,
  tag_content?  : string,
  attributes?   : AttrNode[];
  children?     : LayoutData[];
}
