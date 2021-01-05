package org.neostorm.etl.states;

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
import org.neostorm.etl.ETLHandler;
import org.neostorm.etl.util.StateETL;
import org.neostorm.etl.util.StateUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Michigan extends StateUtils implements StateETL {
    @Override
    public void load(SqlLiteManager sql) {
        ArrayList<Pair<Integer,String>> michigan = new ArrayList();
        michigan.add ( new Pair<Integer,String>(5,"CountyName"));
        michigan.add ( new Pair<Integer,String>(6,"OfficeDescription"));
        michigan.add ( new Pair<Integer,String>(9,"PartyDescription"));
        michigan.add ( new Pair<Integer,String>(11,"CandidateLastName"));
        michigan.add ( new Pair<Integer,String>(15,"CandidateVotes"));

        /*
        +---------------+--------------+------+-----+---------+-------+
| Field         | Type         | Null | Key | Default | Extra |
+---------------+--------------+------+-----+---------+-------+
| election_date | datetime     | YES  |     | NULL    |       |
| state         | varchar(50)  | YES  |     | NULL    |       |
| county        | varchar(100) | YES  |     | NULL    |       |
| office        | varchar(200) | YES  |     | NULL    |       |
| party         | varchar(50)  | YES  |     | NULL    |       |
| candidate     | varchar(100) | YES  |     | NULL    |       |
| votes         | int(11)      | YES  |     | NULL    |       |
         */


        load_election_data("/Users/kevintboyle/elections/StatesData/Michigan/2016/2016GEN_MI_CENR_BY_COUNTY_ORIG.xlsx", "2016GEN_MI_CENR_BY_COUNTY", 1, michigan, new ETLHandler() {
            @Override
            public void handler(List<Pair<Integer, String>> columns, String[] values) {
                String[] list = new String[values.length+3];
                list[0]="20161103";
                list[1]="Michigan";
                list[2]=standardized_ofice(values[1]); //standardize office.
                for ( int i=0; i<values.length; i++ ) {
                    list[i+3] = trim(values [i]);
                }
                list[5]=standardized_party(list[5]);
                PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, office_type, county, office, party, candidate, votes ) values ( ?, ?, ?, ?, ?, ?, ?, ? )");
                sql.preparedStatement( pstmt, list);
            }
        });

        load_election_data("/Users/kevintboyle/elections/StatesData/Michigan/2020/2020GEN_MI_CENR_BY_COUNTY_ORIG.xlsx", "2020GEN_MI_CENR_BY_COUNTY", 1, michigan, new ETLHandler() {
            @Override
            public void handler(List<Pair<Integer, String>> columns, String[] values) {
                String[] list = new String[values.length+3];
                list[0]="20201103";
                list[1]="Michigan";
                list[2]=standardized_ofice(values[1]); //standardize office.
                for ( int i=0; i<values.length; i++ ) {
                    list[i+3] = trim(values [i]);
                }
                list[5]=standardized_party(list[5]);
                PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, office_type, county, office, party, candidate, votes ) values ( ?, ?, ?, ?, ?, ?, ?, ? )");
                sql.preparedStatement( pstmt, list);
            }
        });
    }
}
