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
import org.neostorm.etl.util.RowHandler;
import org.neostorm.etl.util.StateETL;
import org.neostorm.etl.util.StateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;

public class Arizona extends StateUtils implements StateETL {
    @Override
    public void load(SqlLiteManager sql) {
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

        try {
            {
                FileInputStream excelFile = new FileInputStream(new File("/Users/kevintboyle/elections/StatesData/Arizona/2016/2016_AZ_HandEnteredFromPDF.xlsx"));
                Workbook workbook = new XSSFWorkbook(excelFile);

                //String date, String state, int header_row, int start_row, Sheet sheet, RowHandler handler
                for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
                    Sheet sheet = workbook.getSheetAt(s);
                    load_sheet_rows(0, 1, sheet, new RowHandler() {
                        @Override
                        public void header(String[] header, String[] values) {

                            if ( header != null && values != null && header.length == values.length ) {
                                for (int i = 3; i < header.length; i++) {
                                    String county = header[i];
                                    String votes = values[i];
                                    String office = values[1];
                                    String party = values[2];
                                    String candidate = values[0];
                                    String office_type = standardized_ofice(trim(office));

                                    if ( votes != null && Double.valueOf(votes)>0 ) {
                                        PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, county, office, party, candidate, votes, office_type ) values ( ?, ?, ?, ?, ?, ?, ?, ? )");
                                        String[] list = {"20161103", "Arizona", trim(county), trim(office), standardized_party(trim(party)), trim(candidate), trim(votes), office_type };
                                        sql.preparedStatement(pstmt, list);
                                    }
                                }
                            }
                        }
                    });
                }
            }
            {
                FileInputStream excelFile = new FileInputStream(new File("/Users/kevintboyle/elections/StatesData/Arizona/2020/2020_AZ_HandEnteredFromPDF.xlsx"));
                Workbook workbook = new XSSFWorkbook(excelFile);

                //String date, String state, int header_row, int start_row, Sheet sheet, RowHandler handler
                for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
                    Sheet sheet = workbook.getSheetAt(s);
                    load_sheet_rows( 0, 1, sheet, new RowHandler() {
                        @Override
                        public void header(String[] header, String[] values) {
                            if ( header != null && values != null && header.length == values.length ) {
                                for (int i = 3; i < header.length; i++) {
                                    String county = header[i];
                                    String votes = values[i];
                                    String office = values[1];
                                    String party = values[2];
                                    String candidate = values[0];
                                    String office_type = standardized_ofice(trim(office));

                                    if ( votes != null && Double.valueOf(votes)>0 ) {
                                        PreparedStatement pstmt = sql.createPreparedStatement("insert into election_data( election_date, state, county, office, party, candidate, votes, office_type ) values ( ?, ?, ?, ?, ?, ?, ?, ? )");
                                        String[] list = {"20201103", "Arizona", trim(county), trim(office), standardized_party(trim(party)), trim(candidate), trim(votes), office_type};
                                        sql.preparedStatement(pstmt, list);
                                    }
                                }
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
}
