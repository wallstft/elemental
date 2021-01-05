package org.neostorm;

/*
   Copyright 2018 Wall Street Fin Tech

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
   
    
    */

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.poi.ss.util.CellRangeAddress;
import org.neostorm.data.*;
import org.neostorm.db.SqlLiteManager;
import org.neostorm.db.jdbcHandler;
import org.neostorm.excel.NeoSheet;
import org.neostorm.excel.NeoWorkbook;
import org.neostorm.excel.charts.chartParameters.ChartParameters;
import org.neostorm.excel.charts.chartParameters.HistogramChartParameters;
import org.neostorm.excel.charts.chartParameters.LineChartParameters;
import org.neostorm.excel.charts.core.ExcelChart;
import org.neostorm.excel.charts.data_structure.ChartAnchorData;
import org.neostorm.pivot_filters.HouseElectionFilter;
import org.neostorm.pivot_filters.RepublicanAndDemocraticElectionFilter;
import org.neostorm.pivot_filters.RepublicanAndDemocraticVoteTypeFilter;
import org.neostorm.rcaller.RCallerSingularValueDecomposition;
import org.neostorm.rcaller.SVD;
import org.renjin.primitives.matrix.Matrix;
import org.renjin.primitives.text.regex.RE;
import org.renjin.repackaged.guava.io.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class ElectionAnalysis2020 {

    SqlLiteManager db = null;
    List<String> states = new ArrayList<>();

    Comparator<double[]> vector_compare_large_to_small = new Comparator<double[]>() {
        @Override
        public int compare(double[] o1, double[] o2) {
            Double t1 = 0.0;
            for ( double x : o1 )
                t1 += Math.abs(x);
            Double t2 = 0.0;
            for ( double x : o2 )
                t2 += Math.abs(x);
            return t2.compareTo(t1);
        }
    };

    public static void main( String[] args ) throws Exception
    {
        ElectionAnalysis2020 a = new ElectionAnalysis2020();
        a.analyze();
    }

    private void open_db()
    {
        String filename_path = "/Users/kevintboyle/elections/DB/election.DB";
        File dbFile = new File (filename_path);
        db = new SqlLiteManager(dbFile);
    }

    public void analyze()
    {
        open_db();
//        load_states();
        states.add("Georgia");
        Pivot pivot = load_pivot();
        NeoWorkbook workbook = create_workbook();
        presidential_analysis(workbook, pivot);
//        house_analysis(workbook, pivot);
        save_workbook( workbook );
    }


    private void load_states()
    {
        String sql ="select distinct state from election_data";
        db.executeQuery(sql, new jdbcHandler() {
            @Override
            public void handler(ResultSet rs) throws Exception {
                while ( rs.next() ) {
                    states.add(rs.getString("state"));
                }
            }
        });
    }

    public NumericMatrix getData (Pivot p, PivotFilter filter )
    {
        NumericMatrix m = p.pivot(new String[]{"election_date", "state", "office_type", "party"}, new String[]{"county"}, new String[]{"votes"}, filter );
        return m;
    }

    public NumericMatrix groupMatrix ( NumericMatrix matrix )
    {
        MatrixArithmetic mat = new MatrixArithmetic();
        HashMap<String, String> group_map = getGroupingMap();
        NumericMatrix groupMatrix = NumericMatrixBuilder.instance().groupTotalByRow(new MatrixArithmetic.GroupingHandler() {
            @Override
            public String getGroupName(String row_name, int row) {
                for ( Map.Entry<String,String> m : group_map.entrySet() ) {
                    String key = m.getKey();
                    String group_name = m.getValue();
                    if ( row_name.contains(key) ) {
                        return row_name.replace(key,group_name);
                    }
                }
                return row_name;
            }
        }, matrix).sort().build();

        return groupMatrix;
    }

    public NumericMatrix diff ( NumericMatrix a, NumericMatrix b ){

        MatrixArithmetic arith = new MatrixArithmetic();
        NumericMatrix diff = arith.subtract ( a, b );
        diff = diff.setNames(
                removeFromNames(a.getRowNames(),"[20201103]"),
                removeFromNames(a.getColNames(),"[20201103]")
        );
        diff = new NumericMatrixBuilder().instance().set(diff).columnVectorSort(vector_compare_large_to_small).build();
        return diff;
    }

    private String[] removeFromNames ( String [] names, String remove_str ) {
        String[] clean_names = new String[names.length];
        for ( int i =0; i<names.length; i++ ) {
            clean_names [i] = names[i].replace(remove_str, "");
        }
        return clean_names;
    }
    public NumericMatrix percentageDiff ( NumericMatrix a, NumericMatrix b ){

        MatrixArithmetic arith = new MatrixArithmetic();
        NumericMatrix diff = arith.subtract ( a, b );
        diff = arith.divide ( diff, b );
        diff = diff.setNames (
                removeFromNames(a.getRowNames(), "[20201103]"),
                removeFromNames(a.getColNames(),"[20201103]")
        );


        diff = new NumericMatrixBuilder().instance().set(diff).columnVectorSort(vector_compare_large_to_small).build();
        return diff;
    }

    public NumericMatrix runningTotalPercentage ( NumericMatrix a, NumericMatrix b ){

        MatrixArithmetic arith = new MatrixArithmetic();
        NumericMatrix diff = arith.subtract ( a, b );
        diff = diff.setNames (
                removeFromNames(a.getRowNames(), "[20201103]"),
                removeFromNames(a.getColNames(),"[20201103]")
        );

        Comparator<double[]> vector_compare_large_to_small = new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                Double t1 = 0.0;
                for ( double x : o1 )
                    t1 += Math.abs(x);
                Double t2 = 0.0;
                for ( double x : o2 )
                    t2 += Math.abs(x);
                return t2.compareTo(t1);
            }
        };
        diff = new NumericMatrixBuilder().instance().set(diff).columnVectorSort(vector_compare_large_to_small).build();

        double total_rows_and_cols = arith.total_rows_and_col(b);

