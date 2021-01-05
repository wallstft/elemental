#!/usr/bin/env Rscript

library(MASS);
library("xlsx");

#
#https://stat.ethz.ch/R-manual/R-patched/library/graphics/html/arrows.html
#arrows

x <- stats::runif(12); y <- stats::rnorm(12)
i <- order(x, y); x <- x[i]; y <- y[i]
plot(x,y, main = "arrows(.) and segments(.)")
## draw arrows from point to point :
s <- seq(length(x)-1)  # one shorter than data
arrows(x[s], y[s], x[s+1], y[s+1], col = 1:3)
s <- s[-length(s)]
segments(x[s], y[s], x[s+2], y[s+2], col = "pink")


X = c(0,   -0.85619794,	0,  -0.51664794)
Y = c(0,   -0.51664794,	0,   0.85619794)
plot(X,Y, xlim=c(-1,1), ylim=c(-1,1), xlab="X", ylab="Y", main = "arrows(.)")
arrows(X[1], Y[1], X[2], Y[2], col = 1:3)
arrows(X[3], Y[3], X[4], Y[4], col = 1:3)


#https://stackoverflow.com/questions/10882336/plotting-vectors-in-a-coordinate-system-with-r-or-python
#
#Plotting coordinate vectors
plot(NA, xlim=c(0,5), ylim=c(0,5), xlab="X", ylab="Y")
vecs <- data.frame(vname=c("a","b","a+b", "transb"),
                   x0=c(0,0,0,2),y0=c(0,0,0,1), x1=c(2,1,3,3) ,y1=c(1,2,3,3),
                   col=1:4)
with( vecs, mapply("arrows", x0, y0, x1,y1,col=col) )


plot(NA, xlim=c(-1,1), ylim=c(-1,1), xlab="X", ylab="Y")
vecs <- data.frame(vname=c("a","b"),
                   x0=c(0,0,-0.85619794,-0.51664794),y0=c(0,0,-0.51664794,0.85619794),
                   x1=c(0,0,-0.85619794,-0.51664794),y1=c(0,0,-0.51664794,0.85619794),
                   col=1:4)
with( vecs, mapply("arrows", x0, y0,x1,y1col=col) )


-0.85619794,	-0.51664794,
-0.51664794,	0.85619794





#
#https://stat.ethz.ch/R-manual/R-patched/library/graphics/html/rect.html
# Plotting rectangles
require(grDevices)
## set up the plot region:
op <- par(bg = "thistle")
plot(c(100, 250), c(300, 450), type = "n", xlab = "", ylab = "",
     main = "2 x 11 rectangles; 'rect(100+i,300+i,  150+i,380+i)'")
i <- 4*(0:10)
## draw rectangles with bottom left (100, 300)+i
## and top right (150, 380)+i
rect(100+i, 300+i, 150+i, 380+i, col = rainbow(11, start = 0.7, end = 0.1))
rect(240-i, 320+i, 250-i, 410+i, col = heat.colors(11), lwd = i/5)
## Background alternating  ( transparent / "bg" ) :
j <- 10*(0:5)
rect(125+j, 360+j,   141+j, 405+j/2, col = c(NA,0),
     border = "gold", lwd = 2)
rect(125+j, 296+j/2, 141+j, 331+j/5, col = c(NA,"midnightblue"))
mtext("+  2 x 6 rect(*, col = c(NA,0)) and  col = c(NA,\"m..blue\")")

## an example showing colouring and shading
plot(c(100, 200), c(300, 450), type= "n", xlab = "", ylab = "")
rect(100, 300, 125, 350) # transparent
rect(100, 400, 125, 450, col = "green", border = "blue") # coloured
rect(115, 375, 150, 425, col = par("bg"), border = "transparent")
rect(150, 300, 175, 350, density = 10, border = "red")
rect(150, 400, 175, 450, density = 30, col = "blue",
     angle = -30, border = "transparent")

legend(180, 450, legend = 1:4, fill = c(NA, "green", par("fg"), "blue"),
       density = c(NA, NA, 10, 30), angle = c(NA, NA, 30, -30))

par(op)