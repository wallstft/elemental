package com.wallstft.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OpaUtils {


    static public String getString (ObjectNode node, String name ) {
        String value = null;
        if ( node != null ) {
            JsonNode jv = node.get(name);
            if ( jv != null ) {
                value = jv.asText();
            }
        }
        return value;
    }

    static public Integer getInteger (ObjectNode node, String name ) {
        Integer value = null;
        if ( node != null ) {
            JsonNode jv = node.get(name);
            if ( jv != null ) {
                value = jv.asInt();
            }
        }
        return value;
    }

    static public Boolean getBoolean (ObjectNode node, String name ) {
        Boolean value = false;
        if ( node != null ) {
            JsonNode jv = node.get(name);
            if ( jv != null ) {
                value = jv.asBoolean();
            }
        }
        return value;
    }


}