//        diff = arith.divide ( diff, b );
        return diff;
    }



    private void addMultiChart( String state, String year, NeoSheet sheet, NeoSheet.MatrixLocation location )
    {
        int base_row = 6;
        int base_col = 3;
        int width = 40;
        int height = 20;
        int o=0;
        for ( int i =1; i <location.y_range.length; i +=2 ) {
            final Integer idx =i;
            ChartAnchorData position = new ChartAnchorData(6+((o++)*height), 3, width, height);
            ChartParameters parameters = new HistogramChartParameters() {
                @Override
                public CellRangeAddress get_X_AxisRange() {
                    return location.x_range;
                }

                @Override
                public CellRangeAddress[] get_Y_AxisRange() {

                    return new CellRangeAddress[] { location.y_range[idx-1], location.y_range[idx]};
                }

                @Override
                public String[] get_Y_SeriesNames() {
                    return new String[] { location.x_names[idx-1], location.x_names[idx] };
                }

                @Override
                public String getLeftAxisTitle() {
                    return "Votes";
                }

                @Override
                public String getBottomAxisTitle() {
                    return "County";
                }

                @Override
                public String getChartTitle() {
                    return String.format("%s %s Voting Data", year, state);
                }
            };
            ExcelChart bar = new ExcelChart();
            bar.render(sheet, position, parameters);
        }
    }

    private void addChart( String state, String year, NeoSheet sheet, NeoSheet.MatrixLocation location, String LeftAxisTitle )
    {
        ChartParameters parameters = new HistogramChartParameters() {
            @Override
            public CellRangeAddress get_X_AxisRange() {
                return location.x_range;
            }

            @Override
            public CellRangeAddress[] get_Y_AxisRange() {

                return location.y_range;
            }

            @Override
            public String[] get_Y_SeriesNames() {
                return location.x_names;
            }

            @Override
            public String getLeftAxisTitle() {
                return LeftAxisTitle;
            }

            @Override
            public String getBottomAxisTitle() {
                return "County";
            }

            @Override
            public String getChartTitle() {
                return String.format( "%s %s Voting Data", year, state );
            }
        };
        addChart( state, year, sheet, location, LeftAxisTitle, parameters );
    }

    private void addHistorgramChart( String state, String year, NeoSheet sheet, NeoSheet.MatrixLocation location, String LeftAxisTitle )
    {
        ChartParameters parameters = new HistogramChartParameters() {
            @Override
            public CellRangeAddress get_X_AxisRange() {
                return location.x_range;
            }

            @Override
            public CellRangeAddress[] get_Y_AxisRange() {

                return location.y_range;
            }

            @Override
            public String[] get_Y_SeriesNames() {
                return location.x_names;
            }

            @Override
            public String getLeftAxisTitle() {
                return LeftAxisTitle;
            }

            @Override
            public String getBottomAxisTitle() {
                return "County";
            }

            @Override
            public String getChartTitle() {
                return String.format( "%s %s Voting Data", year, state );
            }
        };
        addChart( state, year, sheet, location, LeftAxisTitle, parameters );
    }

    private void addLineChart( String state, String year, NeoSheet sheet, NeoSheet.MatrixLocation location, String LeftAxisTitle )
    {
        ChartParameters parameters = new LineChartParameters() {
            @Override
            public CellRangeAddress get_X_AxisRange() {
                return location.x_range;
            }

            @Override
            public CellRangeAddress[] get_Y_AxisRange() {

                return location.y_range;
            }

            @Override
            public String[] get_Y_SeriesNames() {
                return location.x_names;
            }

            @Override
            public String getLeftAxisTitle() {
                return LeftAxisTitle;
            }

            @Override
            public String getBottomAxisTitle() {
                return "County";
            }

            @Override
            public String getChartTitle() {
                return String.format( "%s %s Voting Data", year, state );
            }
        };
        addChart( state, year, sheet, location, LeftAxisTitle, parameters );
    }

    private void addChart( String state, String year, NeoSheet sheet, NeoSheet.MatrixLocation location, String LeftAxisTitle, ChartParameters parameters )
    {
        ChartAnchorData position = new ChartAnchorData(6, 3, 40, 50);

        ExcelChart bar = new ExcelChart();
        bar.render( sheet, position, parameters );
    }

    private NumericMatrix z_score ( NumericMatrix matrix )
    {
        MatrixArithmetic arith = new MatrixArithmetic();
        double mean = arith.mean ( matrix );
        double stdev = arith.stdev ( matrix );

        NumericMatrix z_score_matrix = arith.z_score ( matrix, mean, stdev );
        z_score_matrix = z_score_matrix.setNames (
                removeFromNames(matrix.getRowNames(),"[20201103]"),
                removeFromNames(matrix.getColNames(),"[20201103]")
        );
//        z_score_matrix.setColNames( removeFromNames(matrix.getColNames(),"[20201103]") );
//        z_score_matrix.setRowNames( removeFromNames(matrix.getRowNames(),"[20201103]") );
        return z_score_matrix;
    }

    private HashMap<String, String> getGroupingMap ()
    {
        HashMap<String, String> grouping_map = new HashMap<>();
        grouping_map.put ("Election Day Votes","IN_PERSON_VOTING");
        grouping_map.put ("Advanced Voting Votes", "IN_PERSON_VOTING");
        grouping_map.put ("Provisional Votes", "IN_PERSON_VOTING");

        grouping_map.put ("Absentee by Mail Votes", "ABSENTEE");

        return grouping_map;
    }

    private double[] calculate_percentage (double[] inPerson2020, double[] inPerson2016 )
    {
        double[] percentVotedInPerson = new double[inPerson2020.length];
        if ( inPerson2016.length == inPerson2020.length ) {
            for ( int i=0; i <inPerson2020.length; i++ ) {
                percentVotedInPerson[i] = inPerson2020[i] / inPerson2016[i];
            }
        }
        return percentVotedInPerson;
    }

    class YoYData {
        double [] yoy_in_person_pcnt_2016 = null;
        double [] yoy_in_person_and_absentee_pcnt_2016 = null;
    }

    private YoYData calculate_percentage (double[] dem_inPerson2020, double[] rep_inPerson2020, double[] dem_inPerson2016, double[] rep_inPerson2016, double[] total_absentee )
    {

        double[] percentVotedInPerson = new double[dem_inPerson2020.length];
        double[] percentVotedInPersonAndAbsentee = new double[dem_inPerson2020.length];
        if ( dem_inPerson2020.length == rep_inPerson2020.length && dem_inPerson2016.length ==  rep_inPerson2016.length && rep_inPerson2020.length == dem_inPerson2016.length ) {
            for ( int i=0; i <dem_inPerson2020.length; i++ ) {
                percentVotedInPerson[i] = (dem_inPerson2020[i]+rep_inPerson2020[i]) / (dem_inPerson2016[i]+rep_inPerson2016[i]);
                if ( total_absentee != null )
                    percentVotedInPersonAndAbsentee[i] = (dem_inPerson2020[i]+rep_inPerson2020[i]+total_absentee[i]) / (dem_inPerson2016[i]+rep_inPerson2016[i]);
            }
        }
        YoYData data = new YoYData();
        data.yoy_in_person_pcnt_2016 = percentVotedInPerson;
        data.yoy_in_person_and_absentee_pcnt_2016 = percentVotedInPersonAndAbsentee;

        return data;
    }

