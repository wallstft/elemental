package org.neostorm.diskDirectoryManager;

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

import java.io.File;

public class DiskDirectoryManager {
    String root_directory_property_name = "root_dir";
    String context = null;

    public DiskDirectoryManager ( String context ) {
        this.context = context;
    }
    public String getRoot_directory_property_name() {
        return root_directory_property_name;
    }
    public void setRoot_directory_property_name(String root_directory_property_name) {
        this.root_directory_property_name = root_directory_property_name;
    }

    private String getRootContext()
    {
        String root = System.getProperties().getProperty(root_directory_property_name);
        return String.format("%s%s%s", root, File.separator, context );
    }
    private void mkdir (String dir ) {
        if ( dir != null ) {
            File f = new File (dir);
            if ( !f.exists() )
                f.mkdirs();
        }
    }
    public String getTempSpace ()
    {
        String dir = String.format("%s%s%s", getRootContext(), File.separator, "temp");
        mkdir(dir);
        return dir;
    }
    public String getDataSpace ()
    {
        String dir = String.format("%s%s%s", getRootContext(), File.separator, "data");
        mkdir(dir);
        return dir;
    }
    public String getApplicationSpace ()
    {
        String dir = String.format("%s%s%s", getRootContext(), File.separator, "app");
        mkdir(dir);
        return dir;
    }
}
