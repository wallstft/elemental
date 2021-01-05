//package org.neostorm;
//
//
//
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.neostorm.data.*;
//import org.neostorm.db.SqlLiteManager;
//import org.neostorm.db.jdbcHandler;
//import org.neostorm.excel.NeoSheet;
//import org.neostorm.excel.NeoWorkbook;
//import org.neostorm.excel.charts.chartParameters.ChartParameters;
//import org.neostorm.excel.charts.chartParameters.LineChartParameters;
//import org.neostorm.excel.charts.core.ExcelChart;
//import org.neostorm.excel.charts.data_structure.ChartAnchorData;
//import org.neostorm.rcaller.RCallerSingularValueDecomposition;
//import org.neostorm.rcaller.SVD;
//import org.renjin.primitives.matrix.Matrix;
//import org.renjin.script.RenjinScriptEngine;
//import org.renjin.sexp.AttributeMap;
//import org.renjin.sexp.DoubleArrayVector;
//import org.renjin.sexp.DoubleVector;
//import org.renjin.sexp.Vector;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.net.URI;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.stream.Collectors;
//
///**
// * Hello world!
// *
// */
//public class App
//{
//
//    public String getMeanScriptContent() throws Exception {
//        URI rScriptUri = this.getClass().getClassLoader().getResource("script.R").toURI();
//        Path inputScript = Paths.get(rScriptUri);
//        return Files.lines(inputScript).collect(Collectors.joining());
//    }
//
//    public void matrix (RenjinScriptEngine engine) throws Exception
//    {
//        Vector res = (Vector)engine.eval("matrix(seq(9), nrow = 3)");
//        try {
//            Matrix m = new Matrix(res);
//
//            System.out.println("Result is a " + m.getNumRows() + "x"
//                    + m.getNumCols() + " matrix.");
//        } catch(IllegalArgumentException e) {
//            System.out.println("Result is not a matrix: " + e);
//        }
//    }
//
//    public void java_data(RenjinScriptEngine engine) throws Exception
//    {
//        engine.put("x", 4);
//        engine.put("y", new double[] { 1d, 2d, 3d, 4d });
//        engine.put("z", new DoubleArrayVector(1,2,3,4,5));
//        engine.put("hashMap", new java.util.HashMap());
//// some R magic to print all objects and their class with a for-loop:
//        engine.eval("for (obj in ls()) { " +
//                "cmd <- parse(text = paste('typeof(', obj, ')', sep = ''));" +
//                "cat('type of ', obj, ' is ', eval(cmd), '\\n', sep = '') }");
//    }
//
//    public double mean( RenjinScriptEngine engine, int[] values) throws Exception {
//
//        String meanScriptContent = getMeanScriptContent();
//        engine.put("input", values);
//        engine.eval(meanScriptContent);
//        DoubleArrayVector result = (DoubleArrayVector) engine.eval("customMean(input)");
//        return result.asReal();
//    }
//
//    public void test(RenjinScriptEngine engine) throws Exception
//    {
//        Vector res = (Vector)engine.eval("matrix(seq(9), nrow = 3)");
//        if (res.hasAttributes()) {
//            AttributeMap attributes = res.getAttributes();
//            Vector dim = attributes.getDim();
//            if (dim == null) {
//                System.out.println("Result is a vector of length " +
//                        res.length());
//
//            } else {
//                if (dim.length() == 2) {
//                    System.out.println("Result is a " +
//                            dim.getElementAsInt(0) + "x" +
//                            dim.getElementAsInt(1) + " matrix.");
//                } else {
//                    System.out.println("Result is an array with " +
//                            dim.length() + " dimensions.");
//                }
//            }
//        }
//    }
//
//    private void party_closure(SqlLiteManager sql, HashMap<String, NumericMatrix> matrix_map, String state)
//    {
//        try {
//            ArrayList<String> party_list = new ArrayList();
//            String sel_sql = String.format("select distinct party from election_data where office_type = 'HOUSE' and state = '%s'", state);
//            sql.executeQuery(sel_sql, new jdbcHandler() {
//                @Override
//                public void handler(ResultSet rs) throws Exception {
//                    while (rs.next()) {
//                        String party = rs.getString("party");
//                        party_list.add(party);
//                    }
//                }
//            });
//
//            sel_sql = String.format("select distinct election_date, state, county, office from election_data where office_type = 'HOUSE' and state = '%s'", state);
//            sql.executeQuery(sel_sql, new jdbcHandler() {
//                @Override
//                public void handler(ResultSet rs) throws Exception {
//                    while (rs.next()) {
//                        String election_date = rs.getString("election_date");
//                        String state = rs.getString("state");
//                        String county = rs.getString("county");
//                        String office = rs.getString("office");
//
//                        NumericMatrix m = matrix_map.get(String.format("%s-%s", election_date, state));
//                        if (m == null) {
//                            matrix_map.put(String.format("%s-%s", election_date, state), m = new NumericMatrix());
//                        }
//                        for ( String party : party_list ) {
//                            String row_key = String.format("%s-%s-%s-%s-%s", election_date, state, county, office, party);
//                            String col_key = county;
//
//                            if ( !m.rowKeyExist ( row_key )) {
//                                m.addRow ( row_key );
//                            }
//
//                        }
//                    }
//                }
//            });
//
//            for ( NumericMatrix m : matrix_map.values() ) {
//                m.sort_rows();
//            }
//        }
//        catch ( Exception ex ) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void house_analysis_old ()
//    {
//        String filename_path = "/Users/kevintboyle/elections/DB/election.DB";
//        File db = new File (filename_path);
//
//        SqlLiteManager sql = new SqlLiteManager(db);
//
//        HashMap<String, NumericMatrix> matrix_map = new HashMap<>();
//
//        ArrayList<String> state_and_office_list = new ArrayList<>();
//
//        String sel_sql = "select election_date, state, county, office, party, sum(votes) as votes from election_data where office_type = 'HOUSE' and state = 'Arizona' group by election_date, state, county, office, party order by election_date, state, county, office, party";
//        sql.executeQuery(sel_sql, new jdbcHandler() {
//            @Override
//            public void handler(ResultSet rs) {
//                try {
//                    while (rs.next()) {
//                        String election_date = rs.getString("election_date");
//                        String state = rs.getString("state");
//                        String county = rs.getString("county");
//                        String party = rs.getString("party");
//                        String office = rs.getString("office");
//                        Double votes = rs.getDouble("votes");
//
//                        String key = String.format("%s-%s", state, office.replace(" ", ""));
//                        if( !state_and_office_list.contains(key) ) {
//                            state_and_office_list.add(key);
//                        }
//
//                        NumericMatrix m = matrix_map.get(String.format("%s-%s", election_date,key ));
//                        if ( m == null ) {
//                            matrix_map.put ( String.format("%s-%s", election_date, state), m = new NumericMatrix());
//                        }
//                        String row_key = String.format("%s", party );
//                        String col_key = county;
//
//                        m.cell( row_key, col_key, votes );
//                    }
//                }
//                catch ( Exception ex ) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//        party_closure (sql, matrix_map, "Arizona");
//
//
//        BufferedWriter r_script = null;
//        try {
//            URI rScriptUri = this.getClass().getClassLoader().getResource("script.R").toURI();
//            Path inputScript = Paths.get(rScriptUri);
//
//            File script = new File("/tmp/Election.R");
//            if (script.exists()) {
//                script.delete();
//            }
//
//            Files.copy(inputScript, Paths.get(script.getCanonicalPath()));
//
//            for ( String state_and_office : state_and_office_list ) {
//                NumericMatrix G2016 = matrix_map.get(String.format("%s-%s", "20161103", state_and_office));
//                NumericMatrix G2020 = matrix_map.get(String.format("%s-%s", "20201103", state_and_office));
//                if ( G2016 != null & G2020 != null ) {
//                    NumericMatrixPrint.print("G2016", G2016);
//                    NumericMatrixPrint.print("G2020", G2020);
//
//                    G2016.write_matrix(String.format("%s_House_2016", state_and_office), script);
//                    G2020.write_matrix(String.format("%s_House_2020", state_and_office), script);
//
//
//                    try {
//                        r_script = new BufferedWriter(new FileWriter(script, true));
//
//                        r_script.write(String.format("house_analysis ( \"%s_House.xlsx\", %s_House_2016, %s_House_2020 );\n\n",
//                                state_and_office_list,
//                                state_and_office_list,
//                                state_and_office_list
//                        ));
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    } finally {
//                        r_script.close();
//                    }
//                }
//            }
//            //election_analysis <- function ( filename, before_name, before_m, before_row_name, before_col_names, after_name, after_m, after_row_names, after_col_names ) {
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if ( r_script != null)
//                    r_script.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//
//    }
//
//    public void presidential_analysis_old ()
//    {
//        String filename_path = "/Users/kevintboyle/elections/DB/election.DB";
//        File db = new File (filename_path);
//
//        SqlLiteManager sql = new SqlLiteManager(db);
//
//        HashMap<String, NumericMatrix> matrix_map = new HashMap<>();
//
//        ArrayList<String> states = new ArrayList<>();
//
//        String sel_sql = "select election_date, state, county, office, party, candidate, sum(votes) as votes from election_data where office_type = 'PRESIDENT' and party in ( 'DEMOCRATIC', 'REPUBLICAN' ) and not candidate like '%WILLIE%' group by election_date, state, county, office, party, candidate order by election_date, state, county, office, party, candidate";
//        sql.executeQuery(sel_sql, new jdbcHandler() {
//            @Override
//            public void handler(ResultSet rs) {
//                try {
//                    while (rs.next()) {
//                        String election_date = rs.getString("election_date");
//                        String state = rs.getString("state");
//                        String county = rs.getString("county");
//                        String party = rs.getString("party");
//                        String candidate = rs.getString ("candidate");
//                        Double votes = rs.getDouble("votes");
//
//                        if( !states.contains(state) ) {
//                            states.add(state);
//                        }
//
//                        NumericMatrix m = matrix_map.get(String.format("%s-%s", election_date, state));
//                        if ( m == null ) {
//                            matrix_map.put ( String.format("%s-%s", election_date, state), m = new NumericMatrix());
//                        }
//                        String row_key = String.format("%s-%s-%s", election_date, candidate, party );
//                        String col_key = county;
//
//                        m.cell( row_key, col_key, votes );
//                    }
//                }
//                catch ( Exception ex ) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//        BufferedWriter r_script = null;
//        try {
//            URI rScriptUri = this.getClass().getClassLoader().getResource("script.R").toURI();
//            Path inputScript = Paths.get(rScriptUri);
//
//            File script = new File("/tmp/Election.R");
//            if (script.exists()) {
//                script.delete();
//            }
//
//            Files.copy(inputScript, Paths.get(script.getCanonicalPath()));
//
//            for ( String state : states ) {
//                NumericMatrix G2016 = matrix_map.get(String.format("%s-%s", "20161103", state));
//                NumericMatrixPrint.print("G2016", G2016);
//
//                NumericMatrix G2020 = matrix_map.get(String.format("%s-%s", "20201103", state));
//                NumericMatrixPrint.print("G2020", G2020);
//
//
//
//                G2016.write_matrix(String.format("%s2016", state), script);
//                G2020.write_matrix(String.format("%s2020", state), script);
//
//                try {
//                    r_script = new BufferedWriter(new FileWriter(script, true));
//
//                    r_script.write(String.format("presidential_analysis ( \"%s.xlsx\", \"%s2016\", %s2016, %s2016_row_names, %s2016_col_names, \"%s2020\", %s2020, %s2020_row_names, %s2020_col_names );\n\n",
//                            state,
//                            state,
//                            state,
//                            state,
//                            state,
//                            state,
//                            state,
//                            state,
//                            state
//                    ));
//                }
//                catch ( Exception ex ) {
//                    ex.printStackTrace();
//                }
//                finally {
//                    r_script.close();
//                }
//            }
//            //election_analysis <- function ( filename, before_name, before_m, before_row_name, before_col_names, after_name, after_m, after_row_names, after_col_names ) {
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                r_script.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//
//    }
//
//
//    class HouseElectionFilter implements  PivotFilter {
//
//        String office = null;
//        String year = null;
//        String state = null;
//        String county = null;
//
//        Integer party_idx = -1;
//        Integer office_idx = -1;
//        Integer state_idx = -1;
//        Integer election_date_idx = -1;
//        Integer county_idx = -1;
//        @Override
//        public boolean include(ArrayList<String> dimension_names, ArrayList<String> dimension_values) {
//            if ( election_date_idx < 0 ) {
//                election_date_idx = dimension_names.indexOf("election_date");
//            }
//            if ( party_idx < 0 ) {
//                party_idx = dimension_names.indexOf("party");
//            }
//            if ( office_idx < 0 ) {
//                office_idx = dimension_names.indexOf("office_type");
//            }
//            if ( state_idx < 0 ) {
//                state_idx = dimension_names.indexOf("state");
//            }
//            if ( county_idx < 0 ) {
//                county_idx = dimension_names.indexOf("county");
//            }
//            if ( ( dimension_values.get(election_date_idx) != null &&  dimension_values.get(election_date_idx).toUpperCase().contains(year.toUpperCase()))) {
//                if (dimension_values.get(state_idx) != null && dimension_values.get(state_idx).toUpperCase().contains(state.toUpperCase())) {
//                    if (dimension_values.get(office_idx) != null && dimension_values.get(office_idx).toUpperCase().contains(office.toUpperCase())) {
//                        if ( this.county!= null ) {
//                            String row_county = dimension_values.get(county_idx);
//                            if (row_county != null && (row_county.toUpperCase().contains(county.toUpperCase()))) {
//                                return true;
//                            }
//                            return false;
//                        }
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//            }
//            return false;
//        }
//
//        public HouseElectionFilter ( String office, String year, String state, String county )
//        {
//            this.office = office;
//            this.year = year;
//            this.state = state;
//            this.county = county;
//        }
//    }
//    class RepublicanAndDemocraticElectionFilter implements  PivotFilter {
//
//        String office = null;
//        String year = null;
//        String state = null;
//
//        Integer party_idx = -1;
//        Integer office_idx = -1;
//        Integer state_idx = -1;
//        Integer election_date_idx = -1;
//        @Override
//        public boolean include(ArrayList<String> dimension_names, ArrayList<String> dimension_values) {
//            if ( election_date_idx < 0 ) {
//                election_date_idx = dimension_names.indexOf("election_date");
//            }
//            if ( party_idx < 0 ) {
//                party_idx = dimension_names.indexOf("party");
//            }
//            if ( office_idx < 0 ) {
//                office_idx = dimension_names.indexOf("office_type");
//            }
//            if ( state_idx < 0 ) {
//                state_idx = dimension_names.indexOf("state");
//            }
//            if ( ( dimension_values.get(election_date_idx) != null &&  dimension_values.get(election_date_idx).toUpperCase().contains(year.toUpperCase()))) {
//                if (dimension_values.get(state_idx) != null && dimension_values.get(state_idx).toUpperCase().contains(state.toUpperCase())) {
//                    if (dimension_values.get(office_idx) != null && dimension_values.get(office_idx).toUpperCase().contains(office.toUpperCase())) {
//                        String party = dimension_values.get(party_idx);
//                        if (party != null && (party.equalsIgnoreCase("REPUBLICAN") || party.equalsIgnoreCase("DEMOCRATIC"))) {
//                            return true;
//                        }
//                        return false;
//                    } else {
//                        return false;
//                    }
//                }
//            }
//            return false;
//        }
//
//        public RepublicanAndDemocraticElectionFilter ( String office, String year, String state )
//        {
//            this.office = office;
//            this.year = year;
//            this.state = state;
//        }
//    }
//
//    private void row_closure ( NumericMatrix A, NumericMatrix B )
//    {
//        if ( A != null && B != null ) {
//            String[] A_row_names = A.getRowNames();
//            for ( String name : A_row_names ) {
//                if ( !B.hasRow ( name )) {
//                    B.addEmptyRow ( name );
//                }
//            }
//        }
//    }
//
//    private void closure ( NumericMatrix A, NumericMatrix B )
//    {
//        if ( A != null && B != null ) {
//            row_closure ( A, B);
//            row_closure ( B, A);
//        }
//        A.sort();
//        B.sort();
//    }
//
//    public void house_analysis(Pivot p)
//    {
//        NumericMatrix ArizonaHouse2020= getHouseData(p, "HOUSE", "2020", "Pennsylvania", null);
//        NumericMatrix ArizonaHouse2016= getHouseData(p, "HOUSE", "2016", "Pennsylvania", null);
//
////        NumericMatrix ArizonaHouse2020= getHouseData(p, "HOUSE", "2020", "Arizona", "Maricopa");
////        NumericMatrix ArizonaHouse2016= getHouseData(p, "HOUSE", "2016", "Arizona", "Maricopa");
//
//        closure ( ArizonaHouse2020, ArizonaHouse2016 );
//
//        NumericMatrix ArizonaHouseDiff = diff ( ArizonaHouse2020, ArizonaHouse2016 );
//        String [] row_names = ArizonaHouseDiff.getRowNames();
//        for ( int i=0;i<row_names.length; i++ ) {
//            row_names[i] = row_names[i].replace("20201103", "DIFF 2020-2016");
//        }
//        ArizonaHouseDiff.setRowNames( row_names );
//
////        NumericMatrixPrint.print("ArizonaHouse2020", ArizonaHouse2020);
////        NumericMatrixPrint.print("ArizonaHouse2016", ArizonaHouse2016);
////        NumericMatrixPrint.print("ArizonaHouseDiff", ArizonaHouseDiff);
//
//        NumericMatrix ArizonaHouse2020_t = ArizonaHouse2020.transpose();
//        NumericMatrixPrint.print("ArizonaHouse2020", ArizonaHouse2020);
//        NumericMatrixPrint.print("ArizonaHouse2020_t", ArizonaHouse2020_t);
//
//        NeoWorkbook book = new NeoWorkbook();
//        NeoSheet az2020 =book.createSheet("Arizona2020");
//        NeoSheet.MatrixLocation location = az2020.put(ArizonaHouse2020_t);
//        String[] t_column_names = ArizonaHouse2020_t.getColNames();
//
//
//        ChartAnchorData position = new ChartAnchorData(2, 6, 40, 50);
//        ChartParameters parameters = new LineChartParameters() {
//            @Override
//            public CellRangeAddress get_X_AxisRange() {
//                return new CellRangeAddress(1, 67, 0, 0);
//            }
//
//            @Override
//            public CellRangeAddress[] get_Y_AxisRange() {
//                return new CellRangeAddress[] {new CellRangeAddress(1, 67, 1, 1), new CellRangeAddress(1, 67, 3, 3)};
//            }
//
//            @Override
//            public String[] get_Y_SeriesNames() {
//                return new String[] {t_column_names[0], t_column_names[2]};
//            }
//
//            @Override
//            public String getLeftAxisTitle() {
//                return "Votes";
//            }
//
//            @Override
//            public String getBottomAxisTitle() {
//                return "County";
//            }
//
//            @Override
//            public String getChartTitle() {
//                return "2020 Pennsylvania Voting Data";
//            }
//        };
//        ExcelChart bar = new ExcelChart();
//        bar.render( az2020, position, parameters );
//
//        try {
//            File xl = new File("/tmp/Pennsylvania2020Chart.xlsx");
//            if ( xl.exists() ) {
//                xl.delete();
//            }
//            book.saveAs(xl.getCanonicalPath());
//        }
//        catch ( Exception ex ) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void presidential_analysis(Pivot p)
//    {
//
//        NumericMatrix Arizona2020= getData(p, "PRESIDENT", "2020", "Arizona");
//        NumericMatrix Arizona2016= getData(p, "PRESIDENT", "2016", "Arizona");
//        NumericMatrix ArizonaDiff = diff ( Arizona2020, Arizona2016 );
//
//        NumericMatrix Georgia2020= getData(p, "PRESIDENT", "2020", "Georgia");
//        NumericMatrix Georgia2016= getData(p, "PRESIDENT", "2016", "Georgia");
//        NumericMatrix GeorgiaDiff = diff ( Georgia2020, Georgia2016 );
//
//        NumericMatrix Michigan2020= getData(p, "PRESIDENT", "2020", "Michigan");
//        NumericMatrix Michigan2016= getData(p, "PRESIDENT", "2016", "Michigan");
//        NumericMatrix MichiganDiff = diff ( Michigan2020, Michigan2016 );
//
//        NumericMatrix Pennsylvania2020= getData(p, "PRESIDENT", "2020", "Pennsylvania");
//        NumericMatrix Pennsylvania2016= getData(p, "PRESIDENT", "2016", "Pennsylvania");
//        NumericMatrix PennsylvaniaDiff = diff ( Pennsylvania2020, Pennsylvania2016 );
//
////        double[][] d = new double[][] {{ 1,2,3},{4,5,6},{7,8,9}};
//        double[][] d = PennsylvaniaDiff.getDoubleMatrix();
//
//        SVD svd = RCallerSingularValueDecomposition.svd(d);
//
////        RCallerSingularValueDecomposition.print( "v", svd.v );
////        RCallerSingularValueDecomposition.print( "u", svd.u );
////        RCallerSingularValueDecomposition.print( "diag", svd.diag );
//
//        NumericMatrix Pennsylvania_V = new NumericMatrix( svd.v );
//        Pennsylvania_V.setRowNames(PennsylvaniaDiff.getColNames());
//
//        NumericMatrix Pennsylvania_U = new NumericMatrix( svd.u );
//        NumericMatrix Pennsylvania_D = new NumericMatrix( NumericMatrix.diag(svd.diag) );
//
//        NumericMatrixPrint.print("Pennsylvania_V", Pennsylvania_V);
//        NumericMatrixPrint.print("Pennsylvania_U", Pennsylvania_U);
//        NumericMatrixPrint.print("Pennsylvania_D", Pennsylvania_D);
//
//
//        int i =0;
//    }
//
//    public NumericMatrix diff ( NumericMatrix a, NumericMatrix b ){
//        MatrixArithmetic arith = new MatrixArithmetic();
//        NumericMatrix diff = arith.subtract ( a, b );
//        diff.setColNames( a.getColNames() );
//        diff.setRowNames( a.getRowNames() );
////        NumericMatrixPrint.print(diff);
//        return diff;
//    }
//
//    public NumericMatrix getHouseData (Pivot p, String office, String year, String state, String county )
//    {
//        HouseElectionFilter filter = new HouseElectionFilter(office, year, state, county);
//        NumericMatrix m = p.pivot(new String[]{ "office", "party"}, new String[]{ "county"}, new String[]{"votes"}, filter );
//        return m;
//    }
//
//    public NumericMatrix getData (Pivot p, String office, String year, String state )
//    {
//        RepublicanAndDemocraticElectionFilter filter = new RepublicanAndDemocraticElectionFilter(office, year, state);
//        NumericMatrix m = p.pivot(new String[]{"election_date", "state", "office_type", "party"}, new String[]{"county"}, new String[]{"votes"}, filter );
//        return m;
//    }
//
//    public void run_R ( DoubleVector before, int before_rows, int before_cols, DoubleVector after, int after_rows, int after_cols )
//    {
//
//        try {
//
//
//
//
//
//
//            /*
//
//            library(MASS);
//library("xlsx")
//
//
//
//                before_m = matrix ( before, before_row, before_col, TRUE)
//    after_m  = matrix ( after, after_row, after_col, TRUE )
//
//    D = after_m - before_m
//
//    res = svd(D)
//
//    V = res$v
//    S = diag(res$d)
//    U = res$u
//
//    Scaled = U %*% S
//
//    Recon = Scaled %*% t(V)
//
//    file = "Debug.xlsx"
//
//    write.xlsx(before_m, file, sheetName = "Before", col.names = TRUE, row.names = TRUE, append = FALSE)
//    write.xlsx(after_m, file, sheetName = "After", col.names = TRUE, row.names = TRUE, append = TRUE)
//    write.xlsx(D, file, sheetName = "Diff", col.names = TRUE, row.names = TRUE, append = TRUE)
//    write.xlsx(U, file, sheetName = "U", col.names = TRUE, row.names = TRUE, append = TRUE)
//    write.xlsx(S, file, sheetName = "S", col.names = TRUE, row.names = TRUE, append = TRUE)
//    write.xlsx(Scaled, file, sheetName = "Scaled", col.names = TRUE, row.names = TRUE, append = TRUE)
//    write.xlsx(V, file, sheetName = "V", col.names = TRUE, row.names = TRUE, append = TRUE)
//
//    write.xlsx(Recon, file, sheetName = "Recon", col.names = TRUE, row.names = TRUE, append = TRUE)
//             */
//
//        }
//        catch ( Exception ex ) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static void main( String[] args ) throws Exception
//    {
//        main_db(args);
//    }
//
//    public static void main_db( String[] args ) throws Exception
//    {
//        String filename_path = "/Users/kevintboyle/elections/DB/election.DB";
//        File dbFile = new File (filename_path);
//
//        SqlLiteManager db = new SqlLiteManager(dbFile);
//
//        String sql = "select election_date, state, county, office, office_type, party, sum(votes) as votes from election_data group by election_date, state, county, office, office_type, party";
//        String[] dim = {"election_date", "state", "county", "office", "office_type", "party"};
//        String[] mes = {"votes"};
//        Pivot p = new Pivot(db, sql, dim, mes);
//
//        App app = new App();
////        app.presidential_analysis(p);
//        app.house_analysis(p);
//    }
//
//    public static void main_R( String[] args ) throws Exception
//    {
//        RenjinScriptEngine engine = new RenjinScriptEngine();
//        App app = new App();
//
//        int [] values  = { 5,4,6,7,8};
//        Double m = app.mean(engine, values);
//
//        System.out.println (String.format("%f", m));
//
//        app.matrix(engine);
//
//        app.java_data(engine);
//
//        app.test(engine);
//    }
//}
