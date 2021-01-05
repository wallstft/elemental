package org.neostorm.rcaller;

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

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.util.Globals;
import org.junit.Test;

public class RCallerSingularValueDecomposition {

    //https://github.com/jbytecode/rcaller/blob/master/doc/rcaller3/rcaller3.pdf



    static public SVD svd (double[][] matrix)
    {
        RCaller caller = null;
        RCode code = null;
        SVD svd = null;

        try {
            caller = RCaller.create();
            code = RCode.create();

            code.clear();
            code.addDoubleMatrix("d", matrix);
            code.addRCode("result <- svd(d)");
            caller.setRCode(code);
            caller.runAndReturnResult("result");

            svd = new SVD();
            svd.u = caller.getParser().getAsDoubleMatrix("u");
            svd.v = caller.getParser().getAsDoubleMatrix("v");
            svd.diag = caller.getParser().getAsDoubleArray("d");
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
        finally {

        }

        return svd;
    }

    static public void print ( String l, double[][] m )
    {
        System.out.println(String.format("%s", l ));
        for ( int r=0; r<m.length; r++ ) {
            double [] col= m[r];
            for ( int c = 0; c<col.length; c++ ) {
                System.out.print(String.format("%f\t", col[c]));
            }
            System.out.println("");
        }
        System.out.println("\n\n");
    }

    static public void print ( String l, double[]col )
    {
        System.out.println(String.format("%s", l ));
        for ( int c = 0; c<col.length; c++ ) {
            System.out.print(String.format("%f\t", col[c]));
        }
        System.out.println("");
        System.out.println("\n\n");
    }

    @Test
    public void svd_test() {
        repeat_test();
        repeat_test();
        repeat_test();
        repeat_test();
        repeat_test();
    }

    public void repeat_test()
    {
        double[][] d = new double[][] {{ 1,2,3},{4,5,6},{7,8,9}};
        SVD decomp = svd(d);
        print ("v", decomp.v);
        print ( "u", decomp.u );
        print ( "diag", decomp.diag );
    }

}
