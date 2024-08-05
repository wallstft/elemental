package com.wallstft.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

public class opa_callable_statement implements CallableStatement {

    CallableStatement callable_statement = null;

    public opa_callable_statement ( CallableStatement stmt ) {
        this.callable_statement = stmt;
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        this.callable_statement.registerOutParameter( parameterIndex, sqlType );
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        this.callable_statement.registerOutParameter(parameterIndex, sqlType, scale );
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.callable_statement.wasNull();
    }

    @Override
    public String getString(int parameterIndex) throws SQLException {
        return this.callable_statement.getString(parameterIndex);
    }

    @Override
    public boolean getBoolean(int parameterIndex) throws SQLException {
        return this.callable_statement.getBoolean(parameterIndex);
    }

    @Override
    public byte getByte(int parameterIndex) throws SQLException {
        return this.callable_statement.getByte(parameterIndex);
    }

    @Override
    public short getShort(int parameterIndex) throws SQLException {
        return this.callable_statement.getShort(parameterIndex);
    }

    @Override
    public int getInt(int parameterIndex) throws SQLException {
        return this.callable_statement.getInt(parameterIndex);
    }

    @Override
    public long getLong(int parameterIndex) throws SQLException {
        return this.callable_statement.getLong(parameterIndex);
    }

    @Override
    public float getFloat(int parameterIndex) throws SQLException {
        return this.callable_statement.getFloat(parameterIndex);
    }

    @Override
    public double getDouble(int parameterIndex) throws SQLException {
        return this.callable_statement.getDouble(parameterIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return this.callable_statement.getBigDecimal(parameterIndex, scale);
    }

    @Override
    public byte[] getBytes(int parameterIndex) throws SQLException {
        return this.callable_statement.getBytes(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex) throws SQLException {
        return this.callable_statement.getDate(parameterIndex);
    }

    @Override
    public Time getTime(int parameterIndex) throws SQLException {
        return this.callable_statement.getTime(parameterIndex);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return this.callable_statement.getTimestamp(parameterIndex);
    }

    @Override
    public Object getObject(int parameterIndex) throws SQLException {
        return this.callable_statement.getObject(parameterIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return this.callable_statement.getBigDecimal(parameterIndex);
    }

    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return this.callable_statement.getObject(parameterIndex,map);
    }

    @Override
    public Ref getRef(int parameterIndex) throws SQLException {
        return this.callable_statement.getRef(parameterIndex);
    }

    @Override
    public Blob getBlob(int parameterIndex) throws SQLException {
        return this.callable_statement.getBlob(parameterIndex);
    }

    @Override
    public Clob getClob(int parameterIndex) throws SQLException {
        return this.callable_statement.getClob(parameterIndex);
    }

    @Override
    public Array getArray(int parameterIndex) throws SQLException {
        return this.callable_statement.getArray(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return this.callable_statement.getDate(parameterIndex,cal);
    }

    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return this.callable_statement.getTime(parameterIndex,cal);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return this.callable_statement.getTimestamp(parameterIndex,cal);
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.callable_statement.registerOutParameter(parameterIndex, sqlType, typeName );
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        this.callable_statement.registerOutParameter(parameterName, sqlType);
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        this.callable_statement.registerOutParameter(parameterName, sqlType, scale );
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        this.callable_statement.registerOutParameter(parameterName, sqlType, typeName );
    }

    @Override
    public URL getURL(int parameterIndex) throws SQLException {
        return this.callable_statement.getURL(parameterIndex);
    }

    @Override
    public void setURL(String parameterName, URL val) throws SQLException {
        this.callable_statement.setURL(parameterName, val);
    }

    @Override
    public void setNull(String parameterName, int sqlType) throws SQLException {
        this.callable_statement.setNull( parameterName, sqlType );
    }

    @Override
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        this.callable_statement.setBoolean( parameterName, x );
    }

    @Override
    public void setByte(String parameterName, byte x) throws SQLException {
        this.callable_statement.setByte( parameterName, x );
    }

    @Override
    public void setShort(String parameterName, short x) throws SQLException {
        this.callable_statement.setShort(parameterName, x );
    }

    @Override
    public void setInt(String parameterName, int x) throws SQLException {
        this.callable_statement.setInt( parameterName, x );
    }

    @Override
    public void setLong(String parameterName, long x) throws SQLException {
        this.callable_statement.setLong( parameterName, x );
    }

    @Override
    public void setFloat(String parameterName, float x) throws SQLException {
        this.callable_statement.setFloat( parameterName, x );
    }

    @Override
    public void setDouble(String parameterName, double x) throws SQLException {
        this.callable_statement.setDouble( parameterName, x );
    }

    @Override
    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        this.callable_statement.setBigDecimal( parameterName, x );
    }

