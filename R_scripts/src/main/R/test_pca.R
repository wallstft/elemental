#!/usr/bin/env Rscript

library(MASS)

I = matrix(
c(1,0,0,0,
  0,1,0,0,
  0,0,1,0,
  0,0,0,1),4,4,TRUE)

D = matrix(
  c(1,0,0,0,1,
    0,1,0,0,1,
    0,0,1,0,0,
    0,0,0,1,0
    ),4,5,TRUE)



M = I%*%D

rownames (M) <- c("Row1", "Row2", "Row3", "Row4")
colnames (M) <- c("Col1","Col2","Col3","Col4","Col5")



print ("Original Matrix")

M

print ("SVD components")

res = svd(M)


v = res$v
s = diag(res$d);
u = res$u

print ("v" );
v
print ("s");
s
print ("u");
u

print ("multiplication back to original")


v %*% s %*% u

