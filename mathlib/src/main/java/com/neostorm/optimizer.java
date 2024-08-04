package com.neostorm;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.junit.jupiter.api.Test;

public class optimizer {

    @Test
    public void mip_optimizer () throws Exception
    {
        Loader.loadNativeLibraries();
        // Create the linear solver with the SCIP backend.
        MPSolver solver = MPSolver.createSolver("SCIP");
        if (solver == null) {
            System.out.println("Could not create solver SCIP");
            return;
        }

        double infinity = java.lang.Double.POSITIVE_INFINITY;
// x and y are integer non-negative variables.
        MPVariable x = solver.makeIntVar(0.0, infinity, "x");
        MPVariable y = solver.makeIntVar(0.0, infinity, "y");

        System.out.println("Number of variables = " + solver.numVariables());

        // x + 7 * y <= 17.5.
        MPConstraint c0 = solver.makeConstraint(-infinity, 17.5, "c0");
        c0.setCoefficient(x, 1);
        c0.setCoefficient(y, 7);

// x <= 3.5.
        MPConstraint c1 = solver.makeConstraint(-infinity, 3.5, "c1");
        c1.setCoefficient(x, 1);
        c1.setCoefficient(y, 0);

        System.out.println("Number of constraints = " + solver.numConstraints());

        // Maximize x + 10 * y
        MPObjective objective = solver.objective();
        objective.setCoefficient(x, 1);
        objective.setCoefficient(y, 10);
        objective.setMaximization();

        final MPSolver.ResultStatus resultStatus = solver.solve();

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Solution:");
            System.out.println("Objective value = " + objective.value());
            System.out.println("x = " + x.solutionValue());
            System.out.println("y = " + y.solutionValue());
        } else {
            System.err.println("The problem does not have an optimal solution!");
        }

    }
}
