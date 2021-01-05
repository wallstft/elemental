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

public class Pennsylvania extends StateUtils implements StateETL {
    @Override
    public void load(SqlLiteManager sql) {
        ArrayList<Pair<Integer,String>> field_list = new ArrayList();
        field_list.add ( new Pair<Integer,String>(1,"County Name"));
        field_list.add ( new Pair<Integer,String>(2,"Office Name"));
        field_list.add ( new Pair<Integer,String>(5,"Candidate Name"));
        field_list.add ( new Pair<Integer,String>(6,"Votes"));
        field_list.add ( new Pair<Integer,String>(4,"Party Name"));
        field_list.add ( new Pair<Integer,String>(9,"Election Day Votes"));
        field_list.add ( new Pair<Integer,String>(12,"Mail Votes"));
        field_list.add ( new Pair<Integer,String>(15,"Provisional Votes"));


        /*
2	County Name
3	Office Name
5	Party Name
6	Candidate Name
7	Votes
10	Election Day Votes
13	Mail Votes
16	Provisional Votes
         */

        /*

        Election Name	County Name	Office Name	District Name	Party Name	Candidate Name	Votes	Yes Votes	No Votes	Election Day Votes	ElectionDay Yes Votes	Election Day No Votes	Mail Votes	Mail Yes Votes	Mail No Votes	Provisional Votes	Provisional Yes Votes	Provisional No Votes

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


2	County Name
3	Office Name
5	Party Name
6	Candidate Name
7	Votes
10	Election Day Votes
13	Mail Votes
16	Provisional Votes

         */


        load_election_data("/Users/kevintboyle/elections/StatesData/Pennsylvania/2016/2016_PENN_OFFICIAL.xlsx", "Official", 1, field_list, new ETLHandler() {
            @Override
            public void handler(List<Pair<Integer, String>> columns, String[] values) {
                String[] list = new String[8];
                list[0]="20161103";
                list[1]="Pennsylvania";
                list[2]=trim(values[0]); //county
                list[3]=trim(values[1]); //office
                list[4]=standardized_party(trim(values[4])); //party
                list[5]=trim(values[2]); //candidate
                list[6]=remove_comma(values[3]); //votes
                list[7]=standardized_ofice( list[3] );  //standard_office


                PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, county, office, party, candidate, votes, office_type ) values ( ?, ?, ?, ?, ?, ?, ?, ? )");
                sql.preparedStatement(pstmt, list);
            }
        });

        load_election_data("/Users/kevintboyle/elections/StatesData/Pennsylvania/2020/2020_PENN_UNOFFICIAL.xlsx", "UnOfficial", 1, field_list, new ETLHandler() {
            @Override
            public void handler(List<Pair<Integer, String>> columns, String[] values) {
                String[] list = new String[8];
                list[0]="20201103";
                list[1]="Pennsylvania";
                list[2]=trim(values[0]); //county
                list[3]=trim(values[1]); //office
                list[4]=standardized_party(trim(values[4])); //party
                list[5]=trim(values[2]); //candidate
                list[6]=remove_comma(values[3]); //votes
                list[7]=standardized_ofice( list[3] );  //standard_office

                PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, county, office, party, candidate, votes, office_type ) values ( ?, ?, ?, ?, ?, ?, ?, ? )");
                sql.preparedStatement(pstmt, list);
            }
        });
    }
}
