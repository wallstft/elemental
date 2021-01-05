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

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.neostorm.db.SqlLiteManager;
import org.neostorm.etl.util.RowFieldHandler;
import org.neostorm.etl.util.StateETL;
import org.neostorm.etl.util.StateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;

public class Georgia extends StateUtils implements StateETL {

    @Override
    public void load(SqlLiteManager sql) {
        load_2020(sql);
        load_2016(sql);
    }
    public void load_2016(SqlLiteManager sql) {

        try {
            String path = "/Users/kevintboyle/elections/StatesData/Georgia/2016/Georgia2016.xlsx";
            FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);
            for ( int s =0 ; s<workbook.getNumberOfSheets(); s++ ) {
                Sheet sheet = workbook.getSheetAt(s);
                if ( sheet != null ) {
                    String sheet_name = sheet.getSheetName();
                    if ( sheet_name != null && sheet_name.equals("URL") ) {
                        continue;
                    }
                    load_race ( "20161103", "Georgia", sheet, new RowFieldHandler() {
                        @Override
                        public void row(String election_date, String state, String county, String office, String party, String candidate, String votes, String vote_type ) {
                            try {
                                PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, county, office, party, candidate, votes, office_type ) values ( ?, ?, ?, ?, ?, ?, ?, ? )");
                                String[] list = {trim(election_date), trim(state), trim(county), trim(office), standardized_party(trim(party)), trim(candidate), trim(votes), standardized_ofice(trim(office))};
                                sql.preparedStatement(pstmt, list);
                            }
                            catch ( Exception ex ) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }

    }







    public void load_2020(SqlLiteManager sql) {


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

        parse_2020_xml(new RowFieldHandler() {
            @Override
            public void row(String election_date, String state, String county, String office, String party, String candidate, String votes, String vote_type_name ) {

                String embeded_party = getParty(candidate);
                if ( embeded_party == null ) {
                    embeded_party = party;
                }

//                System.out.println(String.format("election_date=%s, state=%s, county=%s, office=%s, party=%s, candidate=%s, votes=%s",
//                        election_date,
//                        state,
//                        county,
//                        office,
//                        embeded_party,
//                        candidate,
//                        votes));

                try {
                    PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, county, office, party, candidate, votes, vote_type, office_type ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )");
                    String[] list = {trim(election_date), trim(state), trim(county), trim(office), standardized_party(trim(embeded_party)), trim(candidate), trim(votes), trim(vote_type_name), standardized_ofice(trim(office))};
                    sql.preparedStatement(pstmt, list);
//                    pstmt.executeUpdate();
                }
                catch ( Exception ex ) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
