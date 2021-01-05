#!/usr/bin/env Rscript

library(MASS);
library("xlsx");

U = matrix( c(-0.85619794,	-0.51664794,
-0.51664794,	0.85619794),2,2,TRUE)

S = matrix ( c(
228719.3317,	0,
0,	39589.73425), 2,2, TRUE)


Scaled= U%*%S


Scaled_X = c(0,   Scaled[1,1],	0,  Scaled[2,1])
Scaled_Y = c(0,   Scaled[2,1],	0,  Scaled[2,2])


ab_X = c(0,   0.94243914,	0,  -0.33437773)
ab_Y = c(0,   0.33437773,	0,   0.94243914)
X = c(0,   0.85619794,	0,  -0.51664794)
Y = c(0,   0.51664794,	0,   0.85619794)


jpeg('rplot.jpg')
plot(X,Y, xlim=c(-1,1), ylim=c(-1,1), xlab="X", ylab="Y", main = "arrows(.)")
arrows(X[1], Y[1], X[2], Y[2], col = "green")
arrows(X[3], Y[3], X[4], Y[4], col = "green")
arrows(ab_X[1], ab_Y[1], ab_X[2], ab_Y[2],  col = "blue")
arrows(ab_X[3], ab_Y[3], ab_X[4], ab_Y[4],  col = "blue")
arrows(Scaled_X[1], Scaled_Y[1], Scaled_X[2], Scaled_Y[2],  col = "red")
arrows(Scaled_X[3], Scaled_Y[3], Scaled_X[4], Scaled_Y[4],  col = "red")

dev.off()