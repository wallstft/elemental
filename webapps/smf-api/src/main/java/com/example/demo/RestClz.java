package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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


@RestController
public class RestClz {
    @RequestMapping("/")
    public String index() {
        String name =null;

        SecurityContext context = SecurityContextHolder.getContext();
        if ( context != null ) {
            Authentication auth = context.getAuthentication();
            if ( auth != null && auth.isAuthenticated() ) {
                Object user_obj = auth.getPrincipal();
                if (user_obj != null && user_obj instanceof DefaultOidcUser ) {
                    DefaultOidcUser user = (DefaultOidcUser) user_obj;
                    OidcUserInfo user_info = user.getUserInfo();
                    Map<String, Object> claims = user_info.getClaims();
                    if (claims != null) {
                        name = (String) claims.get("name");
                    }
                }
            }
        }

        return String.format("Spring Boot Example!!  Welcome %s", name );
    }

    @RequestMapping("/data")
    public String data(Principal user_obj ) {
        String json = "N.A.";
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();
            SecurityContext context = SecurityContextHolder.getContext();
            if (context != null) {
                Authentication auth = context.getAuthentication();
                if (auth != null && auth.isAuthenticated()) {
                    if (user_obj != null && user_obj instanceof OAuth2AuthenticationToken) {
                        DefaultOidcUser user_info = (DefaultOidcUser)((OAuth2AuthenticationToken)user_obj).getPrincipal();
                        Map<String, Object> claims = user_info.getClaims();
                        if (claims != null) {
                            for ( Map.Entry<String, Object> n: claims.entrySet() ) {
                                String key = n.getKey();
                                Object val = n.getValue();
                                if ( key != null && val != null ) {
                                    node.put(key, val.toString() );
                                }
                            }
                        }
                    }
                }
            }
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return json;
    }
}
