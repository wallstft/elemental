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


import org.junit.Test;
import org.neostorm.db.SqlLiteManager;

import java.io.File;
import java.util.ArrayList;


public class PivotTest {

    @Test
    public void pivot_test() {
        String filename_path = "/Users/kevintboyle/elections/DB/election.DB";
        File db_file = new File(filename_path);

        SqlLiteManager db = new SqlLiteManager(db_file);

        String sql = "select election_date, state, county, office, office_type, party, sum(votes) as votes from election_data group by election_date, state, county, office, office_type, party";
        String[] dim = {"election_date", "state", "county", "office", "office_type", "party"};
        String[] mes = {"votes"};
        Pivot p = new Pivot(db, sql, dim, mes);

        NumericMatrix m = p.pivot(new String[]{"election_date", "state", "office", "party"}, new String[]{"county"}, new String[]{"votes"}, new PivotFilter() {
            Integer party_idx = -1;
            Integer office_idx = -1;
            Integer state_idx = -1;
            @Override
            public boolean include(ArrayList<String> dimension_names, ArrayList<String> dimension_values) {
                if ( party_idx < 0 ) {
                    party_idx = dimension_names.indexOf("party");
                }
                if ( office_idx < 0 ) {
                    office_idx = dimension_names.indexOf("office_type");
                }
                if ( state_idx < 0 ) {
                    state_idx = dimension_names.indexOf("state");
                }
                if ( dimension_values.get(state_idx) != null &&  dimension_values.get(state_idx).toUpperCase().contains("ARIZONA")) {
                    if (dimension_values.get(office_idx) != null && dimension_values.get(office_idx).toUpperCase().contains("PRESIDENT")) {
                        String party = dimension_values.get(party_idx);
                        if (party != null && (party.equalsIgnoreCase("REPUBLICAN") || party.equalsIgnoreCase("DEMOCRATIC"))) {
                            return true;
                        }
                        return false;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        NumericMatrix m2 = p.pivot(new String[]{ "county"}, new String[]{"election_date","party"}, new String[]{"votes"}, new PivotFilter() {
            Integer party_idx = -1;
            Integer office_idx = -1;
            Integer state_idx = -1;
            @Override
            public boolean include(ArrayList<String> dimension_names, ArrayList<String> dimension_values) {
                if ( party_idx < 0 ) {
                    party_idx = dimension_names.indexOf("party");
                }
                if ( office_idx < 0 ) {
                    office_idx = dimension_names.indexOf("office_type");
                }
                if ( state_idx < 0 ) {
                    state_idx = dimension_names.indexOf("state");
                }
                if ( dimension_values.get(state_idx) != null &&  dimension_values.get(state_idx).toUpperCase().contains("ARIZONA")) {
                    if (dimension_values.get(office_idx) != null && dimension_values.get(office_idx).toUpperCase().contains("PRESIDENT")) {
                        String party = dimension_values.get(party_idx);
                        if (party != null && (party.equalsIgnoreCase("REPUBLICAN") || party.equalsIgnoreCase("DEMOCRATIC"))) {
                            return true;
                        }
                        return false;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
        if (m != null) {
            NumericMatrixPrint pnt = new NumericMatrixPrint(m);
            pnt.print();

            System.out.println("\n\n");
            NumericMatrixPrint pnt2 = new NumericMatrixPrint(m2);
            pnt2.print(true,true);
        }

    }
}
