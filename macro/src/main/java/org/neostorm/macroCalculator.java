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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class macroCalculator {
    List<CompiledMacro> compiled = new ArrayList<>();

    public macroCalculator (List<CompiledMacro> compiled ) {
        this.compiled = compiled;
    }

    public void run_macros ( HashMap<String, String> DataSet ) {
        for ( CompiledMacro m : compiled ) {
            m.execute ( DataSet );
        }
    }
}
