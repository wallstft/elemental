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

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.HashMap;

public class MatrixArithmetic {

    interface Arith {
        public Double doMath ( Double a, Double b );
    }

    interface SingleMatrixArith {
        public Double doMath ( Double a  );
    }

    private Arith addition = new Arith() {
        @Override
        public Double doMath(Double a, Double b) {
            if ( a == null ){
                a=0.0;
            }
            if ( b == null ) {
                b=0.0;
            }
            return a+b;
        }
    };

    public double stdev ( NumericMatrix matrix )
    {
        double[] vector = matrix.getVector();
        double var = StatUtils.variance(vector);
        double stdev = Math.sqrt(var);

        return stdev;
    }

    public double mean ( NumericMatrix matrix )
    {
        double[] vector = matrix.getVector();
        double mean = StatUtils.mean(vector);
        return mean;
    }

    private Arith division = new Arith() {
        @Override
        public Double doMath(Double a, Double b) {
            if ( a == null ){
                a=0.0;
            }
            if ( b == null ) {
                b=0.0;
            }
            if ( b.equals(0.0) )
                return 0.0;

            return a/b;
        }
    };

    private Arith subtraction = new Arith() {
        @Override
        public Double doMath(Double a, Double b) {
            if ( a == null ){
                a=0.0;
            }
            if ( b == null ) {
                b=0.0;
            }
            return a-b;
        }
    };

    public double total_rows_and_col ( NumericMatrix matrix ) {
        class tmp {
            public Double total = 0.0;
        }
        tmp N = new tmp();
        matrix.walker(new NumericMatrix.IteratorAction() {
            @Override
            public void walk(String row_name, int row, String col_name, int col, double v) {
                N.total +=v;
            }
        });

        return N.total;
    }

    public NumericMatrix z_score ( NumericMatrix matrix, double mean, double stdev )
    {
        double [][] data = new double[matrix.getRows()][matrix.getCols()];
        matrix.walker(new NumericMatrix.IteratorAction() {
            @Override
            public void walk(String row_name, int row, String col_name, int col, double x) {
                double z = ( x - mean )/stdev;
                data[row][col] = z;
            }
        });
        NumericMatrix zs = new NumericMatrix(matrix.getRowNames(), matrix.getColNames(), data);
        return zs;
    }

    public NumericMatrix add ( NumericMatrix aM, NumericMatrix bM ) {
        NumericMatrix m = travel_rows_cols(aM, bM, addition );
        return m;
    }
    public NumericMatrix subtract ( NumericMatrix aM, NumericMatrix bM ) {
        NumericMatrix m = travel_rows_cols(aM, bM, subtraction );
        return m;
    }
    public NumericMatrix divide ( NumericMatrix aM, NumericMatrix bM ) {
        NumericMatrix m = travel_rows_cols(aM, bM, division );
        return m;
    }
    public NumericMatrix multiply ( NumericMatrix aM, NumericMatrix bM ) {

        RealMatrix results = aM.getRealMatrix().multiply(bM.getRealMatrix());
        NumericMatrix m = new NumericMatrix( aM.getRowNames(), bM.getColNames(), results );
//        if ( aM.getCols() == bM.getRows() ) {
//            int size = aM.getCols();
//            m = new NumericMatrix();
//            for ( int r = 0; r< aM.getRows(); r++ ) {
//                for ( int c =0; c<bM.getCols(); c++ ) {
//                    Double cv = 0.0;
//                    for ( int n = 0; n <size; n++ ) {
//                        Double av = aM.get(r, n);
//                        Double bv = bM.get(n, c);
//                        cv += (av * bv);
//                    }
//                    m.cell (r, c, cv);
//                }
//            }
//        }
        return m;
    }
    public NumericMatrix identity ( int dimensions ) {
        double [][] data = new double[dimensions][dimensions];
        for ( int i=0;i<dimensions; i++ ) {
            data[i][i] = 1;
        }
        NumericMatrix m = new NumericMatrix(data);
        return m;
    }
    private NumericMatrix travel_rows_cols ( NumericMatrix aM, NumericMatrix bM, Arith handler )
    {

        int a_rows = aM.getRows();
        int a_cols = aM.getCols();
        int b_rows = bM.getRows();
        int b_cols = bM.getCols();
        double [][] data = new double[a_rows][a_cols];
        if ( a_rows == b_rows && a_cols == b_cols ) {
            for ( int r =0; r<a_rows; r++ ) {
                for ( int c=0; c<a_cols; c++ ) {
                    Double av = aM.get(r,c);
                    Double bv = bM.get(r,c);
                    if (handler != null) {
                        Double cv = handler.doMath( av, bv );
                        data[r][c] = cv;
                    }
                }
            }
        }
        NumericMatrix cM = new NumericMatrix(data);
        return cM;
    }

    public interface GroupingHandler {
        public String getGroupName ( String row_name, int row );
    }


}
