package org.neostorm.opa;

import com.wallstft.jdbc.opa_driver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.sql.*;
import java.util.Properties;

public class JdbcTestMain {
    public static void main(String[] args) {
        try {
            // Load custom JDBC driver (ensure your driver is in the classpath)
            // DriverManager.registerDriver(new net.snowflake.client.jdbc.SnowflakeDriver());

            DriverManager.registerDriver(new opa_driver());


            // Create connection using your custom wrapper
            String url = "jdbc:snowflake://your_account.snowflakecomputing.com";
            String opa_url = "jdbc:opa_snowflake://your_account.snowflakecomputing.com";
            Properties properties = new Properties();
            properties.put("user", "your_user");
            properties.put("password", "your_password");

            String db_filename = "/Users/kevinboyle/sqlite/db.sql3";
            File f =new File(db_filename);
            if ( f.exists() ) {
                f.delete();
            }
            String opa_sqlite = String.format("jdbc:opa_sqlite:%s", db_filename );
            Connection connection = DriverManager.getConnection(opa_sqlite, properties );
            Statement stmt = connection.createStatement();

            stmt.execute("create table sales_data ( first_name varchar(100), last_name varchar(100))");
            stmt.execute("insert into sales_data ( first_name, last_name) values ( 'kevin', 'boyle')");
            stmt.execute("insert into sales_data ( first_name, last_name) values ( 'gwen', 'boyle')");
            stmt.execute("insert into sales_data ( first_name, last_name) values ( 'naomi', 'boyle')");
            stmt.execute("insert into sales_data ( first_name, last_name) values ( 'Heather', 'Perrin-Boyle')");
            stmt.execute("insert into sales_data ( first_name, last_name) values ( 'Neo', 'Perrin-Boyle')");
            stmt.execute("insert into sales_data ( first_name, last_name) values ( 'Email', 'kevintboyle@yahoo.com')");
            stmt.execute("insert into sales_data ( first_name, last_name) values ( 'SSN', '056-54-6578')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM sales_data");
            while (rs.next()) {
                System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
