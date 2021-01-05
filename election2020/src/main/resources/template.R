#!/usr/bin/env Rscript

library(MASS);
library("xlsx");


maxf <- function ( data )
{
    return (max(data, na.rm = TRUE));
}

minf <- function ( data )
{
    return (min(data, na.rm = TRUE));
}

presidential_analysis <- function (  inperson2020, absentee_2020, state2016, election_date_voting_2020, advanced_voting_2020 ) {


#
    res = svd(inperson2020);
    inperson_2020_V = res$v;
    inperson_2020_S = diag(res$d);
    inperson_2020_U = res$u;
    inperson_2020_Scaled = inperson_2020_U %*% inperson_2020_S;
    inperson_2020_Recon = inperson_2020_Scaled %*% t(inperson_2020_V);

    inperson_2020_C1 = minf(inperson_2020_V[,1])
    inperson_2020_C2 = maxf(inperson_2020_V[,2])


#
    res = svd(absentee_2020);
    absentee_2020_V = res$v;
    absentee_2020_S = diag(res$d);
    absentee_2020_U = res$u;
    absentee_2020_Scaled = absentee_2020_U %*% absentee_2020_S;
    absentee_2020_Recon = absentee_2020_Scaled %*% t(absentee_2020_V);

    absentee_2020_C1 = minf(absentee_2020_V[,1])
    absentee_2020_C2 = maxf(absentee_2020_V[,2])

#
    res = svd(state2016);
    state2016_V = res$v;
    state2016_S = diag(res$d);
    state2016_U = res$u;
    state2016_Scaled = state2016_U %*% state2016_S;
    state2016_Recon = state2016_Scaled %*% t(state2016_V);

    state2016_C1 = minf(state2016_V[,1])
    state2016_C2 = maxf(state2016_V[,2])

    state2016_X = state2016_V[1,]
    state2016_Y = state2016_V[2,]

#
    res = svd(election_date_voting_2020);
    election_date_voting_2020_V = res$v;
    election_date_voting_2020_S = diag(res$d);
    election_date_voting_2020_U = res$u;
    election_date_voting_2020_Scaled = election_date_voting_2020_U %*% election_date_voting_2020_S;
    election_date_voting_2020_Recon = election_date_voting_2020_Scaled %*% t(election_date_voting_2020_V);

#
    res = svd(advanced_voting_2020);
    advanced_voting_2020_V = res$v;
    advanced_voting_2020_S = diag(res$d);
    advanced_voting_2020_U = res$u;
    advanced_voting_2020_Scaled = advanced_voting_2020_U %*% advanced_voting_2020_S;
    advanced_voting_2020_Recon = advanced_voting_2020_Scaled %*% t(advanced_voting_2020_V);


    X = c (0, inperson_2020_C1*inperson_2020_Scaled[1,1], 0, inperson_2020_C2*inperson_2020_Scaled[1,2], 0, absentee_2020_C1*absentee_2020_Scaled[1,1], 0, absentee_2020_C2*absentee_2020_Scaled[1,2], 0, state2016_C1*state2016_Scaled[1,1], 0, state2016_C2*state2016_Scaled[1,2]
    );
    Y = c (0, inperson_2020_C1*inperson_2020_Scaled[2,1], 0, inperson_2020_C2*inperson_2020_Scaled[2,2], 0, absentee_2020_C1*absentee_2020_Scaled[2,1], 0, absentee_2020_C2*absentee_2020_Scaled[2,2], 0, state2016_C1*state2016_Scaled[2,1], 0, state2016_C2*state2016_Scaled[2,2]
    );



    xlimit = c(minf(X)*1.25, maxf(X)*1.25)
    ylimit = c(minf(Y)*1.25, maxf(Y)*1.25)



    jpeg('rplot.jpg')
    plot(state2016_X,state2016_Y, xlim=xlimit, ylim=ylimit, xlab="Democratic", ylab="Republican", main = "2020 v 2016 Voting Patterns")
    plot(X,Y, xlim=xlimit, ylim=ylimit, xlab="Democratic", ylab="Republican", main = "2020 v 2016 Voting Patterns")
    arrows(X[1], Y[1], X[2], Y[2], col = "green")
    arrows(X[3], Y[3], X[4], Y[4], col = "green")


    arrows(X[5], Y[5], X[6], Y[6],  col = "red")
    arrows(X[7], Y[7], X[8], Y[8],  col = "red")


    arrows(X[9], Y[9], X[10], Y[10],  col = "blue")
    arrows(X[11], Y[11], X[12], Y[12],  col = "blue")


    dev.off()
};
