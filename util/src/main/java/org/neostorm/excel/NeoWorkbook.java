package org.neostorm.excel;

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

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class NeoWorkbook {

    String filename = null;
    XSSFWorkbook workbook = null;
    ArrayList<NeoSheet> sheets = new ArrayList<>();

    public void open (String path) throws Exception
    {
        if ( workbook != null ) {
            workbook.close();
            workbook = null;
        }
        this.filename = path;
        FileInputStream excelFile = new FileInputStream(new File(filename));
        workbook = new XSSFWorkbook(excelFile);
    }
    private void init()
    {
        if ( workbook == null )
            workbook = new XSSFWorkbook();
    }
    public void saveAs( String path ) throws Exception
    {
        init();
        FileOutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
    }
    public NeoSheet createSheet(String sheet_name )
    {
        init();
        NeoSheet sheet = new NeoSheet(workbook,sheet_name);
        sheets.add(sheet);
        return sheet;
    }

    public NeoSheet getSheet ( String sheet_name ) {
        NeoSheet s = new NeoSheet(workbook,workbook.getSheet(sheet_name ));
        s.sheet_name = sheet_name;
        return s ;
    }

}
