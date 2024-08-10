package com.wallstft.masking;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wallstft.utils.OpaUtils;

public class MaskingUtil {

    static public <T> T mask (ObjectNode column_node, T orignal_value ) {
        T v = orignal_value;
        if ( column_node != null ) {
            Boolean mask = OpaUtils.getBoolean( column_node, "mask");
            if ( mask != null && mask ) {
                if ( v instanceof String ) {
                    String s = (String)v;
                    StringBuilder sb = new StringBuilder();
                    for ( int i =0; i<s.length(); i++ ) {
                        if ( s.charAt(i) >= 'a' && s.charAt(i) <='z') {
                            sb.append("X");
                        }
                        else if ( s.charAt(i) >= 'A' && s.charAt(i) <='Z') {
                            sb.append("X");
                        }
                        else if ( s.charAt(i) >= '0' && s.charAt(i) <='9') {
                            sb.append("X");
                        }
                        else {
                            sb.append(s.charAt(i));
                        }
                    }
                    v=(T)sb.toString();
                }
            }
        }
        return v;
    }


    static public <T> T hash (ObjectNode column_node, T orignal_value ) {
        T v = orignal_value;
        if ( column_node != null ) {
            Boolean mask = OpaUtils.getBoolean( column_node, "hash");
            if ( mask != null && mask ) {
                if ( v instanceof String ) {
                    String s = (String)v;
                    int hash_code = Math.abs(s.hashCode());
                    StringBuilder sb = new StringBuilder();
                    for ( int i =0; i<s.length(); i++ ) {
                        if ( s.charAt(i) >= 'a' && s.charAt(i) <='z') {
                            int delta = hash_code % 26;
                            sb.append((char)('a'+delta));
                            hash_code += delta + 1;
                        }
                        else if ( s.charAt(i) >= 'A' && s.charAt(i) <='Z') {
                            int delta = hash_code % 26;
                            sb.append((char)('A'+delta));
                            hash_code += delta + 1;
                        }
                        else if ( s.charAt(i) >= '0' && s.charAt(i) <='9') {
                            int delta = hash_code % 10;
                            sb.append((char)('0'+delta));
                            hash_code += delta + 1;
                        }
                        else {
                            sb.append(s.charAt(i));
                        }
                    }
                    v=(T)sb.toString();
                }
            }
        }
        return v;
    }

}
