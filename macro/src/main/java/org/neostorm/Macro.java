package org.neostorm;

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

import org.neostorm.data_structures.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Macro implements Comparable<Macro> {
    String macro_name = null;
    String code = null;
    Class return_type = null;
    List<Pair<String,Class>> variables = new ArrayList<>();

    public String getMacro_name() {
        return macro_name;
    }

    public void setMacro_name(String macro_name) {
        this.macro_name = macro_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Class getReturn_type() {
        return return_type;
    }

    public void setReturn_type(Class return_type) {
        this.return_type = return_type;
    }

    public List<Pair<String, Class>> getVariables() {
        return variables;
    }

    public void setVariables(List<Pair<String, Class>> variables) {
        this.variables = variables;
    }

    public Macro (String macro_name, String code, Class return_type, List<Pair<String,Class>> variables )
    {
        this.macro_name = macro_name;
        this.code = code;
        this.return_type = return_type;
        this.variables = variables;
    }

    @Override
    public int compareTo(Macro o) {
        if ( o != null ) {
            return getMacro_name().compareTo(o.getMacro_name());
        }
        return 0;
    }
}
