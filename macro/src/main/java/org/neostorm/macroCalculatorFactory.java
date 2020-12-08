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

import org.codehaus.janino.ScriptEvaluator;
import org.neostorm.data_structures.Pair;

import java.util.*;

public class macroCalculatorFactory {

    private boolean recurse_dependency_tree (HashSet<String> seen_names, List<Pair<String, Class>> variables, HashMap<String, Macro> map, Stack<String> breadcrumbs )
    {
        boolean status = false;
        for ( Pair<String,Class> v : variables ) {
            if ( map.containsKey(v.getKey()))  {
                Macro param = map.get(v.getKey());
                if ( seen_names.contains(v.getKey()))
                    status=true;
                else {
                    breadcrumbs.push(v.getKey());
                    if ( status = recurse_dependency_tree( seen_names, param.getVariables(), map, breadcrumbs ))  {
                        break;
                    }
                    breadcrumbs.pop();
                }
            }
        }
        return status;
    }

    private boolean macro_list_has_recursive_dependencies ( List<Macro> macros, List<Pair<String, String>> problem_macros_breadcrumb ) throws Exception {
        if ( problem_macros_breadcrumb == null ){
            throw new Exception ("Must have second parameter defined, problem_macros must be not-null");
        }

        HashSet<String> names = new HashSet<String>();
        HashMap<String, Macro> map = new HashMap<>();
        for ( Macro m : macros ) {
            names.add(m.getMacro_name());
            map.put ( m.getMacro_name(), m );
            names.add(m.getMacro_name());
            Stack<String> breadcrumbs = new Stack<>();
            if ( recurse_dependency_tree ( names, m.getVariables(), map, breadcrumbs )) {
                StringBuilder sb = new StringBuilder();
                String del = "";
                for ( String crumb : breadcrumbs ) {
                    sb.append(del);
                    sb.append(crumb);
                    del = " -> ";
                }
                Pair<String,String> p = new Pair<>(m.getMacro_name(), sb.toString() );
                problem_macros_breadcrumb.add(p);
            }
        }

        return problem_macros_breadcrumb.size()>0;
    }

    private void recursively_build_compilation_order ( HashMap<String, Macro> map, List<Macro> ordered_macro_list, Macro m )
    {
        for ( Pair<String, Class> p : m.getVariables() ) {
            String variable = p.getKey();
            if ( map.containsKey(variable)) {
                Macro vm = map.get(variable);
                recursively_build_compilation_order(map, ordered_macro_list, vm );
            }
        }
        if ( !ordered_macro_list.contains(m)) {
            ordered_macro_list.add(m);
        }
    }
    public macroCalculator build_calculator ( List<Macro> macros, List<Pair<String, String>> problem_macros_breadcrumb ) throws Exception {

        macroCalculator calc = null;
        if ( macro_list_has_recursive_dependencies ( macros, problem_macros_breadcrumb )) {
            return calc;
        }
        HashSet<String> names = new HashSet<String>();
        for ( Macro m : macros ) {
            names.add(m.getMacro_name());
        }

        List<Macro> ordered_macro_list = new ArrayList<>();
        HashMap<String, Macro> map = new HashMap<>();
        for ( Macro m : macros ) {
            names.add(m.getMacro_name());
        }
        for ( Macro m : macros ) {
            recursively_build_compilation_order (map, ordered_macro_list, m );
        }
        List<CompiledMacro> compiled_macros = new ArrayList<>();
        for ( Macro m : ordered_macro_list ) {
            CompiledMacro cm = new CompiledMacro();
            cm.compile(m);
            compiled_macros.add(cm);
        }
        calc = new macroCalculator(compiled_macros);
        return calc;
    }
}