//    public NumericMatrix calculate_group_stats ( NumericMatrix matrix )
//    {
//        /*
//        [20161103][DEMOCRATIC][IN_PERSON_VOTING]
//        [20161103][REPUBLICAN][IN_PERSON_VOTING]
//        [20201103][DEMOCRATIC][ABSENTEE]
//        [20201103][DEMOCRATIC][IN_PERSON_VOTING]
//        [20201103][REPUBLICAN][ABSENTEE]
//        [20201103][REPUBLICAN][IN_PERSON_VOTING]
//        */
//
//        double[] DEMOCRATIC_inPerson2016 = matrix.getRow ( "[20161103][DEMOCRATIC][IN_PERSON_VOTING]" );
//        double[] DEMOCRATIC_inPerson2020 = matrix.getRow ( "[20201103][DEMOCRATIC][IN_PERSON_VOTING]" );
////        double[] DEMOCRATIC_percentVotedInPerson = calculate_percentage ( DEMOCRATIC_inPerson2020, DEMOCRATIC_inPerson2016 );
//
//        double[] REPUBLICAN_inPerson2016 = matrix.getRow ( "[20161103][REPUBLICAN][IN_PERSON_VOTING]" );
//        double[] REPUBLICAN_inPerson2020 = matrix.getRow ( "[20201103][REPUBLICAN][IN_PERSON_VOTING]" );
////        double[] REPUBLICAN_percentVotedInPerson = calculate_percentage ( REPUBLICAN_inPerson2020, REPUBLICAN_inPerson2016 );
//
//
//
//        double[] DEMOCRATIC_ABSENTEE_2020 = matrix.getRow ( "[20201103][DEMOCRATIC][ABSENTEE]" );
//        double[] REPUBLICAN_ABSENTEE_2020 = matrix.getRow ( "[20201103][REPUBLICAN][ABSENTEE]" );
//        double[] total_absentee =null;
//        if ( DEMOCRATIC_ABSENTEE_2020 != null && REPUBLICAN_ABSENTEE_2020 != null ) {
//            total_absentee = new double[matrix.getCols()];
//            for (int i = 0; i < matrix.getCols(); i++) {
//                total_absentee[i] = DEMOCRATIC_ABSENTEE_2020[i] + REPUBLICAN_ABSENTEE_2020[i];
//            }
//        }
//
//
//        String[] row_names = { "[2020_div_2016][TOTAL][IN_PERSON_VOTING]", "[2020_div_2016][TOTAL][IN_PERSON_AND_ABSENTEE]" };
//        NumericMatrix t = NumericMatrixBuilder.instance()
//                .setColNames(matrix.getColNames())
//                .addDataRow( "[2020_div_2016][TOTAL][IN_PERSON_VOTING]", data.yoy_in_person_pcnt_2016 )
//                .addDataRow(  "[2020_div_2016][TOTAL][IN_PERSON_AND_ABSENTEE]", data.yoy_in_person_and_absentee_pcnt_2016 ).sort().build();
//
//
//        return t;
//    }

    private NumericMatrix total_rows ( NumericMatrix matrix )
    {
        double[][] data = new double[1][matrix.getCols()];
        matrix.walker(new NumericMatrix.IteratorAction() {
            @Override
            public void walk(String row_name, int row, String col_name, int col, double v) {
                try {
                    data[0][col] += v;
                }
                catch ( Exception ex ) {
                    ex.printStackTrace();
                }
            }
        });
        String[] rnames = {"TOTAL_2016"};
        NumericMatrix t = new NumericMatrix( rnames, matrix.getColNames(), data );
        return t;
    }

    private double[] scale_vector ( double [] a, double val )
    {
        double [] r= new double [a.length];
        for ( int i=0; i<a.length; i++ ) {
            r[i] = a[i] / val;
        }
        return r;
    }

    private double sum_vector_values ( double [] a )
    {
        Double total = 0.0;
        if ( a!= null ) {
            for ( double x : a )
                total+=x;
        }
        return total;
    }

    private double[] add_vectors( double[]a, double[] b ) {
        double []t =null;
        if ( a.length == b.length ) {
            t= new double [a.length];
            for ( int i=0; i<a.length; i++ ) {
                t[i] = a[i]+b[i];
            }
        }
        return t;
    }

    private double[] div_vectors( double[]a, double[] b ) {
        double []t =null;
        if ( a.length == b.length ) {
            t= new double [a.length];
            for ( int i=0; i<a.length; i++ ) {
                t[i] = a[i]/b[i];
            }
        }
        return t;
    }

    private NumericMatrix presidential_absentee(NeoWorkbook workbook, String state, NumericMatrix vote_type2020, NumericMatrix total2016 )
    {
        NumericMatrix absentee_2016_with_total = null;
        NumericMatrix absentee_2020_only = null;
        double[] DEMOCRATIC_ABSENTEE_2020 = vote_type2020.getRow ( "[20201103][DEMOCRATIC][Absentee by Mail Votes]" );
        double[] REPUBLICAN_ABSENTEE_2020 = vote_type2020.getRow ( "[20201103][REPUBLICAN][Absentee by Mail Votes]" );
        if ( DEMOCRATIC_ABSENTEE_2020 != null && REPUBLICAN_ABSENTEE_2020 != null ) {
//                absentee_2020_with_total = NumericMatrixBuilder.instance().setColNames(vote_type2020.getColNames())
//                    .addDataRow("[2020][DEMOCRATIC][ABSENTEE]", DEMOCRATIC_ABSENTEE_2020)
//                    .addDataRow("[2020][REPUBLICAN][ABSENTEE]", REPUBLICAN_ABSENTEE_2020).columnVectorSort(vector_compare_large_to_small).build();

            absentee_2020_only  = NumericMatrixBuilder.instance().setColNames(vote_type2020.getColNames())
                    .addDataRow("[2020][DEMOCRATIC][ABSENTEE]", DEMOCRATIC_ABSENTEE_2020)
                    .addDataRow("[2020][REPUBLICAN][ABSENTEE]", REPUBLICAN_ABSENTEE_2020).columnVectorSort(vector_compare_large_to_small).build();

            absentee_2016_with_total = NumericMatrixBuilder.instance().setColNames(vote_type2020.getColNames())
                    .addDataRow( "[2016][REP_AND_DEM][TOTAL_VOTE]", total2016.getRow(0)).columnVectorSort(vector_compare_large_to_small).build();

        }

        double [][] val = absentee_2020_only.getDoubleMatrix();
        String [] patterns = {"Pattern-1", "Pattern-2"};

        SVD svd = RCallerSingularValueDecomposition.svd( val );
        NumericMatrix absentee_U = new NumericMatrixBuilder().instance()
                .setRowNames(absentee_2020_only.getRowNames())
                .setColNames(patterns)
                .set(svd.u).build();
        NumericMatrix absentee_Vprime = new NumericMatrixBuilder().instance()
                .setRowNames(absentee_2020_only.getColNames() )
                .setColNames(patterns)
                .set(svd.v).build();
        NumericMatrix absentee_D = new NumericMatrix(NumericMatrixBuilder.diag(svd.diag));

        RealMatrix A = new Array2DRowRealMatrix(val);
        SingularValueDecomposition svdA = new SingularValueDecomposition(A);

        RealMatrix U = svdA.getU();
        RealMatrix V = svdA.getV();
        double[] D = svdA.getSingularValues();


        NumericMatrix absentee_V = absentee_Vprime.transpose();

        absentee_V = NumericMatrixBuilder.instance().set(absentee_V).sortCols().build();

        MatrixArithmetic ma = new MatrixArithmetic();
        NumericMatrix recn = ma.multiply( ma.multiply(absentee_U, absentee_D), absentee_V );

        NeoSheet sheetAbsenteeWith2016 = workbook.createSheet( String.format("%sAbsenteeWith2016", state));
        NeoSheet sheetAbsenteeOnly = workbook.createSheet( String.format("%sAbsenteeOnly", state));

        NeoSheet sheetAbsenteeOnly_U = workbook.createSheet( String.format("%sAbsenteeOnly_U", state));
        NeoSheet sheetAbsenteeOnly_V = workbook.createSheet( String.format("%sAbsenteeOnly_V", state));
        NeoSheet sheetAbsenteeOnly_Vprime = workbook.createSheet( String.format("%sAbsenteeOnly_Vprime", state));
        NeoSheet sheetAbsenteeOnly_D = workbook.createSheet( String.format("%sAbsenteeOnly_D", state));
        NeoSheet sheetAbsenteeOnlyRecon = workbook.createSheet( String.format("%sAbsenteeOnly_Recon", state));

        NeoSheet.MatrixLocation locationAbsentee = sheetAbsenteeWith2016.put( absentee_2016_with_total );
        NeoSheet.MatrixLocation locationAbsenteeOnly = sheetAbsenteeOnly.put( absentee_2020_only );
        NeoSheet.MatrixLocation locationAbsenteeRecon = sheetAbsenteeOnlyRecon.put( recn );

        NeoSheet.MatrixLocation locationAbsenteeOnly_U = sheetAbsenteeOnly_U.put( absentee_U );
        NeoSheet.MatrixLocation locationAbsenteeOnly_V = sheetAbsenteeOnly_V.put( absentee_V );
        NeoSheet.MatrixLocation locationAbsenteeOnly_Vprime = sheetAbsenteeOnly_Vprime.put( absentee_Vprime );
        NeoSheet.MatrixLocation locationAbsenteeOnly_D = sheetAbsenteeOnly_D.put( absentee_D );


        addChart ( state, "Absentee", sheetAbsenteeWith2016, locationAbsentee.truncate(50), "Votes" );
        addChart ( state, "AbsenteeOnly", sheetAbsenteeOnly, locationAbsenteeOnly.truncate(50), "Votes" );

        addChart ( state, "AbsenteeOnly_V", sheetAbsenteeOnly_V, locationAbsenteeOnly_V, "Vector Weights" );


        return absentee_2020_only;
    }

    public NumericMatrix getEarlyVoting ( NumericMatrix groupedVotes )
    {
        /*
        7 = "[20201103][REPUBLICAN][Advanced Voting Votes]"
8 = "[20201103][REPUBLICAN][Election Day Votes]"
         */
        NumericMatrix early_voting_2020_matrix =  NumericMatrixBuilder.instance()
                .setColNames(groupedVotes.getColNames())
                .addDataRow("[2020][DEMOCRATIC][Advanced Voting Votes]", groupedVotes.getRow("[20201103][DEMOCRATIC][Advanced Voting Votes]") )
                .addDataRow("[2020][REPUBLICAN][Advanced Voting Votes]", groupedVotes.getRow("[20201103][REPUBLICAN][Advanced Voting Votes]") )
                .columnVectorSort(vector_compare_large_to_small).build();

        return early_voting_2020_matrix;
    }

    public NumericMatrix getElectionDayVoting ( NumericMatrix groupedVotes )
    {
                /*
        7 = "[20201103][REPUBLICAN][Advanced Voting Votes]"
8 = "[20201103][REPUBLICAN][Election Day Votes]"
         */
        NumericMatrix early_voting_2020_matrix =  NumericMatrixBuilder.instance()
                .setColNames(groupedVotes.getColNames())
                .addDataRow("[2020][DEMOCRATIC][Election Day Votes]", groupedVotes.getRow("[20201103][DEMOCRATIC][Election Day Votes]") )
                .addDataRow("[2020][REPUBLICAN][Election Day Votes]", groupedVotes.getRow("[20201103][REPUBLICAN][Election Day Votes]") )
                .columnVectorSort(vector_compare_large_to_small).build();

        return early_voting_2020_matrix;
    }

    public NumericMatrix presidential_inperson (NeoWorkbook workbook, String state, NumericMatrix groupedVotes)
    {

        double[] dem2016 = null;
        double[] rep2016 = null;
        double[] dem2020 = null;
        double[] rep2020 = null;
        NumericMatrix inperson_2016_matrix =  NumericMatrixBuilder.instance().setColNames(groupedVotes.getColNames())
                .addDataRow("[2016][DEMOCRATIC][IN_PERSON_VOTING]", dem2016=groupedVotes.getRow("[20161103][DEMOCRATIC][IN_PERSON_VOTING]") )
                .addDataRow("[2016][REPUBLICAN][IN_PERSON_VOTING]", rep2016=groupedVotes.getRow("[20161103][REPUBLICAN][IN_PERSON_VOTING]") )
                .columnVectorSort(vector_compare_large_to_small)
                .build();
        NumericMatrix inperson_2020_matrix =  NumericMatrixBuilder.instance()
                .setColNames(groupedVotes.getColNames())
                .addDataRow("[2020][DEMOCRATIC][IN_PERSON_VOTING]", dem2020=groupedVotes.getRow("[20201103][DEMOCRATIC][IN_PERSON_VOTING]") )
                .addDataRow("[2020][REPUBLICAN][IN_PERSON_VOTING]", rep2020=groupedVotes.getRow("[20201103][REPUBLICAN][IN_PERSON_VOTING]") )
                .columnVectorSort(vector_compare_large_to_small).build();

        NumericMatrix inperson_2020_scaled_by_2016_total_votes_matrix = null;

        inperson_2020_scaled_by_2016_total_votes_matrix = NumericMatrixBuilder.instance()
                .setColNames(groupedVotes.getColNames())
                .addDataRow("[2016][DEMOCRATIC][IN_PERSON_VOTING]", dem2016 )
                .addDataRow("[2016][REPUBLICAN][IN_PERSON_VOTING]", rep2016 )
                .addDataRow("[2020][DEMOCRATIC][IN_PERSON_VOTING]", dem2020 )
                .addDataRow("[2020][REPUBLICAN][IN_PERSON_VOTING]", rep2020 )
                .addDataRow("[2020/2016][DEMOCRATIC][IN_PERSON_VOTING]", div_vectors(dem2020,dem2016) )
                .addDataRow("[2020/2016][REPUBLICAN][IN_PERSON_VOTING]", div_vectors(rep2020,rep2016) )
                .addDataRow("[2020/2016][TOTAL][IN_PERSON_VOTING]", div_vectors(add_vectors( dem2020, rep2020),add_vectors( dem2016, rep2016) ))
                .columnVectorSort(new Comparator<double[]>() {
                    @Override
                    public int compare(double[] o1, double[] o2) {
                        Double d1 = o1[6];
                        Double d2 = o2[6];
                        return d2.compareTo(d1);
                    }
                }).build();

        NumericMatrix diff_2020_2016 = diff( inperson_2020_matrix, inperson_2016_matrix );


        NeoSheet sheetInPersone2016 = workbook.createSheet( String.format("%sInPerson2016", state));
        NeoSheet sheetInPersone2020 = workbook.createSheet( String.format("%sInPerson2020", state));
        NeoSheet sheetInPersone2020_minus_2016 = workbook.createSheet( String.format("%sInPerson2020_minus_2016", state));
        NeoSheet sheetInPerson2020_div_2016 = workbook.createSheet( String.format("%sInPerson2020_div_2016", state));

        NeoSheet.MatrixLocation locationInPerson2016 = sheetInPersone2016.put( inperson_2016_matrix );
        NeoSheet.MatrixLocation locationInPerson2020 = sheetInPersone2020.put( inperson_2020_matrix );
        NeoSheet.MatrixLocation locationInPerson2020_scaled_2016 = sheetInPerson2020_div_2016.put( inperson_2020_scaled_by_2016_total_votes_matrix );

        NeoSheet.MatrixLocation locationInPerson2020_minus_2016 = sheetInPersone2020_minus_2016.put( diff_2020_2016 );

        SVD svd = null;
        double [][] val = null;
        String [] patterns = {"Pattern-1", "Pattern-2"};
        {
            val = inperson_2020_matrix.getDoubleMatrix();

            svd = RCallerSingularValueDecomposition.svd( val );
            NumericMatrix in_person2020_U = new NumericMatrixBuilder().instance()
                    .setRowNames(inperson_2020_matrix.getRowNames())
                    .setColNames(patterns)
                    .set(svd.u).build();
            NumericMatrix in_person2020_Vprime = new NumericMatrixBuilder().instance()
                    .setRowNames(inperson_2020_matrix.getColNames() )
                    .setColNames(patterns)
                    .set(svd.v).build();
            NumericMatrix in_person2020_D = new NumericMatrix(NumericMatrixBuilder.diag(svd.diag));


            NumericMatrix in_person2020_V = in_person2020_Vprime.transpose();

            in_person2020_V = NumericMatrixBuilder.instance().set(in_person2020_V).sortCols().build();



            NeoSheet sheetInPerson2020_U = workbook.createSheet(String.format("%sInPerson2020_U", state));
            NeoSheet sheetInPerson2020_V = workbook.createSheet(String.format("%sInPerson2020_V", state));
            NeoSheet sheetInPerson2020_D = workbook.createSheet(String.format("%sInPerson2020_D", state));

            NeoSheet.MatrixLocation locationInPerson2020_U = sheetInPerson2020_U.put(in_person2020_U);
            NeoSheet.MatrixLocation locationInPerson2020_V = sheetInPerson2020_V.put(in_person2020_V);
            NeoSheet.MatrixLocation locationInPerson2020_D = sheetInPerson2020_D.put(in_person2020_D);

            addChart ( state, "2020", sheetInPerson2020_V, locationInPerson2020_V, "Vector Weights" );
        }

        {
            val = inperson_2016_matrix.getDoubleMatrix();


            svd = RCallerSingularValueDecomposition.svd( val );
            NumericMatrix in_person2016_U = new NumericMatrixBuilder().instance()
                    .setRowNames(inperson_2016_matrix.getRowNames())
                    .setColNames(patterns)
                    .set(svd.u).build();
            NumericMatrix in_person2016_Vprime = new NumericMatrixBuilder().instance()
                    .setRowNames(inperson_2016_matrix.getColNames() )
                    .setColNames(patterns)
                    .set(svd.v).build();
            NumericMatrix in_person2016_D = new NumericMatrix(NumericMatrixBuilder.diag(svd.diag));

            NumericMatrix in_person2016_V = in_person2016_Vprime.transpose();

            in_person2016_V = NumericMatrixBuilder.instance().set(in_person2016_V).sortCols().build();



            NeoSheet sheetInPerson2016_U = workbook.createSheet(String.format("%sInPerson2016_U", state));
            NeoSheet sheetInPerson2016_V = workbook.createSheet(String.format("%sInPerson2016_V", state));
            NeoSheet sheetInPerson2016_D = workbook.createSheet(String.format("%sInPerson2016_D", state));

            NeoSheet.MatrixLocation locationInPerson2016_U = sheetInPerson2016_U.put(in_person2016_U);
            NeoSheet.MatrixLocation locationInPerson2016_V = sheetInPerson2016_V.put(in_person2016_V);
            NeoSheet.MatrixLocation locationInPerson2016_D = sheetInPerson2016_D.put(in_person2016_D);


            addChart ( state, "2016", sheetInPerson2016_V, locationInPerson2016_V, "Vector Weights" );
        }

        return inperson_2020_matrix;
    }

    public void presidential_yoy(NeoWorkbook workbook, String state,NumericMatrix state2020, NumericMatrix state2016)
    {
        state2020 = NumericMatrixBuilder.instance().set(state2020).columnVectorSort(vector_compare_large_to_small).build();
        state2016 = NumericMatrixBuilder.instance().set(state2016).columnVectorSort(vector_compare_large_to_small).build();

        NumericMatrix diff = diff ( state2020, state2016 );
        NumericMatrix percentDiff = percentageDiff ( state2020, state2016 );
        NumericMatrix z_score_matrix = z_score ( percentDiff );

        NeoSheet sheetDiff = workbook.createSheet( String.format("%sDiff", state));
        NeoSheet sheetPercentDiff = workbook.createSheet( String.format("%sPercentDiff", state));
        NeoSheet sheetZscore = workbook.createSheet( String.format("%sZScore", state));

        NeoSheet.MatrixLocation locationDiff = sheetDiff.put( diff );
        NeoSheet.MatrixLocation locationPercentDiff = sheetPercentDiff.put(percentDiff );
        NeoSheet.MatrixLocation locationZscore = sheetZscore.put( z_score_matrix );

        NeoSheet sheet2020 = workbook.createSheet( String.format("%s2020", state));
        NeoSheet sheet2016 = workbook.createSheet( String.format("%s2016", state));

        NeoSheet.MatrixLocation location2020 = sheet2020.put( state2020 );
        NeoSheet.MatrixLocation location2016 = sheet2016.put( state2016 );


        addChart ( state, "2020 ( first 50 countys by size)", sheet2020, location2020.truncate(50), "Votes" );
        addChart ( state, "2016 ( first 50 countys by size)", sheet2016, location2016.truncate(50), "Votes" );
        addChart ( state, " 2020 - 2016 Diff", sheetDiff, locationDiff, "Votes" );
        addChart ( state, " (2020 - 2016)/2016 Percentage Diff", sheetPercentDiff, locationPercentDiff, "Votes" );
        addChart ( state, "x=(2020 - 2016)/2016 Percentage Diff, ZScore z=(x-u)/s", sheetZscore, locationZscore, "Standard Deviations N(0,1)" );
    }

    public void presidential_charts (NumericMatrix absentee_2020_only, NumericMatrix inperson_2020_only, NumericMatrix state2016, NumericMatrix election_day_voting_2020, NumericMatrix advanced_voting_2020 )
    {
        BufferedWriter bw = null;
        try {
            File f = new File("/tmp/data.R");
            if ( f.exists() )
                f.delete();

            state2016 = NumericMatrixBuilder.instance().set(state2016).columnVectorSort(vector_compare_large_to_small).build();

            URL resource = this.getClass().getClassLoader().getResource("template.R");

            String filename = resource.getFile();
            Files.copy( new File (filename), f );

            absentee_2020_only.write_matrix("absentee_2020", f);
            inperson_2020_only.write_matrix("inperson_2020_only", f);
            state2016.write_matrix("state2016", f);
            election_day_voting_2020.write_matrix("election_day_voting_2020", f);
            advanced_voting_2020.write_matrix("advanced_voting_2020", f);

            bw = new BufferedWriter( new FileWriter(f, true));
            bw.write(String.format( "\n\npresidential_analysis (  inperson_2020_only, absentee_2020, state2016, election_day_voting_2020, advanced_voting_2020 )\n\n"));

            Runtime.getRuntime().exec(String.format ("chmod a+x %s", f.getCanonicalPath()));
            Thread.sleep(2000);
            Runtime.getRuntime().exec(String.format ("cd /tmp; %s", f.getCanonicalPath(), f.getCanonicalPath() ));

        }
        catch ( Exception ex ) {
            ex.printStackTrace();

        }
        finally {
            try {
                if ( bw != null )
                    bw.close();
            }
            catch ( Exception x ) {
                x.printStackTrace();
            }
        }
    }

    public void presidential_summary ( NeoWorkbook workbook, String state, NumericMatrix absentee_2020_only, NumericMatrix inperson_2020_only, NumericMatrix state2016 )
    {
        MatrixArithmetic arith = new MatrixArithmetic();

        double total_absentee_2020 = arith.total_rows_and_col(absentee_2020_only);
        double total_inperson_2020 = arith.total_rows_and_col(inperson_2020_only);
        double total_2016 = arith.total_rows_and_col(state2016);

        NeoSheet summary_stats = workbook.createSheet( String.format("%sSummaryStatistics", state));


        int row = 0;
        summary_stats.put(row++, 0, "total_absentee_2020", total_absentee_2020);
        summary_stats.put(row++, 0, "total_inperson_2020", total_inperson_2020);
        summary_stats.put(row++, 0, "total_2016", total_2016);
        summary_stats.put(row++, 0, "total_inperson_2020/total_2016", total_inperson_2020/total_2016);
        summary_stats.put(row++, 0, "total_absentee_2020/total_2016", total_absentee_2020/total_2016);
        summary_stats.put(row++, 0, "In Person Gap from 2016 as percentage", 1.0-(total_inperson_2020/total_2016));
        row++;
        summary_stats.put(row++, 0, "Republican & Democratic 2020 Total", total_absentee_2020+total_inperson_2020 );
        summary_stats.put(row++, 0, "Total In Person / 2020 Total", total_inperson_2020 / (total_absentee_2020+total_inperson_2020) );
        summary_stats.put(row++, 0, "Total Absentee / 2020 Total", total_absentee_2020 / (total_absentee_2020+total_inperson_2020) );
    }

    public void presidential_vote_type(NeoWorkbook workbook, String state, NumericMatrix vote_type2020,  NumericMatrix groupedVotes )
    {



        NeoSheet sheetVoteType = workbook.createSheet( String.format("%sVoteType", state));
        NeoSheet sheetGrouping = workbook.createSheet( String.format("%sGrouping", state));


        NeoSheet.MatrixLocation locationVoteType = sheetVoteType.put( vote_type2020 );
        NeoSheet.MatrixLocation locationGrouping = sheetGrouping.put( groupedVotes );


        addChart ( state, "Vote By Type", sheetVoteType, locationVoteType, "Votes" );
        addChart ( state, "Vote By Grouping", sheetGrouping, locationGrouping, "Votes" );
    }

    private  void  presidential_analysis ( NeoWorkbook workbook, Pivot pivot_data )
    {
        String office = "PRESIDENT";
        for ( String state : states ) {
            RepublicanAndDemocraticVoteTypeFilter voteTypeFilter = new RepublicanAndDemocraticVoteTypeFilter ( office, state);
            NumericMatrix vote_type2020 = pivot_data.pivot(new String[]{"election_date", "party", "vote_type"}, new String[]{"county"}, new String[]{"votes"}, voteTypeFilter);

            RepublicanAndDemocraticElectionFilter filter2020 = new RepublicanAndDemocraticElectionFilter(office, "2020", state);
            NumericMatrix state2020 = pivot_data.pivot(new String[]{"election_date", "state", "office_type", "party"}, new String[]{"county"}, new String[]{"votes"}, filter2020);

            RepublicanAndDemocraticElectionFilter filter2016 = new RepublicanAndDemocraticElectionFilter(office, "2016", state);
            NumericMatrix state2016 = pivot_data.pivot(new String[]{"election_date", "state", "office_type", "party"}, new String[]{"county"}, new String[]{"votes"}, filter2016);

            NumericMatrix groupedVotes = groupMatrix ( vote_type2020 );
            NumericMatrix total2016 = total_rows ( state2016 );


            presidential_yoy( workbook, state, state2020, state2016 );

            NumericMatrix absentee_2020_only = presidential_absentee( workbook, state, vote_type2020, total2016 );

            NumericMatrix inperson_2020_only = presidential_inperson( workbook, state, groupedVotes );

            presidential_vote_type ( workbook, state, vote_type2020, groupedVotes );

            presidential_summary( workbook, state, absentee_2020_only, inperson_2020_only, state2016 );

            NumericMatrix electionDayVoting_2020 = getElectionDayVoting( vote_type2020 );

            NumericMatrix advangedVoting_2020 = getEarlyVoting( vote_type2020 );

            presidential_charts (absentee_2020_only, inperson_2020_only, state2016, electionDayVoting_2020, advangedVoting_2020  );

        }
    }

    private  void house_analysis (NeoWorkbook workbook, Pivot pivot_data)
    {
        String office = "HOUSE";
        for ( String state : states ) {
            String county = null;
            HouseElectionFilter filter2020 = new HouseElectionFilter(office, "2020", state, county);
            NumericMatrix state2020 = pivot_data.pivot(new String[]{ "office", "party" }, new String[]{"county"}, new String[]{"votes"}, filter2020);

            HouseElectionFilter filter2016 = new HouseElectionFilter(office, "2016", state, county);
            NumericMatrix state2016 = pivot_data.pivot(new String[]{ "office", "party" }, new String[]{"county"}, new String[]{"votes"}, filter2016);

//            closure ( state2020, state2016 );
            NumericMatrix stateDiff = diff ( state2020, state2016 );

            NeoSheet sheet2020 = workbook.createSheet( String.format("%sHouse2020", state));
            NeoSheet sheet2016 = workbook.createSheet( String.format("%sHouse2016", state));
            NeoSheet sheetDiff = workbook.createSheet( String.format("%sHouseDiff", state));

            NeoSheet.MatrixLocation location2020 = sheet2020.put( state2020 );
            NeoSheet.MatrixLocation location2016 = sheet2016.put( state2016 );
            NeoSheet.MatrixLocation locationDiff = sheetDiff.put( stateDiff );

            addMultiChart ( state, "2020 House", sheet2020, location2020 );
            addMultiChart ( state, "2016 House", sheet2016, location2016 );
            addMultiChart ( state, "2020 - 2016 House Diff", sheetDiff, locationDiff );
        }
    }

    private NeoWorkbook create_workbook()
    {
        return new NeoWorkbook();
    }

    private void save_workbook ( NeoWorkbook workbook )
    {
        try {
            workbook.saveAs("/tmp/2020Election.xlsx");
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private Pivot load_pivot()
    {

        String sql = "select election_date, state, county, office, office_type, party, ifnull(vote_type, 'Election Day Votes') as vote_type, sum(votes) as votes from election_data  group by election_date, state, county, office, office_type, party, vote_type";
        String[] dim = {"election_date", "state", "county", "office", "office_type", "party", "vote_type"};
        String[] mes = {"votes"};
        Pivot p = new Pivot(db, sql, dim, mes);

        return p;
    }
}
