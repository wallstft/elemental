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

import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.neostorm.excel.NeoSheet;
import org.neostorm.excel.charts.chartParameters.ChartParameters;
import org.neostorm.excel.charts.data_structure.ChartAnchorData;
import org.neostorm.excel.charts.data_structure.ChartAxis;

public class ExcelChart extends ChartBase {

    public void render (NeoSheet sheet, ChartAnchorData position, ChartParameters parameters ) {
        render( sheet.getExcelSheet(), position, parameters );
    }
    public void render (XSSFSheet sheet, ChartAnchorData position, ChartParameters parameters ) {
        try {
            if ( sheet != null && parameters != null ) {
                XSSFDrawing drawing = getDrawing(sheet);

                XSSFClientAnchor anchor = getAnchor(drawing, position.dx1, position.dy1, position.dx2, position.dy2, position.col1, position.row1, position.col2, position.row2);

                XSSFChart chart = drawing.createChart(anchor);
                parameters.setChartParameters( chart );

                XDDFChartLegend legend = chart.getOrAddLegend();
                parameters.setChartLegendParameters( legend );

                ChartAxis axis = parameters.setChartAxis(chart);

                XDDFChartData data = parameters.getChartData(sheet, chart, axis);

                parameters.plot(chart,data);

            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

}