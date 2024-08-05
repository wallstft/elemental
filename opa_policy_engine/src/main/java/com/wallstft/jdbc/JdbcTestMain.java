package com.wallstft.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcTestMain {
    public static void main(String[] args) {
        try {
            // Load custom JDBC driver (ensure your driver is in the classpath)
            DriverManager.registerDriver(new net.snowflake.client.jdbc.SnowflakeDriver());

            // Create connection using your custom wrapper
            String url = "jdbc:snowflake://your_account.snowflakecomputing.com";
            Properties properties = new Properties();
            properties.put("user", "your_user");
            properties.put("password", "your_password");

            opa_connection connection = new opa_connection(url, properties);
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM sales_data");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
