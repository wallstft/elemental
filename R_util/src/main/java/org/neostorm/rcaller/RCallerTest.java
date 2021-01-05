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

import com.github.rcaller.graphics.SkyTheme;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.scriptengine.RCallerScriptEngine;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.util.Random;

public class RCallerTest {


    @Test
    public void rCallerTest()
    {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("RCaller");

            engine.put("a", 25);

            engine.eval("b <- sqrt(a)");

            double[] result = (double[]) engine.get("b");

            System.out.println("b is " + result[0]);

            ((RCallerScriptEngine) engine).close();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    @Test
    public void BasicGraphics() {
        try {
            RCaller caller = RCaller.create();
            RCode code = RCode.create();

            code.addRCode("x <- rnorm(30)");
            code.addRCode("y <- rnorm(30)");
            code.addRCode("ols <- lm(y~x)");

            caller.setGraphicsTheme(new SkyTheme());

            File plt = code.startPlot();
            code.addRCode("barplot(x,y)");
            code.addRCode("abline(ols$coefficients[1], ols$coefficients[2])");
            code.addRCode("abline(mean(y), 0)");
            code.addRCode("abline(v = mean(x))");
            code.endPlot();

            caller.setRCode(code);
            caller.runAndReturnResult("ols");

            code.showPlot(plt);

            Thread.sleep(60*1000 );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DescriptiveStatistics() {
        try {

            /**
             * Creating Java's random number generator
             */
            Random random = new Random();

            /**
             * Creating RCaller
             */
            RCaller caller = RCaller.create();
            RCode code = RCode.create();

            /**
             *  We are creating a random data from a normal distribution
             * with zero mean and unit variance with size of 100
             */
            double[] data = new double[100];

            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextGaussian();
            }

            /**
             * We are transferring the double array to R
             */
            code.addDoubleArray("x", data);

            /**
             * Adding R Code
             */
            code.addRCode("my.mean<-mean(x)");
            code.addRCode("my.var<-var(x)");
            code.addRCode("my.sd<-sd(x)");
            code.addRCode("my.min<-min(x)");
            code.addRCode("my.max<-max(x)");
            code.addRCode("my.standardized<-scale(x)");

            /**
             * Combining all of them in a single list() object
             */
            code.addRCode(
                    "my.all<-list(mean=my.mean, variance=my.var, sd=my.sd, min=my.min, max=my.max, std=my.standardized)");

            /**
             * We want to handle the list 'my.all'
             */
            caller.setRCode(code);
            caller.runAndReturnResult("my.all");

            double[] results;

            /**
             * Retrieving the 'mean' element of list 'my.all'
             */
            results = caller.getParser().getAsDoubleArray("mean");
            System.out.println("Mean is " + results[0]);

            /**
             * Retrieving the 'variance' element of list 'my.all'
             */
            results = caller.getParser().getAsDoubleArray("variance");
            System.out.println("Variance is " + results[0]);

            /**
             * Retrieving the 'sd' element of list 'my.all'
             */
            results = caller.getParser().getAsDoubleArray("sd");
            System.out.println("Standard deviation is " + results[0]);

            /**
             * Retrieving the 'min' element of list 'my.all'
             */
            results = caller.getParser().getAsDoubleArray("min");
            System.out.println("Minimum is " + results[0]);

            /**
             * Retrieving the 'max' element of list 'my.all'
             */
            results = caller.getParser().getAsDoubleArray("max");
            System.out.println("Maximum is " + results[0]);

            /**
             * Retrieving the 'std' element of list 'my.all'
             */
            results = caller.getParser().getAsDoubleArray("std");

            /**
             * Now we are retrieving the standardized form of vector x
             */
            System.out.println("Standardized x is ");

            for (double result : results) {
                System.out.print(result + ", ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum SimType {
        Vector, Matrix
    }

    /**
     * Random vector generator
     *
     * @param size Size of vector
     * @return Generated vector
     */
    private final static double[] generateRandomVector(int size) {

        double[] d = new double[size];
        for (int i = 0; i < size; i++) {
            d[i] = Math.random();
        }

        return d;
    }
    private final static double[][] generateRandomMatrix(int n, int m) {

        double[][] d = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                d[i][j] = Math.random();
            }
        }

        return d;
    }

    @Test
    public void test_matrix ()
    {
        performSimulation(/* size of vector */ 10, 10 /* times */ , SimType.Matrix);
    }

    public static void performSimulation(int VectorSize, int SimCount, SimType type) {

        RCaller caller = RCaller.create();
        RCode code = RCode.create();
        int[] elapsed = new int[SimCount];

        System.out.println("- Simulations started.");

        for (int simulations = 0; simulations < SimCount; simulations++) {

            int timeStart = (int) System.currentTimeMillis();
            code.clear();
            if (type == SimType.Vector) {
                code.addDoubleArray("randomvector", generateRandomVector(VectorSize));
                code.addRCode("result <- randomvector^2.0");
            } else {
                code.addDoubleMatrix("randommatrix", generateRandomMatrix(VectorSize, VectorSize));
                code.addRCode("result <- t(randommatrix)");
            }
            caller.setRCode(code);
            caller.runAndReturnResultOnline("result");
            /* return variable is not handled */ caller.getParser().getAsDoubleArray("result");
            //caller.getParser().getAsDoubleMatrix("name");
            elapsed[simulations] = (int) System.currentTimeMillis() - timeStart;

        }

        System.out.println("- Simulations finished. Calculating statistics:");

        code.clear();
        code.addIntArray("times", elapsed);
        code.addRCode("stats <- c(min(times), max(times), mean(times), sd(times), median(times), mad(times))");
        caller.setRCode(code);
        caller.runAndReturnResultOnline("stats");

        double[] stats = caller.getParser().getAsDoubleArray("stats");

        System.out.printf("%10s %10s %10s %10s %10s %10s\n", "Min", "Max", "Mean", "Std.Dev.", "Median", "Mad");
        System.out.printf("%10f %10f %10f %10f %10f %10f\n", stats[0], stats[1], stats[2], stats[3], stats[4],
                stats[5]);
        caller.stopRCallerOnline();
    }
}
