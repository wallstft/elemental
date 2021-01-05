package org.neostorm.data;

/*
   Copyright 2018 Wall Street Fin Tech

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   See the License for the specific language governing permissions and
   limitations under the License. 
   
    
    */

import org.neostorm.data_structures.Pair;

import java.util.*;

public class NumericMatrixBuilder {

    private ArrayList<NumericMatrixPair> row_names = new ArrayList<>();
    private ArrayList<NumericMatrixPair> columns_names = new ArrayList<>();
    private ArrayList<ArrayList<Double>> data = new ArrayList<>();


    static public NumericMatrixBuilder instance() {
        NumericMatrixBuilder builder = new NumericMatrixBuilder();
        return builder;
    }

    public NumericMatrixBuilder setRowNames ( ArrayList<String> rnames )
    {
        String[] d= new String[rnames.size()];
        rnames.toArray(d);
        setRowNames(d);
        return this;
    }
    public NumericMatrixBuilder setColNames ( ArrayList<String> cnames )
    {
        String[] d= new String[cnames.size()];
        cnames.toArray(d);
        setColNames(d);
        return this;
    }
    public NumericMatrixBuilder setRowNames ( String[] rnames )
    {
        row_names.clear();
        for ( int i=0; i<rnames.length; i++ ) {
            row_names.add( new NumericMatrixPair( rnames[i], i));
        }
        return this;
    }

    public NumericMatrixBuilder setColNames ( String [] cnames ) {
        columns_names.clear();
        for ( int i=0; i<cnames.length; i++ ) {
            columns_names.add( new NumericMatrixPair( cnames[i], i));
        }
        return this;
    }

    public NumericMatrixBuilder addDataRow ( String rname, double[] d )
    {
        row_names.add ( new NumericMatrixPair(rname, row_names.size()));
        ArrayList<Double> l = new ArrayList<>();
        for ( double n : d )
        {
            l.add(n);
        }
        data.add(l);
        return this;
    }

    private void addAll (List<NumericMatrixPair> list, String [] data)
    {
        for ( int i=0; i<data.length; i++ ) {
            list.add(new NumericMatrixPair( data[i], i));
        }
    }

    public NumericMatrixBuilder set ( NumericMatrix m )
    {
        row_names.clear();
        columns_names.clear();
        addAll(row_names, m.getRowNames());
        addAll(columns_names, m.getColNames());
        double[][] mdata = m.getDoubleMatrix();
        for ( int i=0; i<mdata.length; i++ ) {
            ArrayList<Double> l = new ArrayList<>();
            for ( double d : mdata[i])
                l.add(d);
            data.add(l);
        }
        return this;
    }

    public NumericMatrixBuilder set ( double[][]mdata )
    {
        for ( int i=0; i<mdata.length; i++ ) {
            ArrayList<Double> l = new ArrayList<>();
            for ( double d : mdata[i])
                l.add(d);
            data.add(l);
        }
        return this;
    }

    public NumericMatrixBuilder sortRows()
    {
        row_names.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        return this;
    }

    public NumericMatrixBuilder sortCols()
    {
        columns_names.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        return this;
    }

    public NumericMatrixBuilder sort()
    {
        sortRows();
        sortCols();
        return this;
    }

    public NumericMatrixBuilder columnVectorSort ( Comparator<double[]> handler )
    {
        for ( int i=0; i<columns_names.size(); i++ ) {
            NumericMatrixPair c1 = columns_names.get(i);
            double[] column1 = new double[row_names.size()];
            int idx = 0;
            for ( NumericMatrixPair r : row_names ) {
                column1[idx++] = data.get(r.getValue()).get(c1.getValue());
            }
            for ( int j =i+1; j<columns_names.size(); j++ ) {
                NumericMatrixPair c2 = columns_names.get( j);
                double[] column2 = new double[row_names.size()];
                idx = 0;
                for ( NumericMatrixPair r : row_names ) {
                    column2[idx++] = data.get(r.getValue()).get(c2.getValue());
                }
                if ( handler != null ) {
                    if ( c2.getKey().equalsIgnoreCase("[Whitfield]")) {
                        int s = 0;
                    }
                    if ( handler.compare( column1, column2 )> 0 ) {
                        NumericMatrixPair tn = columns_names.get(i);
                        columns_names.set(i, columns_names.get(j));
                        columns_names.set(j, tn );
                        column1 = column2;
                        c1 = c2;
                    }
                }
            }
        }
        return this;
    }

    private ArrayList addEmptyRow ( int size )
    {
        ArrayList<Double> l = new ArrayList<>();
        for ( int i=0; i<size; i++ ){
            l.add(0.0);
        }
        return l;
    }

