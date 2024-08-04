package com.neostorm;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.GradientFunction;
import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableVectorFunction;
import org.apache.commons.math3.analysis.solvers.BracketingNthOrderBrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.junit.jupiter.api.Test;

public class MultifactorDifferentiationTest {

//    public double[][] value(double[] point) {
//        // set up parameters
//        final DerivativeStructure[] dsX = new DerivativeStructure[point.length];
//        for (int i = 0; i < point.length; ++i) {
//            dsX[i] = new DerivativeStructure(point.length, 1, i, point[i]);
//        }
//        // compute the derivatives
//        final DerivativeStructure[] dsY = f.value(dsX);
//        // extract the Jacobian
//        final double[][] y = new double[dsY.length][point.length];
//        final int[] orders = new int[point.length];
//        for (int i = 0; i < dsY.length; ++i) {
//            for (int j = 0; j < point.length; ++j) {
//                orders[j] = 1;
//                y[i][j] = dsY[i].getPartialDerivative(orders);
//                orders[j] = 0;
//            }
//        }
//        return y;
//    }


    //https://www.baeldung.com/apache-commons-math

    @Test
    public void test () throws Exception
    {
        UnivariateFunction function = v -> Math.pow(v, 2) - 16;
        UnivariateSolver solver = new BracketingNthOrderBrentSolver(1.0e-12, 1.0e-8, 5);
        double c = solver.solve(100, function, -10.0, 10.0, 0);

        System.out.println(String.format("Solution = %f", c));
    }


    @Test
    public void test2 () throws Exception
    {
        UnivariateFunction function = new UnivariateFunction() {
            @Override
            public double value(double v) {
                return Math.pow(v, 2) - 16;
            }
        };
        /*
        final UnivariateSolver solver = new BrentSolver(absoluteAccuracy);
        return solver.solve(Integer.MAX_VALUE, function, x0, x1);
         */
        UnivariateSolver solver = new BracketingNthOrderBrentSolver(1.0e-12, 1.0e-8, 5);
//        double c = solver.solve(100, function, -10.0, 10.0, 0);
        double c = solver.solve(100, function, -10.0, 10.0);

        System.out.println(String.format("Solution = %f", c));
    }

    @Test
    public void gradientFunctionTest () throws Exception
    {
        MultivariateDifferentiableFunction function = new MultivariateDifferentiableFunction() {
            @Override
            public DerivativeStructure value(DerivativeStructure[] derivativeStructures) throws MathIllegalArgumentException {
                DerivativeStructure ret = null;
                if ( derivativeStructures != null ) {
                    double[] vector = new double[derivativeStructures.length];
                    for ( int i =0; i<derivativeStructures.length; i++ ) {
                        DerivativeStructure  d = derivativeStructures[i];
                        vector[i] = d.getValue();
                    }
                    double x = value(vector);
                    ret = new DerivativeStructure(1, 1, 0, x );
                }
                return ret;
            }

            @Override
            public double value(double[] vector) {
                return Math.pow(vector[0], 2);
            }
        };
        GradientFunction gradientFunction = new GradientFunction(function);

        double [] x_vector = { 5.0 };
        double y = function.value(x_vector);
        double[] v = gradientFunction.value(x_vector);

        for ( int i=0; i<v.length; i++ ) {
            System.out.println(String.format("Solution function(y=%f) Index(%d) = %f",y, i, v[i]));
        }
    }


    @Test
    public void test3 ()
    {
        MultivariateDifferentiableVectorFunction f = new MultivariateDifferentiableVectorFunction(){
            @Override
            public double[] value(double[] doubles) throws IllegalArgumentException {
                return new double[0];
            }

            @Override
            public DerivativeStructure[] value(DerivativeStructure[] derivativeStructures) throws MathIllegalArgumentException {
                return new DerivativeStructure[0];
            }
        };
    }

//    public double[][] value(double[] point) {
//        // set up parameters
//        final DerivativeStructure[] dsX = new DerivativeStructure[point.length];
//        for (int i = 0; i < point.length; ++i) {
//            dsX[i] = new DerivativeStructure(point.length, 1, i, point[i]);
//        }
//        // compute the derivatives
//        final DerivativeStructure[] dsY = f.value(dsX);
//        // extract the Jacobian
//        final double[][] y = new double[dsY.length][point.length];
//        final int[] orders = new int[point.length];
//        for (int i = 0; i < dsY.length; ++i) {
//            for (int j = 0; j < point.length; ++j) {
//                orders[j] = 1;
//                y[i][j] = dsY[i].getPartialDerivative(orders);
//                orders[j] = 0;
//            }
//        }
//        return y;
//    }
}
