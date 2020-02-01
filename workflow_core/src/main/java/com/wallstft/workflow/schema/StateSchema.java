package com.wallstft.workflow.schema;

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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class StateSchema {
    String schema_name = null;
    String schema = null;

    public String getSchema_name() {
        return schema_name;
    }

    public void setSchema_name(String schema_name) {
        this.schema_name = schema_name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public StateSchema(String schema_name, String schema_filename )
    {
        try {
            File schema_file = new File ( schema_filename );
            this.schema = FileUtils.readFileToString(schema_file, Charset.defaultCharset());
            this.schema_name = schema_name;
        }
        catch (Exception ex ){
            ex.printStackTrace();
        }
    }
}