    private NumericMatrixBuilder row_closure ( NumericMatrix A, NumericMatrix B )
    {
        if ( A.getCols() == B.getCols() ) {
            set(A);
            ArrayList<String> row_list = new ArrayList();
            for (String n : B.getRowNames()) {
                if (!row_names.contains(n)) {
                    row_names.add(new NumericMatrixPair(n,row_list.size()));
                    data.add( addEmptyRow(A.getCols()));
                }
            }
        }
        else {
            System.out.println("row_closure can only operate on matrices with the same number of columns");
        }
        return this;
    }

    private Double get ( int r, int c )
    {
        if ( r != -1 && c != -1 ) {
            return data.get(r).get(c);
        }
        return 0.0;
    }

    private Double get ( String row, String col )
    {
        int r = indexOf (row_names, row);
        int c = indexOf (columns_names, col);
        return get (r,c);
    }

    private Double set ( int r, int c, Double v )
    {
        if ( r != -1 && c != -1 ) {
            return data.get(r).set(c,v);
        }
        return 0.0;
    }

    private Double set ( String row, String col, Double v )
    {
        int r = indexOf ( row_names, row);
        int c = indexOf ( columns_names, col);
        return set (r,c,v);
    }

    static public double[][] diag( double[] v )
    {
        double[][] data = new double[v.length][v.length];
        for ( int i=0; i<v.length; i++) {
            data[i][i]=v[i];
        }
        return data;
    }

    public NumericMatrixBuilder groupTotalByRow (MatrixArithmetic.GroupingHandler handler, NumericMatrix matrix )
    {
        HashMap<Pair<String,String>, Double> map = new HashMap<>();
        row_names.clear();
        columns_names.clear();
        data.clear();
        matrix.walker( new NumericMatrix.IteratorAction() {
                           @Override
                           public void walk(String row_name, int row, String col_name, int col, double v) {
                               String group_name = handler.getGroupName(row_name, row);
                               NumericMatrixPair row_pair = new NumericMatrixPair(group_name,     row_names.size());
                               NumericMatrixPair col_pair = new NumericMatrixPair(col_name,       columns_names.size());
                               if ( !row_names.contains(row_pair))
                                   row_names.add(row_pair);
                               if ( !columns_names.contains(col_pair))
                                   columns_names.add(col_pair);
                           }
                       });
        double[][] dvector = new double[row_names.size()][columns_names.size()];
        matrix.walker( new NumericMatrix.IteratorAction() {
            @Override
            public void walk(String row_name, int row, String col_name, int col, double v) {
                try {
                    if ( handler != null ) {
                        try {
                            String group_name = handler.getGroupName(row_name, row);
                            int row_idx = indexOf (row_names, group_name);
                            int col_idx = indexOf ( columns_names, col_name);
                            dvector[row_idx][col_idx] += v;
                        }
                        catch (Exception ex )
                        {
                            ex.printStackTrace();
                        }
                    }
                }
                catch ( Exception ex ) {
                    ex.printStackTrace();
                }
            }
        });
        for ( int r=0; r<dvector.length; r++ ) {
            ArrayList<Double> row = null;
            data.add(row=new ArrayList<>());
            for ( int c=0; c<dvector[r].length; c++ ) {
                row.add(dvector[r][c]);
            }
        }


        return this;
    }

