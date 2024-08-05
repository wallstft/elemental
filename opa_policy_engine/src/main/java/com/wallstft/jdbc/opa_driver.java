package com.wallstft.jdbc;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class opa_driver implements Driver {

    static private final String jdbc_header = "jdbc:opa_";
    private Driver driver ;

    static {
        try {
            DriverManager.registerDriver(new opa_driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register custom driver", e);
        }
    }

    @Override
    public Connection connect(String opa_url, Properties info) throws SQLException {
        if ( opa_url != null && is_url_acceptable(opa_url) ) {
            String db_url = opa_url.replace(jdbc_header, "jdbc:");
            Connection conn = DriverManager.getConnection(db_url,info);
            return new opa_connection( conn, info);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return is_url_acceptable(url);
    }

    static public boolean is_url_acceptable (String url) throws SQLException {
        return (url!=null && url.startsWith(jdbc_header)?true:false);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return null;
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return true;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
