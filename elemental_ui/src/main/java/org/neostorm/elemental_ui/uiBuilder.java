package org.neostorm.elemental_ui;

/*
   Copyright 2018 Wall Street Fin Tech

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
   
    
    */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class uiBuilder {

    int id_count = 0;
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode root = mapper.createObjectNode();

    private String getText ( ObjectNode node, String field ) {
        String text = null;
        JsonNode textNode = node.get(field);
        if ( textNode != null && textNode.isTextual() ) {
            return textNode.asText();
        }
        return null;
    }


    private ArrayNode getAttributes ( ObjectNode node ) {
        ArrayNode list = (ArrayNode)node.get("attributes");
        if ( list == null ) {
            list = node.putArray("attributes");
        }
        return list;
    }

    private ArrayNode getChildren ( ObjectNode node ) {
        ArrayNode list = (ArrayNode)node.get("children");
        return list;
    }

    private void addChild ( ObjectNode c ) {
        if ( root != null ) {
            JsonNode n = root.get("children");
            if ( n == null || n.isNull()) {
                root.putArray("children");
            }
            n = root.get("children");
            if ( n != null && n.isArray() ) {
                ArrayNode list = (ArrayNode)n;
                if ( list != null ) {
                    list.add(c);
                }
            }
        }
    }


    public String getTag() {
        return getText(root, "tag");
    }

    private uiBuilder add(uiBuilder el )
    {
        addChild(el.root);
        return this;
    }

    public String getHref() {
        return getText(root, "href");
    }

    public uiBuilder attr ( String name, String value ) {
        ArrayNode attr_list = getAttributes(root);
        ObjectNode n = attr_list.addObject();
        n.put ( "name", name );
        n.put ( "value", value );
        return this;
    }

    public uiBuilder href(String href) {
        attr("href", href );
        return this;
    }

    public uiBuilder tag(String tag) {
        root.put("tag", tag );
        root.put( "id_cnt", id_count++);
        return this;
    }

    public String getId() {
        return getText(root, "id");
    }

    public uiBuilder id(String id) {
        attr("id", id );
        return this;
    }

    public uiBuilder data_target( String data_target)
    {
        attr("data-target", data_target );
        return this;
    }

    public uiBuilder data_toggle( String data_toggle)
    {
        attr("data-toggle", data_toggle );
        return this;
    }

    public String getStyle() {
        return getText(root, "style");
    }

    public uiBuilder style(String style) {
        attr("style", style );
        return this;
    }

    public String getClz() {
        return getText(root, "clz");
    }

    public uiBuilder clz(String clz) {
        attr("class", clz );
        return this;
    }

    public uiBuilder placeholder(String placeholder) {
        attr("placeholder", placeholder );
        return this;
    }

    public uiBuilder forAttr(String forAttr) {
        attr("for", forAttr );
        return this;
    }

    public uiBuilder type(String type) {
        attr("type", type );
        return this;
    }

    public uiBuilder value(String value) {
        attr("value", value );
        return this;
    }

    //
    public uiBuilder data_dismiss(String data_dismiss) {
        attr("data-dismiss", data_dismiss );
        return this;
    }

    public uiBuilder src(String src) {
        attr("src", src );
        return this;
    }

    public uiBuilder alt(String alt) {
        attr("alt", alt );
        return this;
    }


    public String getData_field_name() {
        return getText(root, "data_field_name");
    }

    public uiBuilder setData_field_name(String data_field_name) {
        attr("data_field_name", data_field_name );
        return this;
    }

    public String getObject_name() {
        return getText(root, "object_name");
    }

    public uiBuilder setObject_name(String object_name) {
        attr("object_name", object_name );
        return this;
    }

    public String getLabel() {
        return getText(root, "label");
    }

    public uiBuilder content(String tag_content) {
        root.put("tag_content", tag_content );
        return this;
    }

    public uiBuilder click_url(String click_url) {
        attr("click_url", click_url );
        return this;
    }

    public String getClick_action() {
        return getText(root, "click_action");
    }

    public uiBuilder setClick_action(String click_action) {
        attr("click_action", click_action );
        return this;
    }

    public String getClick_url() {
        return getText(root, "click_url");
    }

    public uiBuilder setClick_url(String click_url) {
        attr("click_url", click_url );
        return this;
    }

    static public String toHtml (uiBuilder[] pages)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for ( uiBuilder p : pages ) {
            sb.append( p.toHtml(p.root));
        }
        sb.append("</html>");
        return sb.toString();
    }

    public String toHtml (ObjectNode node )
    {
        StringBuilder sb = new StringBuilder();
        if ( node != null ) {
            String tag = getText( node, "tag");
            if ( tag != null ) {
                sb.append(String.format("<%s ", tag));

                ArrayNode attr_list = getAttributes(node);
                if ( attr_list != null ) {
                    for ( JsonNode n : attr_list ) {
                        if ( n != null && n.isObject() ) {
                            String name  = getText( (ObjectNode)n, "name");
                            String value = getText( (ObjectNode)n, "value");
                            sb.append(String.format (" %s=\"%s\" ", name, value ));
                        }
                    }
                }

                sb.append(String.format(">\n"));
                String tag_content = getText(node, "tag_content");
                if ( tag_content != null ) {
                    sb.append(String.format("%s\n",tag_content));
                }


                ArrayNode list = getChildren(node);
                if ( list != null ) {
                    for ( JsonNode n : list ) {
                        if ( n != null && n.isObject() ) {
                            sb.append(toHtml((ObjectNode)n));
                        }
                    }
                }
                sb.append(String.format("</%s>\n", tag));
            }

        }

        return sb.toString();
    }

    static public String toJson (uiBuilder[] pages) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        String del = "";
        for ( uiBuilder p : pages ) {
            sb.append(del);
            sb.append(p.toJson());
            del=",";
        }
        sb.append("]");
        return sb.toString();
    }
    public String toJson() {
        String json = null;
        if ( root != null ) {
            try {
                json = mapper.writeValueAsString(root);
            }
            catch ( Exception ex ) {
                ex.printStackTrace();
            }
        }
        return json;
    }

    //
    //
    //
    //Unordered List
    //

    public static uiBuilder strong() { return (new uiBuilder()).tag("strong"); }
    public static uiBuilder body() { return (new uiBuilder()).tag("body"); }
    public static uiBuilder ul() { return (new uiBuilder()).tag("ul"); }
    public static uiBuilder li() { return (new uiBuilder()).tag("li"); }
    public static uiBuilder div () {
        return (new uiBuilder()).tag("div");
    }
    public static uiBuilder button () { return (new uiBuilder()).tag("button");}
    public static uiBuilder text_input() { return (new uiBuilder()).tag("input");}
    public static uiBuilder a() { return (new uiBuilder()).tag("a"); }
    public static uiBuilder span() { return (new uiBuilder()).tag("span"); }
    public static uiBuilder header() { return (new uiBuilder()).tag("header"); }
    public static uiBuilder h1() { return (new uiBuilder()).tag("h1"); }
    public static uiBuilder h2() { return (new uiBuilder()).tag("h2"); }
    public static uiBuilder h3() { return (new uiBuilder()).tag("h3"); }
    public static uiBuilder form() { return (new uiBuilder()).tag("form"); }
    public static uiBuilder section() { return (new uiBuilder()).tag("section"); }
    public static uiBuilder i() { return (new uiBuilder()).tag("i"); }
    public static uiBuilder p() { return (new uiBuilder()).tag("p"); }
    public static uiBuilder img() { return (new uiBuilder()).tag("img"); }
    public static uiBuilder footer() { return (new uiBuilder()).tag("footer"); }
    public static uiBuilder label() { return (new uiBuilder()).tag("label"); }
    public static uiBuilder textarea() { return (new uiBuilder()).tag("textarea"); }
    public static uiBuilder nav() { return (new uiBuilder()).tag("nav"); }


    public uiBuilder children (uiBuilder... list )
    {
        for ( uiBuilder n : list ) {
            add(n);
        }
        return this;
    }

}
