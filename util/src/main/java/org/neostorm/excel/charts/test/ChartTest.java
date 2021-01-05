package org.neostorm.excel.charts.test;

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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.neostorm.excel.NeoSheet;
import org.neostorm.excel.NeoWorkbook;
import org.neostorm.excel.charts.core.ExcelChart;
import org.neostorm.excel.charts.chartParameters.*;
import org.neostorm.excel.charts.data_structure.ChartAnchorData;
import org.neostorm.excel.charts.data_structure.ChartAxis;

import java.io.File;
import java.io.FileOutputStream;

public class ChartTest {


    @Test
    public void arizona_chart() throws Exception
    {
        NeoWorkbook wb = new NeoWorkbook();
        wb.open( "/tmp/Arizona2020.xlsx");
        NeoSheet az2020 = wb.getSheet("Arizona2020");

        ChartAnchorData position = new ChartAnchorData(0, 8, 20, 20);
        ChartParameters parameters = new LineChartParameters() {
            @Override
            public CellRangeAddress get_X_AxisRange() {
                return new CellRangeAddress(1, 67, 0, 0);
            }

            @Override
            public CellRangeAddress[] get_Y_AxisRange() {
                return new CellRangeAddress[] {new CellRangeAddress(1, 67, 1, 1), new CellRangeAddress(1, 67, 3, 3)};
            }

            @Override
            public String[] get_Y_SeriesNames() {
                return new String[] { "DEMOCRATIC", "REPUBLICAN"};
            }

            @Override
            public String getLeftAxisTitle() {
                return "Votes";
            }

            @Override
            public String getBottomAxisTitle() {
                return "County";
            }

            @Override
            public String getChartTitle() {
                return "2020 Pennsylvania Voting Data";
            }
        };
        ExcelChart bar = new ExcelChart();
        bar.render( az2020, position, parameters );

        wb.saveAs("/tmp/AZChart.xlsx");
    }

    @Test
    public void test_bar_chart() throws Exception
    {
        XSSFWorkbook wb = new XSSFWorkbook();

        String sheetName = "CountryBarChart";//"CountryColumnChart";

        XSSFSheet sheet = wb.createSheet(sheetName);

        // Create row and put some cells in it. Rows and cells are 0 based.
        Row row = sheet.createRow((short) 0);

        Cell cell = row.createCell((short) 0);
        cell.setCellValue("Russia");

        cell = row.createCell((short) 1);
        cell.setCellValue("Canada");

        cell = row.createCell((short) 2);
        cell.setCellValue("USA");

        cell = row.createCell((short) 3);
        cell.setCellValue("China");

        cell = row.createCell((short) 4);
        cell.setCellValue("Brazil");

        cell = row.createCell((short) 5);
        cell.setCellValue("Australia");

        cell = row.createCell((short) 6);
        cell.setCellValue("India");

        row = sheet.createRow((short) 1);

        cell = row.createCell((short) 0);
        cell.setCellValue(17098242);

        cell = row.createCell((short) 1);
        cell.setCellValue(9984670);

        cell = row.createCell((short) 2);
        cell.setCellValue(9826675);

        cell = row.createCell((short) 3);
        cell.setCellValue(9596961);

        cell = row.createCell((short) 4);
        cell.setCellValue(8514877);

        cell = row.createCell((short) 5);
        cell.setCellValue(7741220);

        cell = row.createCell((short) 6);
        cell.setCellValue(3287263);


        build_chart_variations(sheet);

        // Write output to an excel file
        String filename = "/tmp/bar-chart-top-seven-countries.xlsx";//"column-chart-top-seven-countries.xlsx";

        File f = new File ( filename );
        if ( f.exists() )
            f.delete();

        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            wb.write(fileOut);
        }
    }

    public void build_chart_variations(XSSFSheet sheet)
    {
        ChartAnchorData position = new ChartAnchorData(4, 0, 20, 20);
        line_chart(sheet, position);
        histogram(sheet, position.setPosition(position.getRow1()+position.getCol2(), position.getCol1(), position.getWidth(), position.getHeight()));
        horizontal_bar_chart(sheet, position.setPosition(position.getRow1()+position.getCol2(), position.getCol1(), position.getWidth(), position.getHeight()));
        pie_chart(sheet, position.setPosition(position.getRow1()+position.getCol2(), position.getCol1(), position.getWidth(), position.getHeight()));
    }

    public void line_chart(XSSFSheet sheet, ChartAnchorData position)
    {
        ChartParameters parameters = new LineChartParameters();
        ExcelChart bar = new ExcelChart();
        bar.render( sheet, position, parameters );
    }

    public void histogram(XSSFSheet sheet, ChartAnchorData position)
    {
        ChartParameters parameters = new HistogramChartParameters();
        ExcelChart bar = new ExcelChart();
        bar.render( sheet, position, parameters );
    }

    public void horizontal_bar_chart(XSSFSheet sheet, ChartAnchorData position)
    {
        ChartParameters parameters = new ChartParameters() {
            @Override
            public ChartTypes getChartType() {
                return ChartTypes.BAR;
            }

            @Override
            public XDDFChartData getChartData(XSSFSheet sheet, XSSFChart chart, ChartAxis axis) {
                XDDFChartData data = super.getChartData(sheet, chart, axis);

                XDDFBarChartData bar = (XDDFBarChartData) data;
                bar.setBarDirection(BarDirection.BAR);

                return data;
            }

        };
        ExcelChart bar = new ExcelChart();
        bar.render( sheet, position, parameters );
    }

    public void pie_chart(XSSFSheet sheet, ChartAnchorData position )
    {
        ChartParameters parameters = new PieChartParameters();
        ExcelChart chart = new ExcelChart();
        chart.render( sheet, position, parameters );
    }

}
