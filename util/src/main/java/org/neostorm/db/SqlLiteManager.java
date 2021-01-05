package org.neostorm.db;

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

import java.io.File;
import java.sql.*;

public class SqlLiteManager {

    private Connection connect = null;
    File db = null;

    public SqlLiteManager(File filename_path)
    {
        db = filename_path;
        connect();
    }

    public void connect()
    {
        try {
//            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/election?user=root");
            String url = "jdbc:sqlite:"+db.getCanonicalPath();
            connect = DriverManager.getConnection(url);
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public boolean isDatabaseEmpty()
    {
        if ( db == null ) {
            return false;
        }
        if ( !db.exists() ) {
            return false;
        }
        if ( db != null && db.exists() && db.length() == 0 ) {
            return true;
        }

        return false;
    }

    public void executeQuery ( String sql, jdbcHandler handler )
    {
        if ( connect != null && sql != null ) {
            if ( handler != null ) {
                try {
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if ( rs != null ) {
                        handler.handler(rs);
                    }
                }
                catch ( Exception ex ) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void execute ( String sql ) {
        if ( connect != null && sql != null ) {
            try {
                Statement stmt = connect.createStatement();
                stmt.execute(sql);
            }
            catch ( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }

    public PreparedStatement createPreparedStatement (String sql)
    {
        PreparedStatement pstmt = null;
        try {
            pstmt = connect.prepareStatement(sql);
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return pstmt;
    }

    public void preparedStatement ( PreparedStatement pstmt, String[] args  )
    {
        try {
            if ( args != null ) {
                for ( int i=0; i<args.length; i++ ) {
                    String s = args[i];
                    pstmt.setString ( i+1, s );
                }
                pstmt.addBatch();
                pstmt.execute();
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
