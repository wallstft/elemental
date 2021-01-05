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

public class RCallerBase {
    RCaller caller = null;
    RCode code = null;
    public void init()
    {
        if( caller == null ) {
            caller = RCaller.create();
            code = RCode.create();
        }
    }


    protected final static double[][] generateRandomMatrix(int n, int m) {

        double[][] d = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                d[i][j] = Math.random();
            }
        }

        return d;
    }

    protected final static double[][] countMatrix(int n, int m) {

        double[][] d = new double[n][m];
        double c=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                d[i][j] = c++;
            }
        }

        return d;
    }
}
