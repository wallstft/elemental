package com.wallstft.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

public class opa_prepared_statement implements PreparedStatement {

    PreparedStatement prepared_statement;

    public opa_prepared_statement ( PreparedStatement stmt ) {
        prepared_statement = stmt;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return this.prepared_statement.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return this.prepared_statement.executeUpdate();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.prepared_statement.setNull(parameterIndex,sqlType);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.prepared_statement.setBoolean(parameterIndex,x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.prepared_statement.setByte(parameterIndex,x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        this.prepared_statement.setShort(parameterIndex,x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        this.prepared_statement.setInt(parameterIndex,x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        this.prepared_statement.setLong(parameterIndex,x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.prepared_statement.setFloat(parameterIndex,x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.prepared_statement.setDouble(parameterIndex,x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        this.prepared_statement.setBigDecimal(parameterIndex,x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        this.prepared_statement.setString(parameterIndex,x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.prepared_statement.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.prepared_statement.setDate(parameterIndex,x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.prepared_statement.setTime(parameterIndex,x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        this.prepared_statement.setTimestamp(parameterIndex,x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.prepared_statement.setAsciiStream(parameterIndex,x,length);
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.prepared_statement.setUnicodeStream(parameterIndex,x,length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.prepared_statement.setBinaryStream(parameterIndex,x,length);
    }

    @Override
    public void clearParameters() throws SQLException {
        this.prepared_statement.clearParameters();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        this.prepared_statement.setObject(parameterIndex,x,targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        this.prepared_statement.setObject(parameterIndex,x);
    }

    @Override
    public boolean execute() throws SQLException {
        return this.prepared_statement.execute();
    }

    @Override
    public void addBatch() throws SQLException {
        this.prepared_statement.addBatch();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        this.prepared_statement.setCharacterStream(parameterIndex, reader, length );
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        this.prepared_statement.setRef(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        this.prepared_statement.setBlob(parameterIndex,x);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        this.prepared_statement.setClob(parameterIndex,x);
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        this.prepared_statement.setArray(parameterIndex,x);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.prepared_statement.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        this.prepared_statement.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        this.prepared_statement.setTime(parameterIndex, x, cal );
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        this.prepared_statement.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.prepared_statement.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.prepared_statement.setURL(parameterIndex, x );
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return this.prepared_statement.getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.prepared_statement.setRowId(parameterIndex, x );
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        this.prepared_statement.setNString(parameterIndex, value );
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        this.prepared_statement.setNCharacterStream( parameterIndex, value, length );
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.prepared_statement.setNClob(parameterIndex, value );
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.prepared_statement.setClob(parameterIndex,reader,length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        this.prepared_statement.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.prepared_statement.setNClob(parameterIndex, reader, length );
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        this.prepared_statement.setSQLXML( parameterIndex, xmlObject );
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        this.prepared_statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength );
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.prepared_statement.setAsciiStream( parameterIndex, x, length );
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.prepared_statement.setBinaryStream( parameterIndex, x, length );
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        this.prepared_statement.setCharacterStream( parameterIndex, reader, length );
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        this.prepared_statement.setAsciiStream( parameterIndex, x );
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        this.prepared_statement.setBinaryStream( parameterIndex, x );
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        this.prepared_statement.setCharacterStream( parameterIndex, reader );
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        this.prepared_statement.setNCharacterStream( parameterIndex, value );
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.prepared_statement.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        this.prepared_statement.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.prepared_statement.setNClob( parameterIndex, reader );
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return         this.prepared_statement.executeQuery( sql) ;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return         this.prepared_statement.executeUpdate(sql);
    }

    @Override
    public void close() throws SQLException {
        this.prepared_statement.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return         this.prepared_statement.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        this.prepared_statement.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return         this.prepared_statement.getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        this.prepared_statement.setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        this.prepared_statement.setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return         this.prepared_statement.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        this.prepared_statement.getQueryTimeout();
    }

    @Override
    public void cancel() throws SQLException {
        this.prepared_statement.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return         this.prepared_statement.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.prepared_statement.clearWarnings();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        this.prepared_statement.setCursorName(name);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return         this.prepared_statement.execute(sql);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return         this.prepared_statement.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return         this.prepared_statement.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return         this.prepared_statement.getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        this.prepared_statement.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return         this.prepared_statement.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        this.prepared_statement.setFetchDirection(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return         this.prepared_statement.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return         this.prepared_statement.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return         this.prepared_statement.getResultSetType();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        this.prepared_statement.addBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        this.prepared_statement.clearBatch();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return         this.prepared_statement.executeBatch();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return         this.prepared_statement.getConnection();
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return         this.prepared_statement.getMoreResults();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return         this.prepared_statement.getGeneratedKeys();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return         this.prepared_statement.executeUpdate();
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return         this.prepared_statement.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return         this.prepared_statement.executeUpdate(sql, columnNames);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return         this.prepared_statement.execute(sql,autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return         this.prepared_statement.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return         this.prepared_statement.execute(sql, columnNames);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return         this.prepared_statement.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return         this.prepared_statement.isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        this.prepared_statement.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return         this.prepared_statement.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        this.prepared_statement.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return         this.prepared_statement.isCloseOnCompletion();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return         this.prepared_statement.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return         this.prepared_statement.isWrapperFor(iface);
    }
}
