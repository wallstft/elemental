package org.neostorm.etl;

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

import org.neostorm.db.SqlLiteManager;
import org.neostorm.etl.states.Arizona;
import org.neostorm.etl.states.Georgia;
import org.neostorm.etl.states.Michigan;
import org.neostorm.etl.states.Pennsylvania;
import org.neostorm.etl.util.StateETL;

import java.io.File;

public class etl_main {

    public static void main( String[] args ) throws Exception
    {
        etl_main etl = new etl_main();
        Class.forName("com.mysql.jdbc.Driver");

        String filename_path = "/Users/kevintboyle/elections/DB/election.DB";
        File db = new File (filename_path);

        if (db.exists()) {
            db.delete();
        }

        SqlLiteManager sql = new SqlLiteManager(db);

        if ( sql.isDatabaseEmpty() ) {
            etl.create_table ( sql ) ;
        }

        sql.execute("delete from election_data");



        StateETL[] list = { new Michigan(), new Pennsylvania(), new Georgia(), new Arizona()   };
//        StateETL[] list = { new Arizona() };

        for (StateETL state : list ) {
            if ( state != null ) {
                state.load(sql);
            }
        }
    }

    public void create_table ( SqlLiteManager sql )
    {
        /*
| election_date | datetime     | YES  |     | NULL    |       |
| state         | varchar(50)  | YES  |     | NULL    |       |
| county        | varchar(100) | YES  |     | NULL    |       |
| office        | varchar(200) | YES  |     | NULL    |       |
| party         | varchar(50)  | YES  |     | NULL    |       |
| candidate     | varchar(100) | YES  |     | NULL    |       |
| votes         | int(11)      | YES  |     | NULL    |       |
         */
        String create_table_sql = String.format("create table election_data ( election_date datetime, state varchar(50), county varchar(100), office varchar(100), party varchar(50), candidate varchar(100), votes int, vote_type varchar(100), office_type varchar(100) )");
        sql.execute(create_table_sql );
    }

}