    public NumericMatrix build()
    {
        double[][] d = new double [row_names.size()][columns_names.size()];
        for ( int r=0;r<row_names.size(); r++ ) {
            for ( int c=0; c<columns_names.size(); c++ ) {
                NumericMatrixPair rn= row_names.get(r);
                NumericMatrixPair cn= columns_names.get(c);
                d[r][c] = data.get(rn.getValue()).get(cn.getValue());
            }
        }
        String[] rn = new String[row_names.size()];
        String[] cn = new String[columns_names.size()];
        for ( int i=0; i<row_names.size(); i++ ) {
            NumericMatrixPair r = row_names.get(i);
            rn[i] = r.getKey();
        }
        for ( int i=0; i<columns_names.size(); i++ ) {
            NumericMatrixPair r = columns_names.get(i);
            cn[i] = r.getKey();
        }
        try {
            NumericMatrix m = new NumericMatrix(rn, cn, d);
            return m;
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return null;
    }


    public int indexOf ( ArrayList<NumericMatrixPair> list, String val )
    {
        for ( int i=0; i<list.size(); i++ ) {
            if ( val != null && list.get(i) != null && list.get(i).getKey() != null && list.get(i).getKey().equals(val) )
                return i;
        }
        return -1;
    }


//    public NumericMatrixBuilder addRow ( String row_name, String[] cnames, double[] d )
//    {
//        if (columns_names.size() == cnames.length ) {
//            for ( int i=0; i<cnames.length; i++ ) {
//
//                String col_name = cnames[i];
//                cell ( row_name, col_name, data[i] );
//            }
//        }
//        else {
//            System.out.println("Column numbers must match");
//        }
//        return this;
//    }
//
//    public boolean rowKeyExist ( String key )
//    {
//        for ( Pair<String,Integer> r : row_names ) {
//            if ( r != null && r.getKey() !=null && r.getKey().equals(key)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void addRow ( String row_key )
//    {
//        if ( !rowKeyExist( row_key )) {
//            row_names.add(new Pair(row_key, row_names.size()));
//            ArrayList<Double> list = new ArrayList<>();
//            for ( int c=0; c<columns_names.size(); c++ ) {
//                list.add(0.0);
//            }
//            data.add(list);
//        }
//    }

//    public void sort_rows()
//    {
//        for ( int i=0;i<row_names.size(); i++ ) {
//            for ( int j=0; j<row_names.size(); j++ ) {
//                Pair<String,Integer> i_name = row_names.get(i);
//                Pair<String,Integer> j_name = row_names.get(j);
//                if ( i_name.getKey().compareTo(j_name.getKey())< 0 ) {
//                    Pair<String,Integer> tmp_name = row_names.get(i);
//                    row_names.set(i, row_names.get(j));
//                    row_names.set(j, tmp_name );
//                }
//            }
//        }
//    }

//    public void sort_cols()
//    {
//        for ( int i=0;i<columns_names.size(); i++ ) {
//            for ( int j=0; j<columns_names.size(); j++ ) {
//                Pair<String,Integer> i_name = columns_names.get(i);
//                Pair<String,Integer> j_name = columns_names.get(j);
//                if ( i_name.getKey().compareTo(j_name.getKey())< 0 ) {
//                    Pair<String,Integer> tmp_name = columns_names.get(i);
//                    columns_names.set(i, columns_names.get(j));
//                    columns_names.set(j, tmp_name );
//                }
//            }
//        }
//    }

//    public void sort_data_by_col( Comparator<Double[]> comp)
//    {
//        for ( int i=0;i<columns_names.size(); i++ ) {
//            for ( int j=0; j<columns_names.size(); j++ ) {
//                Pair<String,Integer> i_name = columns_names.get(i);
//                Pair<String,Integer> j_name = columns_names.get(j);
//                Double [] iv = getColumnVector ( i_name );
//                Double [] jv = getColumnVector ( j_name );
//                if ( comp.compare(iv, jv )< 0 ) {
//                    Pair<String,Integer> tmp_name = columns_names.get(i);
//                    columns_names.set(i, columns_names.get(j));
//                    columns_names.set(j, tmp_name );
//                }
//            }
//        }
//    }

//    public void sort()
//    {
//        sort_rows();
//        sort_cols();
//    }

//    public void column_closure()
//    {
//        for ( int r=0; r<row_names.size(); r++ ) {
//            ArrayList<Double> list = data.get(r);
//            for ( int c=list.size(); c<columns_names.size(); c++ ) {
//                list.add(0.0);
//            }
//        }
//    }
//
//    public void setColNames ( String [] cn )
//    {
//        columns_names.clear();
//        for ( String c : cn )
//        {
//            columns_names.add(c);
//        }
//    }
//
//    public void setRowNames ( String [] rn )
//    {
//        row_names.clear();
//        for ( String s: rn ) {
//            row_names.add(s);
//        }
//    }
//

//
//    public ArrayList addEmptyRow ( int size )
//    {
//        ArrayList<Double> l = new ArrayList<>();
//        for ( int i=0; i<size; i++ ){
//            l.add(0.0);
//        }
//        return l;
//    }

//    public void cell ( int row, int col, Double value ) {
//        update_cell( row, col, value );
//        for ( int r =row_names.size(); r<=row; r++ ) {
//            row_names.add("");
//        }
//        for ( int c =columns_names.size(); c<=col; c++ ) {
//            columns_names.add("");
//        }
//    }
//    public void cell ( String row, String col, Double value )
//    {
//        if ( row != null && col != null && value != null ) {
//            int col_idx = getIndex ( columns_names, col);
//            int row_idx = getIndex ( row_names, row);
//            update_cell ( row_idx, col_idx, value );
//        }
//    }
//
//    private int getIndex( ArrayList<String> list, String col )
//    {
//        return list.indexOf(col);
//    }
//
//    private void update_cell ( int row, int col, Double value )
//    {
//        for ( int r = data.size(); r<=row; r++ ) {
//            data.add(new ArrayList<>());
//        }
//        for ( int r=0; r<=row; r++ ) {
//            ArrayList<Double> row_list = data.get(r);
//            for (int c = row_list.size(); c <= col; c++) {
//                row_list.add(0.0);
//            }
//        }
//        ArrayList<Double> row_list = data.get(row);
//        row_list.set ( col, value );
//    }


}
