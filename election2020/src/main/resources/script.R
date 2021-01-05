#!/usr/bin/env Rscript

library(MASS);
library("xlsx");

customMean <- function(vector) {
    mean(vector)
};


house_analysis <- function (file, House2016, House2020)
{
    D = House2020 -  House2016;
    write.xlsx(House2016, file, sheetName = "House2016", col.names = TRUE, row.names = TRUE, append = FALSE);
    write.xlsx(House2020, file, sheetName = "House2020", col.names = TRUE, row.names = TRUE, append = TRUE);
    write.xlsx(D, file, sheetName = "Difference", col.names = TRUE, row.names = TRUE, append = TRUE);
};

presidential_analysis <- function (  filename, before_name, before_m, before_row_name, before_col_names, after_name, after_m, after_row_names, after_col_names ) {


    D = after_m - before_m;

    colnames (D) <- after_col_names

    res = svd(D);

    V = res$v;
    S = diag(res$d);
    U = res$u;

    rownames(V) <- after_col_names

    Scaled = U %*% S;

    Recon = Scaled %*% t(V);

    file = filename;

    write.xlsx(before_m, file, sheetName = "Before", col.names = TRUE, row.names = TRUE, append = FALSE);
    write.xlsx(after_m, file, sheetName = "After", col.names = TRUE, row.names = TRUE, append = TRUE);
    write.xlsx(D, file, sheetName = "Diff", col.names = TRUE, row.names = TRUE, append = TRUE);
    write.xlsx(U, file, sheetName = "U", col.names = TRUE, row.names = TRUE, append = TRUE);
    write.xlsx(S, file, sheetName = "S", col.names = TRUE, row.names = TRUE, append = TRUE);
    write.xlsx(Scaled, file, sheetName = "Scaled", col.names = TRUE, row.names = TRUE, append = TRUE);
    write.xlsx(V, file, sheetName = "V", col.names = TRUE, row.names = TRUE, append = TRUE);

    write.xlsx(Recon, file, sheetName = "Recon", col.names = TRUE, row.names = TRUE, append = TRUE);

};



