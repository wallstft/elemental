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

import org.neostorm.data_structures.Pair;

import java.util.ArrayList;

public class NumericMatrixPrint {
    NumericMatrix matrix = null;

    public NumericMatrixPrint (NumericMatrix m ) {
        this.matrix = m;
    }

    public void print ()
    {
       print ( false, false );
    }

    public void print (boolean calc_row_totals, boolean calc_col_totals)
    {
        String [] row_names = matrix.getRowNames();
        String [] col_names = matrix.getColNames();
        Double [] col_totals = new Double[col_names.length];
        int max_row_name = 0;
        for ( String c : row_names ) {
            max_row_name = Math.max(max_row_name, c.length() );
        }
        if ( max_row_name == 0 ) {
            max_row_name = 1;
        }
        if ( calc_col_totals ) {
            max_row_name = Math.max( max_row_name, "TOTAL".length() );
        }
        System.out.print(String.format("%"+max_row_name+"s", ""));
        for ( int i =0; i<col_names.length; i++ ) {
            String c = col_names[i];
            System.out.print(String.format("%30s", c));
            col_totals[i] = 0.0;
        }
        if ( calc_row_totals ) {
            System.out.print(String.format("%30s", "TOTAL"));
        }
        System.out.println("");
        for ( int r =0; r<row_names.length; r++ ) {
            String rn = row_names[r];
            System.out.print(String.format("%"+max_row_name+"s", rn ));
            Double row_total = 0.0;
            for ( int c =0; c<col_names.length; c++ ) {
                Double v = matrix.get(r,c);
                System.out.print(String.format("%30f", v));
                row_total += v;
                col_totals[c] += v;
            }
            if ( calc_row_totals ) {
                System.out.print(String.format("%30f", row_total));
            }
            System.out.println("");
        }
        if ( calc_col_totals ) {
            System.out.print(String.format("%"+max_row_name+"s", "TOTAL" ));
            Double grand_total = 0.0;
            for ( int c =0; c<col_names.length; c++ ) {
                Double v = col_totals[c];
                System.out.print(String.format("%30f", v));
                grand_total += v;
            }
            System.out.print(String.format("%30f", grand_total));
        }
    }
    public static void print ( String name, NumericMatrix m ) {
        System.out.println(String.format("%s", name));
        NumericMatrixPrint pnt = new NumericMatrixPrint(m);
        pnt.print();;
        System.out.println("\n\n");
    }
}
