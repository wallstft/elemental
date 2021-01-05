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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.neostorm.data.NumericMatrix;

public class NeoSheet {

    XSSFWorkbook workbook = null;
    String sheet_name = null;
    XSSFSheet sheet =null;

    NeoSheet ( XSSFWorkbook workbook, String sheet_name )
    {
        this.workbook = workbook;
        this.sheet = workbook.createSheet(sheet_name );
    }

    NeoSheet ( XSSFWorkbook workbook, XSSFSheet sheet)
    {
        this.workbook = workbook;
        this.sheet = sheet;
    }

    public class MatrixLocation {
        public String[] x_names = null;
        public String[] y_names = null;
        public CellRangeAddress x_range = null;
        public CellRangeAddress[] y_range = null;

        public MatrixLocation truncate ( int count ) {
            int first = Math.min( y_names.length, count );
            MatrixLocation l = new MatrixLocation();
            l.x_names = new String [first];
            l.x_names = x_names;
            l.y_names = new String[first];
            for ( int i=0; i<first; i++ ) {
                l.y_names[i] = y_names[i];
            }
            l.x_range = new CellRangeAddress( x_range.getFirstRow(), x_range.getLastRow(), x_range.getFirstColumn(), x_range.getFirstColumn()+first);
            l.y_range = new CellRangeAddress[y_range.length];
            for ( int i=0; i< y_range.length; i++ ) {
                l.y_range[i] = new CellRangeAddress( y_range[i].getFirstRow(), y_range[i].getLastRow(), y_range[i].getFirstColumn(), y_range[i].getFirstColumn()+first);
            }
            /*
                    m.x_range = new CellRangeAddress( x_first_row, x_first_row, base_col, base_col+x_last_col );
                    m.y_range[r] = new CellRangeAddress( base_row+r, base_row+r, base_col+y_first_col, base_col+y_last_col );
             */

            return l;
        }
    }

    public XSSFSheet getExcelSheet ()
    {
        return sheet;
    }

    public void put ( int row_num, int col_num, String label, double value )
    {
        Row row = sheet.createRow(row_num);
        Cell cell = row.createCell(col_num );
        cell.setCellValue(label);
        cell = row.createCell(col_num+1 );
        cell.setCellValue(value);
    }
    public MatrixLocation put (NumericMatrix matrix)
    {
        int base_row= 1;
        int base_col= 1;
        return put ( matrix, base_row, base_col );
    }
    public MatrixLocation put (NumericMatrix matrix, int base_row, int base_col )
    {
        MatrixLocation m = new MatrixLocation();
        String [] row_names = matrix.getRowNames();
        String [] col_names = matrix.getColNames();
        int x_first_row = base_row-1;
        int x_last_row = x_first_row;
        int x_first_col = 0;
        int x_last_col = col_names.length;
        m.x_range = new CellRangeAddress( x_first_row, x_first_row, base_col, base_col+x_last_col );
        m.x_names = row_names;
        m.y_names = col_names;

        Row row = sheet.createRow(base_row-1);
        for ( int t=0; t<col_names.length; t++ ) {
            Cell cell = row.createCell(base_col+t);
            if ( cell != null ) {
                cell.setCellValue(col_names[t]);
            }
        }
        int y_first_row = base_row;
        int y_last_row = base_row+matrix.getRows();
        int y_first_col = 0;
        int y_last_col = col_names.length;
        m.y_range = new CellRangeAddress[matrix.getRows()];
        for ( int r=0;r<matrix.getRows(); r++ ) {
            m.y_range[r] = new CellRangeAddress( base_row+r, base_row+r, base_col+y_first_col, base_col+y_last_col );
            row = sheet.createRow(base_row+r);
            Cell hdr = row.createCell(base_col-1);
            hdr.setCellValue( row_names[r]);
            for ( int c=0; c<matrix.getCols(); c++ ) {
                Cell cell = row.createCell(base_col+c);
                if ( cell != null ) {
                    cell.setCellValue(matrix.get(r,c));
                }
            }
        }
        return m;
    }
}
