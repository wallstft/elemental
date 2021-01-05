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

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.neostorm.excel.charts.data_structure.ChartAnchorData;
import org.neostorm.excel.charts.data_structure.ChartAxis;

public class ChartParameters {

//    public ChartAnchorData getChartAnchorData ()
//    {
//        ChartAnchorData ad = new ChartAnchorData();
//        return ad;
//    }

    public void setChartParameters(XSSFChart chart)
    {
        chart.setTitleText(getChartTitle());
        chart.setTitleOverlay(false);
    }

    public String getChartTitle ()
    {
        return "Area-wise Top Seven Countries";
    }

    public void setChartLegendParameters ( XDDFChartLegend legend )
    {
        legend.setPosition(LegendPosition.TOP_RIGHT);
    }

    public String getBottomAxisTitle ()
    {
        return "Country";
    }

    public String getLeftAxisTitle()
    {
        return "Area";
    }

    public ChartAxis setChartAxis (XSSFChart chart )
    {
        ChartAxis ch = new ChartAxis();
        ch.bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        ch.bottomAxis.setTitle(getBottomAxisTitle());
        ch.leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        ch.leftAxis.setTitle(getLeftAxisTitle());
        ch.leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        return ch;
    }

    public ChartTypes getChartType()
    {
        return ChartTypes.LINE;
    }

    public CellRangeAddress get_X_AxisRange()
    {
        return new CellRangeAddress(0, 0, 0, 6);
    }

    public CellRangeAddress [] get_Y_AxisRange()
    {
        return new CellRangeAddress[] {new CellRangeAddress(1, 1, 0, 6)};
    }

    public String [] get_Y_SeriesNames()
    {
        return new String[] {"series_1"};
    }



//    public XDDFChartData createChartData( XSSFChart chart, ChartAxis axis )
//    {
//        XDDFChartData data = chart.createData(getChartType(), axis.bottomAxis, axis.leftAxis);
//        return data;
//    }
//
//    public void createSeries (XSSFSheet sheet, XDDFChartData data )
//    {
//        XDDFDataSource<String> countries = XDDFDataSourcesFactory.fromStringCellRange(sheet, get_X_AxisRange() );
//        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet, get_Y_AxisRange());
//        XDDFChartData.Series series1 = data.addSeries(countries, values);
//        series1.setTitle(getChartTitle(), null);
//    }

    public void plot (XSSFChart chart, XDDFChartData data)
    {
        chart.plot(data);
    }

    public XDDFChartData getChartData(XSSFSheet sheet, XSSFChart chart, ChartAxis axis )
    {
//        XDDFDataSource<String> countries = XDDFDataSourcesFactory.fromStringCellRange(sheet,
//        new CellRangeAddress(0, 0, 0, 6));
//
//        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
//                new CellRangeAddress(1, 1, 0, 6));
//
//        XDDFChartData data = chart.createData(getChartType(), axis.bottomAxis, axis.leftAxis);
//
//        data.setVaryColors(true);
//        data.addSeries(countries, values);

        XDDFDataSource<String> countries = XDDFDataSourcesFactory.fromStringCellRange(sheet, get_X_AxisRange() );

        XDDFChartData data = null;
        if ( axis != null ) {
            data = chart.createData(getChartType(), axis.bottomAxis, axis.leftAxis);
        }
        else {
            data = chart.createData(getChartType(), null, null);
        }

        data.setVaryColors(true);
        CellRangeAddress [] ranges = get_Y_AxisRange();
        String [] series_names = get_Y_SeriesNames();
        for ( int i=0; i<ranges.length; i++ ) {
            CellRangeAddress range = ranges[i];
            String series_name = String.format("series_%d", i);
            if ( i<series_names.length && series_names[i] != null )
                series_name = series_names[i];
            XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet, range);
            XDDFChartData.Series series1 = data.addSeries(countries, values);
            series1.setTitle(String.format(series_name, i), null);
        }

        return data;
    }
}
