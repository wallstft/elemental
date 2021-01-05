package org.neostorm.excel.charts.core;

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

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ChartBase {

    protected XSSFDrawing getDrawing (XSSFSheet sheet)
    {
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        return drawing;
    }

    protected XSSFClientAnchor getAnchor (XSSFDrawing drawing, int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2 )
    {
        XSSFClientAnchor anchor = drawing.createAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2);
        return anchor;
    }
}
