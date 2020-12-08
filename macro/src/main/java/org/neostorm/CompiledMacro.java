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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.janino.ScriptEvaluator;
import org.neostorm.data_structures.Pair;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CompiledMacro {

    static private Logger logger = LogManager.getLogger(CompiledMacro.class.getName());

    TypeConverter String_tc     = new TypeConverter() {
        @Override
        public Object fromString(String value,String fmt) {
            return value;
        }

        @Override
        public String toString(Object value,String fmt) {
            if ( value != null )
                return value.toString();
            return null;
        }
    };
    TypeConverter Integer_tc    = new TypeConverter() {
        @Override
        public Object fromString(String value,String fmt) {
            try {
                if ( value != null ){
                    return Integer.valueOf(value);
                }
            }
            catch ( Exception x ){
                logger.error(x);
            }
            return null;
        }

        @Override
        public String toString(Object value,String fmt) {
            if ( value != null )
                return value.toString();
            return null;
        }
    };
    TypeConverter Double_tc     = new TypeConverter() {
        @Override
        public Object fromString(String value,String fmt) {
            try {
                if ( value != null ){
                    return Double.valueOf(value);
                }
            }
            catch ( Exception x ){
                logger.error(x);
            }
            return null;
        }

        @Override
        public String toString(Object value,String fmt) {
            if ( value != null )
                return value.toString();
            return null;
        }
    };
    TypeConverter Boolean_tc    = new TypeConverter() {
        @Override
        public Object fromString(String value,String fmt) {
            try {
                if ( value != null ){
                    return Boolean.valueOf(value);
                }
            }
            catch ( Exception x ){
                logger.error(x);
            }
            return null;
        }

        @Override
        public String toString(Object value,String fmt) {
            if ( value != null )
                return value.toString();
            return null;
        }
    };
    TypeConverter Long_tc       = new TypeConverter() {
        @Override
        public Object fromString(String value,String fmt) {
            try {
                if ( value != null ){
                    return Long.valueOf(value);
                }
            }
            catch ( Exception x ){
                logger.error(x);
            }
            return null;
        }

        @Override
        public String toString(Object value,String fmt) {
            if ( value != null )
                return value.toString();
            return null;
        }
    };
    TypeConverter Date_tc       = new TypeConverter() {
        @Override
        public Object fromString(String value,String fmt) {
            try {
                if ( value != null ){
                    SimpleDateFormat sdf = new SimpleDateFormat(fmt);
                    Date dt = sdf.parse(value);
                    return dt;
                }
            }
            catch ( Exception x ){
                logger.error(x);
            }
            return null;
        }

        @Override
        public String toString(Object value,String fmt) {
            String dt_str = null;
            try {
                if (value != null && value instanceof Date) {
                    Date dt = (Date) value;
                    SimpleDateFormat sdf = new SimpleDateFormat(fmt);
                    dt_str = sdf.format(dt);
                }
            }
            catch ( Exception x ) {
                logger.error(x);
            }
            return dt_str;
        }
    };

    Macro macro = null;
    ScriptEvaluator macro_eval = null;
    TypeConverter [] converter_vector = null;
    TypeConverter return_type_converter = null;

    String date_format = "MM/dd/yyyy";

    public String getDate_format() {
        return date_format;
    }

    public void setDate_format(String date_format) {
        this.date_format = date_format;
    }

    String[] imports = { "java.util.*", "java.text.*" };

    private Pair<String[], Class[]> split_name_and_type ( Macro macro )
    {
        String[] names = null;
        Class[] classes = null;
        Pair<String[], Class[]> parameters = new Pair<String[], Class[]>( names= new String[macro.variables.size()], classes = new Class[macro.variables.size()]);
        for ( int i=0; i<macro.variables.size(); i++ ) {
            names[i]   = macro.variables.get(i).getKey();
            classes[i] = macro.variables.get(i).getValue();
        }
        return parameters;
    }


    public boolean execute ( HashMap<String, String> DataSet ) {
        String macro_name = macro.getMacro_name();
        boolean success = true;
        if ( macro_eval != null ) {
            Object[] args = new Object[macro.getVariables().size()];
            try {
                for ( int i=0; i< macro.getVariables().size(); i++ ) {
                    Pair<String,Class> p = macro.getVariables().get(i);
                    String value = DataSet.get(p.getKey());
                    args[i] = converter_vector[i].fromString(value, date_format );
                }
                Object ret = macro_eval.evaluate(args);
                String str_value = return_type_converter.toString( ret, date_format );
                DataSet.put ( macro_name, str_value );
            }
            catch (Exception x ) {
                logger.error(x);
                success = false;
            }
        }
        return success;
    }

    private TypeConverter assign_converter ( Class clz )
    {
        TypeConverter converter = null;
        if ( clz !=  null ) {
            if ( clz.equals(String.class)) {
                converter = String_tc;
            }
            else if ( clz.equals(Integer.class)) {
                converter = Integer_tc;
            }
            else if ( clz.equals(Long.class)) {
                converter = Long_tc;
            }
            else if ( clz.equals(Double.class)) {
                converter = Double_tc;
            }
            else if ( clz.equals(Boolean.class)) {
                converter = Boolean_tc;
            }
            else if ( clz.equals(Date.class)) {
                converter = Date_tc;
            }
        }
        return converter;
    }

    private TypeConverter[] generate_converter_vector (Class[] class_type_list )
    {
        TypeConverter[] tlist = null;
        if ( class_type_list != null ) {
            tlist = new TypeConverter[class_type_list.length];
            for ( int i=0; i<class_type_list.length ;i ++) {
                Class clz = class_type_list[i];
                tlist[i] = assign_converter(clz);
            }
        }
        return tlist;
    }

    public boolean compile ( Macro macro ) {
        boolean status = true;
        try {

            this.macro = macro;
            macro_eval = new ScriptEvaluator();
            macro_eval.setReturnType(macro.return_type);

            Class[] exp = new Class[1];
            exp[0] = Exception.class;

            Pair<String[], Class[]> parameters = split_name_and_type(macro);
            converter_vector = generate_converter_vector ( parameters.getValue() );
            return_type_converter = assign_converter( macro.return_type );
            macro_eval.setParameters(parameters.getKey(), parameters.getValue());
            macro_eval.setDefaultImports(imports);
            macro_eval.setDebuggingInformation(true, true, true);
            macro_eval.setThrownExceptions(exp);
            macro_eval.cook(macro.code);
        }
        catch ( Exception x ) {
            logger.error(x);
            status = false;
        }

        return status;
    }
}
