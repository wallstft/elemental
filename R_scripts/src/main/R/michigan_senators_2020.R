#!/usr/bin/env Rscript

library(MASS)
library("xlsx")


Michgan2020 = matrix (c(
2284,2089,22939,6273,5758,3085,1528,11369,27072,5328,34777,6119,28145,8565,6664,5475,6729,5372,21490,2653,7827,4881,31460,9216,27291,120380,4905,3556,6856,6237,7941,6021,92378,10639,5804,2554,13849,32649,78842,3013,175256,686,2286,16590,8277,19767,46118,855,2638,224448,5995,20408,6660,7228,4230,19927,1999,32721,9714,1717,44192,7696,418312,4796,3788,1464,3226,1384,4703,59187,3056,5309,51520,6244,1712,15830,31846,8824,9422,15944,150529,582367,5684
,4614,2919,42362,10257,9924,5443,2409,23499,31314,6692,44801,13682,35786,16814,10030,10026,10497,10468,25269,5021,12828,9431,30876,12506,31792,94505,9245,4436,11746,16227,10151,11730,48378,20361,9040,4063,14673,45054,60227,7328,176795,837,3804,34340,8380,31659,77802,2070,4263,252052,8356,16247,10238,13275,7795,28057,6598,50580,21146,4029,44637,18593,334629,8808,7758,2225,8808,3376,9727,106108,5167,9385,49209,15544,2905,22110,56476,18018,19174,21816,60745,260146,12122

), 2,83, TRUE)

row_names <- c(
               "Federal-US-Senator-Democratic",
               "Federal-US-Senator-Republican"
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

rownames (Michgan2020) <- row_names
colnames (Michgan2020) <- column_names

#data.frame(Michgan2020)





res = svd(Michgan2020)

#res$u

V = res$v
S = diag(res$d)
U = res$u



rownames (U) <- row_names

rownames (V) <- column_names

Scaled = U %*% S

Recon = Scaled %*% t(V)

file = "Michgan2020_senators_Analysis.xlsx"

write.xlsx(Michgan2020, file, sheetName = "Michgan2020", col.names = TRUE, row.names = TRUE, append = FALSE)
write.xlsx(U, file, sheetName = "U", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(S, file, sheetName = "S", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(Scaled, file, sheetName = "Scaled", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(V, file, sheetName = "V", col.names = TRUE, row.names = TRUE, append = TRUE)

write.xlsx(Recon, file, sheetName = "Recon", col.names = TRUE, row.names = TRUE, append = TRUE