    @Override
    public void setString(String parameterName, String x) throws SQLException {
        this.callable_statement.setString( parameterName, x );
    }

    @Override
    public void setBytes(String parameterName, byte[] x) throws SQLException {
        this.callable_statement.setBytes( parameterName, x ) ;
    }

    @Override
    public void setDate(String parameterName, Date x) throws SQLException {
        this.callable_statement.setDate(parameterName,x);
    }

    @Override
    public void setTime(String parameterName, Time x) throws SQLException {
        this.callable_statement.setTime( parameterName, x );
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        this.callable_statement.setTimestamp( parameterName, x );
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        this.callable_statement.setAsciiStream( parameterName, x, length );
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        this.callable_statement.setBinaryStream( parameterName, x, length );
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        this.callable_statement.setObject( parameterName, x, targetSqlType, scale );
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        this.callable_statement.setObject(parameterName, x, targetSqlType);
    }

    @Override
    public void setObject(String parameterName, Object x) throws SQLException {
        this.callable_statement.setObject(parameterName,x);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        this.callable_statement.setCharacterStream(parameterName, reader,length);
    }

    @Override
    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        this.callable_statement.setDate(parameterName, x, cal);
    }

    @Override
    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        this.callable_statement.setTime( parameterName, x, cal );
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        this.callable_statement.setTimestamp(parameterName, x, cal );
    }

    @Override
    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        this.callable_statement.setNull ( parameterName, sqlType, typeName );
    }

    @Override
    public String getString(String parameterName) throws SQLException {
        return         this.callable_statement.getString(parameterName);
    }

    @Override
    public boolean getBoolean(String parameterName) throws SQLException {
        return         this.callable_statement.getBoolean(parameterName);
    }

    @Override
    public byte getByte(String parameterName) throws SQLException {
        return         this.callable_statement.getByte(parameterName);
    }

    @Override
    public short getShort(String parameterName) throws SQLException {
        return         this.callable_statement.getShort(parameterName);
    }

    @Override
    public int getInt(String parameterName) throws SQLException {
        return         this.callable_statement.getInt(parameterName);
    }

    @Override
    public long getLong(String parameterName) throws SQLException {
        return         this.callable_statement.getLong(parameterName);
    }

    @Override
    public float getFloat(String parameterName) throws SQLException {
        return         this.callable_statement.getFloat(parameterName);
    }

    @Override
    public double getDouble(String parameterName) throws SQLException {
        return         this.callable_statement.getDouble(parameterName);
    }

    @Override
    public byte[] getBytes(String parameterName) throws SQLException {
        return         this.callable_statement.getBytes(parameterName);
    }

    @Override
    public Date getDate(String parameterName) throws SQLException {
        return         this.callable_statement.getDate(parameterName);
    }

    @Override
    public Time getTime(String parameterName) throws SQLException {
        return         this.callable_statement.getTime(parameterName);
    }

    @Override
    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return         this.callable_statement.getTimestamp(parameterName);
    }

    @Override
    public Object getObject(String parameterName) throws SQLException {
        return         this.callable_statement.getObject(parameterName);
    }

    @Override
    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return         this.callable_statement.getBigDecimal(parameterName);
    }

    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return         this.callable_statement.getObject(parameterName, map);
    }

    @Override
    public Ref getRef(String parameterName) throws SQLException {
        return         this.callable_statement.getRef(parameterName);
    }

    @Override
    public Blob getBlob(String parameterName) throws SQLException {
        return         this.callable_statement.getBlob(parameterName);
    }

    @Override
    public Clob getClob(String parameterName) throws SQLException {
        return         this.callable_statement.getClob(parameterName);
    }

    @Override
    public Array getArray(String parameterName) throws SQLException {
        return         this.callable_statement.getArray(parameterName);
    }

    @Override
    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return         this.callable_statement.getDate(parameterName, cal);
    }

    @Override
    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return         this.callable_statement.getTime(parameterName, cal);
    }

    @Override
    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return         this.callable_statement.getTimestamp( parameterName, cal );
    }

    @Override
    public URL getURL(String parameterName) throws SQLException {
        return         this.callable_statement.getURL(parameterName);
    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        return         this.callable_statement.getRowId(parameterIndex);
    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        return         this.callable_statement.getRowId(parameterName);
    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        this.callable_statement.setRowId(parameterName, x);
    }

    @Override
    public void setNString(String parameterName, String value) throws SQLException {
        this.callable_statement.setNString(parameterName, value );
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        this.callable_statement.setNCharacterStream( parameterName, value, length);
    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        this.callable_statement.setNClob(parameterName, value);
    }

    @Override
    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        this.callable_statement.setClob(parameterName, reader, length );
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        this.callable_statement.setBlob( parameterName, inputStream, length);
    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        this.callable_statement.setNClob(parameterName, reader, length);
    }

    @Override
    public NClob getNClob(int parameterIndex) throws SQLException {
        return         this.callable_statement.getNClob( parameterIndex );
    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        return         this.callable_statement.getNClob(parameterName);
    }

    @Override
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        this.callable_statement.setSQLXML(parameterName, xmlObject );
    }

    @Override
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return         this.callable_statement.getSQLXML( parameterIndex );
    }

    @Override
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return         this.callable_statement.getSQLXML( parameterName);
    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        return         this.callable_statement.getNString( parameterIndex );
    }

    @Override
    public String getNString(String parameterName) throws SQLException {
        return         this.callable_statement.getNString( parameterName );
    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return         this.callable_statement.getNCharacterStream(parameterIndex);
    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return         this.callable_statement.getNCharacterStream(parameterName);
    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return         this.callable_statement.getCharacterStream(parameterIndex);
    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        return         this.callable_statement.getCharacterStream(parameterName);
    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        this.callable_statement.setBlob(parameterName, x);
    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        this.callable_statement.setClob(parameterName, x);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        this.callable_statement.setAsciiStream( parameterName, x, length );
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        this.callable_statement.setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        this.callable_statement.setCharacterStream(parameterName,reader,length);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        this.callable_statement.setAsciiStream(parameterName, x);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        this.callable_statement.setBinaryStream(parameterName, x );
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        this.callable_statement.setCharacterStream(parameterName, reader);
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        this.callable_statement.setNCharacterStream(parameterName, value);
    }

    @Override
    public void setClob(String parameterName, Reader reader) throws SQLException {
        this.callable_statement.setClob(parameterName,reader);
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        this.callable_statement.setBlob( parameterName, inputStream);
    }

    @Override
    public void setNClob(String parameterName, Reader reader) throws SQLException {
        this.callable_statement.setNClob( parameterName, reader);
    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        return         this.callable_statement.getObject( parameterIndex, type);
    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        return         this.callable_statement.getObject(parameterName, type );
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return         this.callable_statement.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return         this.callable_statement.executeUpdate();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.callable_statement.setNull(parameterIndex, sqlType );
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.callable_statement.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.callable_statement.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        this.callable_statement.setShort( parameterIndex, x );
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        this.callable_statement.setInt(parameterIndex, x );
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        this.callable_statement.setLong( parameterIndex, x );
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.callable_statement.setFloat(parameterIndex, x );
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.callable_statement.setDouble(parameterIndex, x );
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        this.callable_statement.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        this.callable_statement.setString(parameterIndex,x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.callable_statement.setBytes(parameterIndex,x );
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.callable_statement.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.callable_statement.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        this.callable_statement.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.callable_statement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.callable_statement.setUnicodeStream(parameterIndex, x, length );
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.callable_statement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void clearParameters() throws SQLException {
        this.callable_statement.clearParameters();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        this.callable_statement.setObject(parameterIndex, x, targetSqlType );
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        this.callable_statement.setObject( parameterIndex, x );
    }

    @Override
    public boolean execute() throws SQLException {
        return         this.callable_statement.execute();
    }

    @Override
    public void addBatch() throws SQLException {
        this.callable_statement.addBatch();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        this.callable_statement.setCharacterStream(parameterIndex,reader,length);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        this.callable_statement.setRef(parameterIndex,x);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        this.callable_statement.setBlob(parameterIndex,x);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        this.callable_statement.setClob(parameterIndex,x);
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        this.callable_statement.setArray(parameterIndex,x);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return         this.callable_statement.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        this.callable_statement.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        this.callable_statement.setTime(parameterIndex,x,cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        this.callable_statement.setTimestamp(parameterIndex,x,cal);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.callable_statement.setNull(parameterIndex,sqlType,typeName);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.callable_statement.setURL(parameterIndex,x);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return         this.callable_statement.getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.callable_statement.setRowId(parameterIndex,x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        this.callable_statement.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        this.callable_statement.setNCharacterStream(parameterIndex,value,length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.callable_statement.setNClob(parameterIndex,value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.callable_statement.setClob(parameterIndex,reader,length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        this.callable_statement.setBlob(parameterIndex,inputStream,length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.callable_statement.setNClob(parameterIndex,reader,length);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        this.callable_statement.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        this.callable_statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength );
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.callable_statement.setAsciiStream(parameterIndex, x, length );
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.callable_statement.setBinaryStream(parameterIndex,x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        this.callable_statement.setCharacterStream(parameterIndex,reader,length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        this.callable_statement.setAsciiStream(parameterIndex,x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        this.callable_statement.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        this.callable_statement.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        this.callable_statement.setNCharacterStream(parameterIndex,value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.callable_statement.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        this.callable_statement.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.callable_statement.setNClob(parameterIndex,reader);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return         this.callable_statement.executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return         this.callable_statement.executeUpdate(sql);
    }

    @Override
    public void close() throws SQLException {
        this.callable_statement.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return         this.callable_statement.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        this.callable_statement.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return         this.callable_statement.getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        this.callable_statement.setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        this.callable_statement.setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return         this.callable_statement.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        this.callable_statement.setQueryTimeout(seconds);
    }

    @Override
    public void cancel() throws SQLException {
        this.callable_statement.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return         this.callable_statement.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.callable_statement.clearWarnings();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        this.callable_statement.setCursorName(name);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return         this.callable_statement.execute(sql);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return         this.callable_statement.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return         this.callable_statement.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return         this.callable_statement.getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        this.callable_statement.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return         this.callable_statement.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        this.callable_statement.setFetchDirection(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return         this.callable_statement.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return         this.callable_statement.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return         this.callable_statement.getResultSetType();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        this.callable_statement.addBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        this.callable_statement.clearBatch();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return         this.callable_statement.executeBatch();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return         this.callable_statement.getConnection();
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return         this.callable_statement.getMoreResults();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return         this.callable_statement.getGeneratedKeys();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return         this.callable_statement.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return         this.callable_statement.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return         this.callable_statement.executeUpdate(sql, columnNames);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return         this.callable_statement.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return         this.callable_statement.execute(sql,columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return         this.callable_statement.execute(sql, columnNames);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return         this.callable_statement.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return         this.callable_statement.isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        this.callable_statement.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return         this.callable_statement.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        this.callable_statement.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return         this.callable_statement.isCloseOnCompletion();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return         this.callable_statement.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return         this.callable_statement.isWrapperFor(iface);
    }
}
