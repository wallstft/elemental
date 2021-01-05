package org.neostorm.excel.charts.chartParameters;

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

import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.neostorm.excel.charts.data_structure.ChartAnchorData;
import org.neostorm.excel.charts.data_structure.ChartAxis;

public class HistogramChartParameters extends ChartParameters {


    @Override
    public ChartTypes getChartType() {
        return ChartTypes.BAR;
    }

    @Override
    public XDDFChartData getChartData(XSSFSheet sheet, XSSFChart chart, ChartAxis axis) {
        XDDFChartData data = super.getChartData(sheet, chart, axis);

        XDDFBarChartData bar = (XDDFBarChartData) data;
        bar.setBarDirection(BarDirection.COL);

        return data;
    }
}
