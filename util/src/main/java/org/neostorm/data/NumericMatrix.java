package org.neostorm.data;

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

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.neostorm.data_structures.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class NumericMatrix {
    private String names = null;
    private final String[] columns_names ;
    private final String[] row_names ;
    private final RealMatrix matrix ;

    public NumericMatrix setNames ( String [] rows, String [] cols) {
        return new NumericMatrix( rows, cols, matrix );
    }

    public RealMatrix getRealMatrix()
    {
        return matrix;
    }
    public NumericMatrix ( double[][] data )
    {
        int rows = data.length;
        int cols = data[0].length;
        row_names = new String[rows];
        columns_names = new String[cols];
        matrix = new Array2DRowRealMatrix(data);

        for ( int r=0; r<rows; r++ ) {
            row_names[r] = "";
        }
        for ( int c=0; c<cols; c++ ) {
            columns_names[c] = "";
        }
    }

    public NumericMatrix ( ArrayList<String> row_names, ArrayList<String> col_names, double[][] data )
    {
        this.row_names = new String[row_names.size()];
        row_names.toArray(this.row_names);

        this.columns_names = new String[col_names.size()];
        col_names.toArray(this.columns_names);

        matrix = new Array2DRowRealMatrix(data);
    }

    public NumericMatrix ( String[] row_names, String[] col_names, double[][] data )
    {
        this.row_names = row_names;
        this.columns_names = col_names;
        matrix = new Array2DRowRealMatrix(data);
    }
    public NumericMatrix ( String[] row_names, String[] col_names, RealMatrix data_matrix )
    {
        this.row_names = row_names;
        this.columns_names = col_names;
        matrix = data_matrix;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    private int getIndex ( String [] list, String value ) {
        for ( int i=0; i<list.length; i++ ) {
            if ( list[i] != null && list[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public double [] getRow (String name ) {
        int r = getIndex( row_names, name);
        return getRow(r);
    }

    public double [] getRow ( int r )
    {
        return matrix.getRow(r);
    }

    public NumericMatrix transpose ()
    {
        NumericMatrix t = new NumericMatrix(columns_names, row_names, matrix.transpose());
        return t;
    }

    public String[] getRowNames () {
        return row_names;
    }

    public boolean hasRow ( String rowname )
    {
        for ( String rn : row_names ) {
            if ( rn != null && rn.equals(rowname)) {
                return true;
            }
        }
        return false;
    }

    public interface IteratorAction {
        public void walk (String row_name, int row, String col_name, int col, double v );
    }


    public void walker( IteratorAction handler )
    {
        for ( int r=0;r<row_names.length; r++ ) {
            for ( int c=0; c<columns_names.length; c++ ) {
                String r_name = row_names[r];
                String c_name = columns_names[c];
                Double v = matrix.getEntry( r, c );
                if ( handler != null ) {
                    handler.walk( r_name, r, c_name, c, v);
                }
            }
        }
    }

    public double[] getVector()
    {
        double[]data = new double[getRows()*getCols()];
        walker ( new IteratorAction() {
            int i=0;
            @Override
            public void walk(String row_name, int row, String col_name, int col, double v) {
                data[i++] = v;
            }
        } );
        return data;
    }


    public double[][] getDoubleMatrix ()
    {
        return matrix.getData();
    }

    public String[] getColNames () {
        return columns_names;
    }

    public Double get ( String row, String col ) throws Exception
    {
        Double v = 0.0;
        int row_idx = getIndex(row_names,       row );
        int col_idx = getIndex(columns_names,   col );
        if ( row_idx != -1 && col_idx != -1 ) {
            return get ( row_idx, col_idx );
        }
        return v;
    }

    public Double get ( int row, int col )
    {
        return matrix.getEntry( row, col );
    }

    private double[] getColumnVector ( int column )
    {
        return this.matrix.getColumn(column );
    }

    public boolean rowKeyExist ( String row_key ) {
        return hasRow(row_key);
    }

    public int getRows ()
    {
        return this.matrix.getRowDimension();
    }
    public int getCols ()
    {
        return this.matrix.getColumnDimension();
    }

    //OLD CODE BELOW

    public void write_matrix  (String variable_name, File script ) throws Exception
    {
        BufferedWriter bw = new BufferedWriter( new FileWriter( script, true ));
        try {
            StringBuilder vector = new StringBuilder();
            String del = "";
            for ( int r =0; r<matrix.getRowDimension(); r++ ) {
                for ( int c =0; c<matrix.getColumnDimension(); c++ ) {
                    Double data = matrix.getEntry(r,c);
                    vector.append(del);
                    if ( data == null ) {
                        vector.append("0");
                    }
                    else {
                        vector.append(data.toString());
                    }
                    del =",";
                }
            }
            StringBuilder row_names_builder = new StringBuilder();
            {

                del = "";
                for ( String rn : row_names) {
                    row_names_builder.append(del);
                    row_names_builder.append(String.format("\"%s\"", rn ));
                    del = ",";
                }
            }
            StringBuilder col_names_builder = new StringBuilder();
            {

                del = "";
                for ( String rn : columns_names )  {
                    col_names_builder.append(del);
                    col_names_builder.append(String.format("\"%s\"", rn));
                    del=",";
                }
            }
            bw.write(String.format("%s = matrix ( c(%s), %d, %s, TRUE)\n\n", variable_name, vector.toString(), row_names.length, columns_names.length ));
            bw.write(String.format("%s_row_names = c(%s)\n\n", variable_name, row_names_builder.toString() ));
            bw.write(String.format("%s_col_names = c(%s)\n\n", variable_name, col_names_builder.toString() ));
            bw.write(String.format("rownames (%s) <- %s_row_names\n\n", variable_name, variable_name ));
            bw.write(String.format("colnames (%s) <- %s_col_names\n\n", variable_name, variable_name ));

            //rownames (U) <- row_names
        } catch(IllegalArgumentException e) {
            System.out.println("Result is not a matrix: " + e);
        }
        finally {
            bw.close();
            bw = null;
        }
    }
}