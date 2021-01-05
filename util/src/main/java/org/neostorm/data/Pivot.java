package org.neostorm.data;

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

import org.neostorm.data_structures.Pair;
import org.neostorm.db.SqlLiteManager;
import org.neostorm.db.jdbcHandler;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Pivot {

    ArrayList<String> dimensions = new ArrayList<>();
    ArrayList<String> measures = new ArrayList<>();
    ArrayList<Pair<ArrayList<String>, ArrayList<Double>>> data = new ArrayList<>();

    public Pivot (SqlLiteManager db, String sql, String [] dimensions, String measures[] )
    {
        for ( String s: dimensions ) {
            if ( !this.dimensions.contains(s)) {
                this.dimensions.add(s);
            }
        }
        for ( String s: measures ) {
            if ( !this.measures.contains(s) ) {
                this.measures.add(s);
            }
        }
        final Pivot This = this;
        db.executeQuery(sql, new jdbcHandler() {
            @Override
            public void handler(ResultSet rs) throws Exception {
                while ( rs.next() ) {
                    ArrayList<String> dim = new ArrayList<>();
                    ArrayList<Double> mes = new ArrayList<>();
                    for ( String d : This.dimensions ) {
                        dim.add( rs.getString( d ));
                    }
                    for ( String m : This.measures ) {
                        mes.add ( rs.getDouble( m )) ;
                    }

                    data.add(new Pair<ArrayList<String>, ArrayList<Double>> ( dim, mes ));
                }
            }
        });
    }

    public NumericMatrix pivot (String [] row_dimensions, String [] col_dimensions, String [] measures )
    {
        return pivot( row_dimensions, col_dimensions, measures, null);
    }

    private String getDimensionKey (ArrayList<Integer> dim_idx, ArrayList<String> dimensions )
    {
        StringBuilder dim_sb = new StringBuilder();
        for ( Integer idx : dim_idx ) {
            dim_sb.append(String.format("[%s]", dimensions.get(idx)));
        }
        String key = dim_sb.toString();
        return key;
    }

    private HashMap<Pair<String,String>, ArrayList<Double>> aggregate ( ArrayList<Integer> row_dim_idx, ArrayList<Integer> col_dim_idx, ArrayList<Integer> mes_idx, PivotFilter filterHandler )
    {
        HashMap<Pair<String,String>, ArrayList<Double>> map = new HashMap<>();
        for ( Pair<ArrayList<String>, ArrayList<Double>> n : data ) {
            ArrayList<String> row_dimensions = n.getKey();
            ArrayList<Double> row_measures = n.getValue();
            if ( row_dimensions == null || row_measures == null ) {
                continue;
            }
            if ( this.dimensions.size() != row_dimensions.size() ) {
                continue;
            }
            if ( filterHandler != null && !filterHandler.include( this.dimensions, row_dimensions)) {
                continue;
            }
            //Create key for new pivot rows
            String row_key = getDimensionKey ( row_dim_idx, row_dimensions );
            String col_key = getDimensionKey ( col_dim_idx, row_dimensions );

//            ArrayList<Double> mes = new ArrayList<>();
//            for ( Integer idx : mes_idx ) {
//                mes.add( row_measures.get(idx) );
//            }

            Pair hash_key = new Pair<String,String>(row_key, col_key);
            ArrayList<Double> l = map.get(hash_key);
            if ( l == null ) {
                map.put ( hash_key, l= new ArrayList<Double>() );
                for ( int i=0; i<mes_idx.size(); i++ ) {
                    l.add( 0.0 );
                }
            }
            for ( int i=0; i<mes_idx.size(); i++ ) {
                Double v = l.get(i );
                Integer midx = mes_idx.get(i);
                Double x = row_measures.get(midx);
                l.set (i, v+x );
            }
        }
        return map;
    }

    private NumericMatrix build_matrix(HashMap<Pair<String,String>, ArrayList<Double>> map)
    {
        ArrayList<String> row_list = new ArrayList();
        ArrayList<String> col_list = new ArrayList();
        for ( Map.Entry<Pair<String,String>,ArrayList<Double>> e : map.entrySet() ) {
            Pair<String,String> key = e.getKey();

            for ( int midx = 0; midx < this.measures.size(); midx ++ ) {
                String row_key = key.getKey();
                String col_key = key.getValue();
                if ( this.measures.size()>1 ) {
                    col_key = String.format("[%s][%s]", col_key, this.measures.get(midx));
                }
                if ( !row_list.contains(row_key)) {
                    row_list.add(row_key);
                }
                if ( !col_list.contains(col_key)) {
                    col_list.add(col_key);
                }
            }
        }
        row_list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        col_list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        double [][] mdata = new double [row_list.size()][col_list.size()];
        for ( Map.Entry<Pair<String,String>,ArrayList<Double>> e : map.entrySet() ) {
            Pair<String,String> key = e.getKey();
            ArrayList<Double> val = e.getValue();

            for ( int midx = 0; midx < this.measures.size(); midx ++ ) {
                Double d = val.get(midx);
                String row_key = key.getKey();
                String col_key = key.getValue();
                if ( this.measures.size()>1 ) {
                    col_key = String.format("[%s][%s]", col_key, this.measures.get(midx));
                }
//                m.cell ( row_key, col_key, d );
                int ridx = row_list.indexOf(row_key);
                int cidx = col_list.indexOf(col_key);
                mdata[ridx][cidx]=d;
            }

        }
        NumericMatrix m = new NumericMatrixBuilder().instance().setRowNames (row_list).setColNames(col_list).set(mdata).sort().build();
//        m.column_closure();
//        m.sort();
        return m;
    }


//    private NumericMatrix build_matrix(HashMap<Pair<String,String>, ArrayList<Double>> map)
//    {
//        for ( Map.Entry<Pair<String,String>,ArrayList<Double>> e : map.entrySet() ) {
//            Pair<String,String> key = e.getKey();
//            ArrayList<Double> val = e.getValue();
//
//            for ( int midx = 0; midx < this.measures.size(); midx ++ ) {
//                Double d = val.get(midx);
//                String row_key = key.getKey();
//                String col_key = key.getValue();
//                if ( this.measures.size()>1 ) {
//                    col_key = String.format("[%s][%s]", col_key, this.measures.get(midx));
//                }
//                m.cell ( row_key, col_key, d );
//            }
//        }
//        NumericMatrix m = new NumericMatrix();
//        m.column_closure();
//        m.sort();
//        return m;
//    }
    public NumericMatrix pivot (String [] row_dimensions, String [] col_dimensions, String [] measures, PivotFilter filterHandler )
    {
        ArrayList<Integer> row_dim_idx = new ArrayList<>();
        ArrayList<Integer> col_dim_idx = new ArrayList<>();
        ArrayList<Integer> mes_idx = new ArrayList<>();
        for ( String s : row_dimensions ) {
            Integer idx = this.dimensions.indexOf(s);
            row_dim_idx.add(idx);
        }
        if ( col_dimensions != null ) {
            for (String s : col_dimensions) {
                Integer idx = this.dimensions.indexOf(s);
                col_dim_idx.add(idx);
            }
        }
        for ( String s : measures ) {
            Integer idx = this.measures.indexOf(s);
            mes_idx.add(idx);
        }

        HashMap<Pair<String,String>, ArrayList<Double>> map = aggregate(row_dim_idx, col_dim_idx, mes_idx, filterHandler);

        NumericMatrix m = build_matrix(map);

        return m;
    }

}
