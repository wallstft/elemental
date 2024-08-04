package com.neostorm;

import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.DimensionMismatchException;

public class ApacheSandBox {

    public static void main(String[] args) {

        ApacheSandBox sandBox = new ApacheSandBox();

        sandBox.testOldWay();

        sandBox.testNewWay();

    }

    public void testOldWay() {
        // First create a quadratic function that has a specified minimum
        DifferentiableUnivariateFunction quadFunction = new Quadratic3_0(1, 5);
        System.out.println("Using the old DifferentiableUnivariateFunction way");
        // To get the value of the function
        System.out.println("Value at x=3: " + quadFunction.value(3));
        System.out.println("Value at x=5: " + quadFunction.value(5));
        System.out.println("Value at x=6: " + quadFunction.value(6));

        // To get the value of the derivative you need to pull out the derivative function first
        System.out.println("Derivative at x=3: " + quadFunction.derivative().value(3));
        System.out.println("Derivative at x=5: " + quadFunction.derivative().value(5));
        System.out.println("Derivative at x=6: " + quadFunction.derivative().value(6));

    }

    public void testNewWay() {
        // First create a quadratic function that has a specified minimum

//        UnivariateDifferentiableFunction quadFunction = new Quadratic3_2(1, 5);
        final UnivariateDifferentiableFunction cdf = new UnivariateDifferentiableFunction() {

            DerivativeStructure k = null;
            DerivativeStructure one = null;

            @Override
            public double value(double x) {

                // This method is nice in that you don't have to repeat yourself
                return value(new DerivativeStructure(1,3,x)).getValue();

                // This would also work, and is almost certainly faster!
                //return a*(x-min)*(x-min);
            }

            @Override
            public DerivativeStructure value(DerivativeStructure x)
                    throws DimensionMismatchException {
                if ( k == null ) {
                    k = x.createConstant(2.0*(Math.sqrt(2 / Math.PI)));
                }
                if ( one == null ) {
                    one = x.createConstant(1);
                }
                DerivativeStructure r = k.multiply(x).exp().divide(one.add(k.multiply(x).exp()));
                return r;
            }
        };

        UnivariateDifferentiableFunction BlackScholes = new UnivariateDifferentiableFunction() {

            DerivativeStructure K = null; //100;
            DerivativeStructure T = null; // 1
            DerivativeStructure V = null; // .2
            DerivativeStructure R = null; // .04

            double k = 100;
            double t = 1;
            double v = .2;
            double r = .04;

            @Override
            public double value(double S) {

                // This method is nice in that you don't have to repeat yourself
                return value(new DerivativeStructure(1,3,S)).getValue();

                // This would also work, and is almost certainly faster!
                //return a*(x-min)*(x-min);
            }
            private void init (DerivativeStructure S) {
                if ( K == null ) {
                    K = S.createConstant(k);
                }
                if ( T == null ) {
                    T = S.createConstant(t);
                }
                if ( R == null ) {
                    R = S.createConstant(r);
                }
                if ( V == null ) {
                    V = S.createConstant(v);
                }
            }

            @Override
            public DerivativeStructure value(DerivativeStructure S)
                    throws DimensionMismatchException {
                DerivativeStructure d1 = S.divide(K).log().add(T.multiply(R.add(V.pow(2.0).divide(2.0))));
                DerivativeStructure d2 = d1.subtract(V.multiply(T.sqrt()));
                DerivativeStructure call = cdf.value(d1).multiply(S).subtract(cdf.value(d2).multiply(K).multiply(R.multiply(T).negate().exp()));

                return call;
            }
        };

        /*
            private double approxCDF ( double x )
    {
        double k = Math.sqrt(2/Math.PI);
        double p = Math.exp(2*k*x)/(1+Math.exp(2*k*x));
        return p;
    }
         */

        UnivariateDifferentiableFunction quadFunction = new UnivariateDifferentiableFunction() {
            @Override
            public double value(double x) {

                // This method is nice in that you don't have to repeat yourself
                return value(new DerivativeStructure(1,3,x)).getValue();

                // This would also work, and is almost certainly faster!
                //return a*(x-min)*(x-min);
            }

            @Override
            public DerivativeStructure value(DerivativeStructure t)
                    throws DimensionMismatchException {
                DerivativeStructure r = t.pow(3).add(t.pow(2)).add(t);
                return r;
            }
        };
        System.out.println("Using the new UnivariateDifferentiableFunction way");

        // To get the value of the function
        System.out.println("Value at x=3: " + quadFunction.value(3));
        System.out.println("Value at x=5: " + quadFunction.value(5));
        System.out.println("Value at x=6: " + quadFunction.value(6));

        // To get the value of the derivative you need to pull out the derivative function first
        System.out.println("Derivative at x=3: " + quadFunction.value(new DerivativeStructure(1,3,0,3)).getPartialDerivative(1));
        System.out.println("Derivative at x=5: " + quadFunction.value(new DerivativeStructure(1,3,0,5)).getPartialDerivative(1));
        System.out.println("Derivative at x=6: " + quadFunction.value(new DerivativeStructure(1,3,0,6)).getPartialDerivative(1));


        System.out.println("Second Derivative at x=3: " + quadFunction.value(new DerivativeStructure(1,3,0,3)).getPartialDerivative(2));
        System.out.println("Second Derivative at x=5: " + quadFunction.value(new DerivativeStructure(1,3,0,5)).getPartialDerivative(2));
        System.out.println("Second Derivative at x=6: " + quadFunction.value(new DerivativeStructure(1,3,0,6)).getPartialDerivative(2));

        System.out.println("Third Derivative at x=3: " + quadFunction.value(new DerivativeStructure(1,3,0,3)).getPartialDerivative(3));
        System.out.println("Third Derivative at x=5: " + quadFunction.value(new DerivativeStructure(1,3,0,5)).getPartialDerivative(3));
        System.out.println("Third Derivative at x=6: " + quadFunction.value(new DerivativeStructure(1,3,0,6)).getPartialDerivative(3));


        double max = 0;
        double max_x = 0;
        double total_diff = 0;
        double abs_total_diff = 0;
        for ( double x=-3; x<3; x+=.0001 ) {
            NormalDistribution nd = new NormalDistribution(0, 1);
            double prob = nd.cumulativeProbability(x);
            System.out.println("nd.cumulativeProbability(0) = " + prob);

//            double approx = approxCDF(x);
            double approx = cdf.value(x);
            System.out.println("approxCDF(0) = " + approx);

            double diff = prob - approx;
            if ( Math.abs(max)<Math.abs(diff)) {
                max = diff;
                max_x= x;
            }
            total_diff += diff;
            abs_total_diff += Math.abs(diff);
        }

        System.out.println(String.format("Max X = %f, Max Diff = %f, TotalDiff=%f, AbsTotalDiff=%f", max_x, max, total_diff, abs_total_diff ));

    }

