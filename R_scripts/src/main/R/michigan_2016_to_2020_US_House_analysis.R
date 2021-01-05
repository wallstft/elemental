#!/usr/bin/env Rscript

library(MASS)
library("xlsx")


US_House_Race_Oakland = matrix ( c (
137331.00, 92626.00
,141893.00,129349.00

,130404.00,	113703.00
,41348.00, 	37422.00

,71460.00,	41727.00
,85911.00,	87536.00

,85338.00,	68492.00
,44577.00,	42963.00 ),
 8,2, TRUE);

US_House_Race_Oakland_row_names <- c (
    "11th District Representative in Congress 2 Year Term Position-DEM",
    "11th District Representative in Congress 2 Year Term Position-REP",
    "14th District Representative in Congress 2 Year Term Position-DEM",
    "14th District Representative in Congress 2 Year Term Position-REP",
    "8th District Representative in Congress 2 Year Term Position-DEM",
    "8th District Representative in Congress 2 Year Term Position-REP",
    "9th District Representative in Congress 2 Year Term Position-DEM",
    "9th District Representative in Congress 2 Year Term Position-REP"
);


US_House_Race_Oakland_col_names <- c ( "2020 Votes", "2016 Votes", "Difference", "Percentage Increase" );

US_House_Race_Oakland <- cbind(US_House_Race_Oakland, c(0,0,0,0,0,0,0,0) )
US_House_Race_Oakland <- cbind(US_House_Race_Oakland, c(0,0,0,0,0,0,0,0) )


rownames (US_House_Race_Oakland) <- US_House_Race_Oakland_row_names

for(x in 1:nrow(US_House_Race_Oakland)) {
    US_House_Race_Oakland[x,3] <- US_House_Race_Oakland[x,1] - US_House_Race_Oakland[x,2]
    US_House_Race_Oakland[x,4] <- US_House_Race_Oakland[x,3] / as.double(US_House_Race_Oakland[x,2])
}


colnames (US_House_Race_Oakland) <- US_House_Race_Oakland_col_names



Michigan2106 = matrix (c(
1732,1663,18050,4877,4448,2384,1156,9114,21642,4108,29495,5061,24157,7270,5137,4302,5379,4249,16492,2110,6436,3923,24938,6972,20965,102751,3794,2925,5666,4799,6018,4579,79110,8352,4345,2004,11404,25795,67148,2280,138683,527,1939,12734,6774,16750,34384,681,2085,176317,4979,16042,5281,5827,3539,15635,1565,26863,7874,1287,37304,6212,343070,3973,3030,1176,2705,1044,3556,44973,2400,4287,44396,4873,1369,12546,24553,7526,7429,13258,128483,519444,4436
,4201,2585,34183,9090,8469,4950,2158,19202,28328,5539,38647,11786,31494,14243,8674,8683,9122,8505,21636,4354,11121,8580,27609,10616,27413,84175,8124,4018,9880,14095,8475,10692,43868,16635,8345,3675,12338,39793,51034,6116,148180,814,3159,30037,7239,26430,65680,1756,3744,224665,6915,14646,8505,10305,6702,23846,5386,43261,16907,3498,36127,15173,289203,7228,6827,2066,7336,2843,8266,88467,4488,8141,45469,13446,2556,19230,49051,14884,17102,17890,50631,228993,10000

), 2,83, TRUE)

Michigan2020 = matrix (c(
2142,2053,24449,6000,5960,2774,1478,11797,26151,5480,37438,6159,28877,9130,6939,5437,6648,5199,21968,2672,7606,4744,31299,9662,28683,119390,4524,3570,6693,5883,7750,5490,94212,10901,5373,2493,14072,31995,83686,3002,187915,672,2288,16367,8795,20918,48220,842,2632,223952,6107,20465,6802,7375,4316,20493,1967,32975,9703,1628,45643,7873,434148,4944,3475,1391,3214,1342,4743,64705,2911,5166,51088,5966,1589,15347,31363,9262,8712,16803,157136,597170,5838
,4848,3014,41392,10686,9748,5928,2512,23471,33125,6601,43519,14064,36221,16699,9841,10186,10681,10861,25098,5087,13207,9617,31798,12135,30502,98714,9893,4600,12102,17037,10378,12731,47639,20657,9759,4216,14815,47372,56823,7436,165741,862,3946,35482,7916,31541,76982,2109,4304,263863,8321,16286,10207,13267,8117,27675,6648,52710,21815,4171,45133,18857,325971,8892,8253,2358,8928,3466,9779,100913,5342,9670,50785,16194,3090,23149,59185,18127,20297,21591,56241,264553,12102
), 2,83, TRUE)


MichiganDiff = Michigan2020 - Michigan2106


row_names <- c(
              "PRESIDENT-Democratic",
              "PRESIDENT-Republican"
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

rownames (MichiganDiff) <- row_names
colnames (MichiganDiff) <- column_names






res = svd(MichiganDiff)

#res$u

V = res$v
S = diag(res$d)
U = res$u



rownames (U) <- row_names

rownames (V) <- column_names

Scaled = U %*% S

Recon = Scaled %*% t(V)


file = "Michigan_2016_to_2020_US_House.xlsx"

write.xlsx(MichiganDiff, file, sheetName = "Michigan_2016_2020_Diff", col.names = TRUE, row.names = TRUE, append = FALSE)
write.xlsx(U, file, sheetName = "U", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(S, file, sheetName = "S", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(Scaled, file, sheetName = "Scaled", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(V, file, sheetName = "V", col.names = TRUE, row.names = TRUE, append = TRUE)

write.xlsx(Recon, file, sheetName = "Recon", col.names = TRUE, row.names = TRUE, append = TRUE)

write.xlsx(US_House_Race_Oakland, file, sheetName = "US_House_Race_Oakland", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(Michigan2020, file, sheetName = "Michigan2020", col.names = TRUE, row.names = TRUE, append = TRUE)
write.xlsx(Michigan2106, file, sheetName = "Michigan2106", col.names = TRUE, row.names = TRUE, append = TRUE)

