#!/usr/bin/env Rscript

library(MASS)
library("xlsx")


Michigan2106 = matrix (c(
1732,1663,18050,4877,4448,2384,1156,9114,21642,4108,29495,5061,24157,7270,5137,4302,5379,4249,16492,2110,6436,3923,24938,6972,20965,102751,3794,2925,5666,4799,6018,4579,79110,8352,4345,2004,11404,25795,67148,2280,138683,527,1939,12734,6774,16750,34384,681,2085,176317,4979,16042,5281,5827,3539,15635,1565,26863,7874,1287,37304,6212,343070,3973,3030,1176,2705,1044,3556,44973,2400,4287,44396,4873,1369,12546,24553,7526,7429,13258,128483,519444,4436
,4201,2585,34183,9090,8469,4950,2158,19202,28328,5539,38647,11786,31494,14243,8674,8683,9122,8505,21636,4354,11121,8580,27609,10616,27413,84175,8124,4018,9880,14095,8475,10692,43868,16635,8345,3675,12338,39793,51034,6116,148180,814,3159,30037,7239,26430,65680,1756,3744,224665,6915,14646,8505,10305,6702,23846,5386,43261,16907,3498,36127,15173,289203,7228,6827,2066,7336,2843,8266,88467,4488,8141,45469,13446,2556,19230,49051,14884,17102,17890,50631,228993,10000

), 2,83, TRUE)

row_names <- c(
              "PRESIDENT-Democratic-Clinton",
              "PRESIDENT-Republican-Trump"

                            )



column_names <- c("ALCONA",
                             "ALGER",
                             "ALLEGAN",
                             "ALPENA",
                             "ANTRIM",
                             "ARENAC",
                             "BARAGA",
                             "BARRY",
                             "BAY",
                             "BENZIE",
                             "BERRIEN",
                             "BRANCH",
                             "CALHOUN",
                             "CASS",
                             "CHARLEVOIX",
                             "CHEBOYGAN",
                             "CHIPPEWA",
                             "CLARE",
                             "CLINTON",
                             "CRAWFORD",
                             "DELTA",
                             "DICKINSON",
                             "EATON",
                             "EMMET",
                             "GD. TRAVERSE",
                             "GENESEE",
                             "GLADWIN",
                             "GOGEBIC",
                             "GRATIOT",
                             "HILLSDALE",
                             "HOUGHTON",
                             "HURON",
                             "INGHAM",
                             "IONIA",
                             "IOSCO",
                             "IRON",
                             "ISABELLA",
                             "JACKSON",
                             "KALAMAZOO",
                             "KALKASKA",
                             "KENT",
                             "KEWEENAW",
                             "LAKE",
                             "LAPEER",
                             "LEELANAU",
                             "LENAWEE",
                             "LIVINGSTON",
                             "LUCE",
                             "MACKINAC",
                             "MACOMB",
                             "MANISTEE",
                             "MARQUETTE",
                             "MASON",
                             "MECOSTA",
                             "MENOMINEE",
                             "MIDLAND",
                             "MISSAUKEE",
                             "MONROE",
                             "MONTCALM",
                             "MONTMORENCY",
                             "MUSKEGON",
                             "NEWAYGO",
                             "OAKLAND",
                             "OCEANA",
                             "OGEMAW",
                             "ONTONAGON",
                             "OSCEOLA",
                             "OSCODA",
                             "OTSEGO",
                             "OTTAWA",
                             "PRESQUE ISLE",
                             "ROSCOMMON",
                             "SAGINAW",
                             "SANILAC",
                             "SCHOOLCRAFT",
                             "SHIAWASSEE",
                             "ST. CLAIR",
                             "ST. JOSEPH",
                             "TUSCOLA",
                             "VAN BUREN",
                             "WASHTENAW",
                             "WAYNE",
                             "WEXFORD" )

rownames (Michigan2106) <- row_names
colnames (Michigan2106) <- column_names






res = svd(Michigan2106)

#res$u

V = res$v
S = diag(res$d)
U = res$u



rownames (U) <- row_names

rownames (V) <- column_names

Scaled = U %*% S

Recon = Scaled %*% t(V)


file = "Michgan2016_President_Analysis.xlsx"

write.xlsx(Michigan2106, file, sheetName = "Michigan2106", col.names = TRUE, row.names = TRUE, append = FALSE)
write.xlsx(U, file, sheetName = "U", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(S, file, sheetName = "S", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(Scaled, file, sheetName = "Scaled", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(V, file, sheetName = "V", col.names = TRUE, row.names = TRUE, append = TRUE)

write.xlsx(Recon, file, sheetName = "Recon", col.names = TRUE, row.names = TRUE, append = TRUE)