    private double approxCDF ( double x )
    {
        double k = Math.sqrt(2/Math.PI);
        double p = Math.exp(2*k*x)/(1+Math.exp(2*k*x));
        return p;
    }

    //   DifferentiableUnivariateFunctions can seem odd at first. It extends
    //   UnivariateFunction, which is where it gets its value method from, and
    //   then adds a new method that returns a UnivariateFunction that represents
    //   the derivative. So a DifferentiableUnivariateFunction has a .value method
    //   that returns the value, and then a method that returns a different
    //   UnivariateFunction whose .value method returns the derivative.
    public class Quadratic3_0 implements DifferentiableUnivariateFunction {
        private double a;
        private double min;

        public Quadratic3_0(double a, double min) {
            this.a = a;
            this.min = min;
        }

        @Override
        public double value(double x) {
            return a*(x-min)*(x-min);
        }

        @Override
        public UnivariateFunction derivative() {
            return new UnivariateFunction() {
                @Override
                public double value(double x) {
                    return 2*(x-min);
                }
            };
        }

    }

    //   The new way of doing things uses a UnivariateDifferentiableFunction (I know, the name is almost
    //   the same as the old, they probably did this to allow people to continue using the deprecated
    //   older classes). This beast works in an entirely different way! Getting the value of the function
    //   is still the same, but now the derivative requires more legwork.
    public class Quadratic3_2 implements UnivariateDifferentiableFunction {
        private double a;
        private double min;

        public Quadratic3_2(double a, double min) {
            this.a = a;
            this.min = min;
        }

        @Override
        public double value(double x) {

            // This method is nice in that you don't have to repeat yourself
            return value(new DerivativeStructure(1,1,x)).getValue();

            // This would also work, and is almost certainly faster!
            //return a*(x-min)*(x-min);
        }

        @Override
        public DerivativeStructure value(DerivativeStructure t)
                throws DimensionMismatchException {
            return t.subtract(min).pow(2).multiply(a);
        }

    }
}