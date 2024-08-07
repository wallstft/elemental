package com.wallstft.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wallstft.opa.OPAClient;
import com.wallstft.utils.OpaUtils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class opa_result_set implements ResultSet {

    private ResultSet result_set = null;
    private OPAClient opaClient ;
    private Properties properties;
    List<ObjectNode> columns = null;
    HashMap<String, Integer> name_to_index = null;

    public opa_result_set ( ResultSet rs, OPAClient client, Properties prop ) {
        this.result_set = rs;
        this.opaClient = client;
        this.properties = prop;

        try {
            ObjectMapper mapper = new ObjectMapper();
            columns = new ArrayList<>();
            name_to_index = new HashMap<>();
            ResultSetMetaData meta_data = rs.getMetaData();
            if (meta_data != null) {
                int column_count = meta_data.getColumnCount();
                for ( int i=1; i <=column_count; i ++ ) {
                    ObjectNode node = mapper.createObjectNode();
                    columns.add(node);
                    String name = null;
                    node.put( "column_name", name = meta_data.getColumnName(i));
                    node.put( "type", meta_data.getColumnTypeName(i));
                    node.put( "label", meta_data.getColumnLabel(i));
                    node.put( "table_name", meta_data.getTableName(i));
                    node.put( "schema_name", meta_data.getSchemaName(i));
                    node.put( "catalog", meta_data.getCatalogName(i));

                    name_to_index.put ( name, i );
                }
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();;
        }
    }

    @Override
    public boolean next() throws SQLException {
        return this.result_set.next();
    }

    @Override
    public void close() throws SQLException {
        this.result_set.close();
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.result_set.wasNull();
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        String s = this.result_set.getString(columnIndex);
        ObjectNode node = columns.get(columnIndex);
        if ( node != null ) {
            String column_name = OpaUtils.getString( node, "column_name");
            if ( column_name != null && column_name.equals("last_name")) {
                s = null;
            }
        }
        return s;
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return this.result_set.getBoolean(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return this.result_set.getByte(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return this.result_set.getShort(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return this.result_set.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return this.result_set.getLong(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return this.result_set.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return this.result_set.getDouble(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return this.result_set.getBigDecimal(columnIndex, scale);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return this.result_set.getBytes(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return this.result_set.getDate(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return this.result_set.getTime(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return this.result_set.getTimestamp(columnIndex);
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return this.result_set.getAsciiStream(columnIndex);
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return this.result_set.getUnicodeStream(columnIndex);
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return this.result_set.getBinaryStream(columnIndex);
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        String s = this.result_set.getString(columnLabel);
        if ( columnLabel != null && columnLabel.equals("last_name")) {
            s = null;
        }
        return s;
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return this.result_set.getBoolean(columnLabel);
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        return this.result_set.getByte(columnLabel);
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        return this.result_set.getShort(columnLabel);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        return this.result_set.getInt(columnLabel);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        return this.result_set.getLong(columnLabel);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return this.result_set.getFloat(columnLabel);
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return this.result_set.getDouble(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return this.result_set.getBigDecimal(columnLabel, scale);
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        return this.result_set.getBytes(columnLabel);
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        return this.result_set.getDate(columnLabel);
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        return this.result_set.getTime(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return this.result_set.getTimestamp(columnLabel);
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return this.result_set.getAsciiStream(columnLabel);
    }

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return this.result_set.getUnicodeStream(columnLabel);
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return this.result_set.getBinaryStream(columnLabel);
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return this.result_set.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.result_set.clearWarnings();
    }

    @Override
    public String getCursorName() throws SQLException {
        return this.result_set.getCursorName();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.result_set.getMetaData();
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return this.result_set.getObject(columnIndex);
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return this.result_set.getObject(columnLabel);
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return this.result_set.findColumn(columnLabel);
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        return this.result_set.getCharacterStream(columnIndex);
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        return this.result_set.getCharacterStream(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return this.result_set.getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return this.result_set.getBigDecimal(columnLabel);
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        return this.result_set.isBeforeFirst();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return this.result_set.isAfterLast();
    }

    @Override
    public boolean isFirst() throws SQLException {
        return this.result_set.isFirst();
    }

    @Override
    public boolean isLast() throws SQLException {
        return this.result_set.isLast();
    }

    @Override
    public void beforeFirst() throws SQLException {
        this.result_set.beforeFirst();
    }

    @Override
    public void afterLast() throws SQLException {
        this.result_set.afterLast();
    }

    @Override
    public boolean first() throws SQLException {
        return this.result_set.first();
    }

    @Override
    public boolean last() throws SQLException {
        return this.result_set.last();
    }

    @Override
    public int getRow() throws SQLException {
        return this.result_set.getRow();
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        return this.result_set.absolute(row);
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        return this.result_set.relative(rows);
    }

    @Override
    public boolean previous() throws SQLException {
        return this.result_set.previous();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        this.result_set.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return this.result_set.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        this.result_set.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return this.result_set.getFetchSize();
    }

    @Override
    public int getType() throws SQLException {
        return this.result_set.getType();
    }

    @Override
    public int getConcurrency() throws SQLException {
        return this.result_set.getConcurrency();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return this.result_set.rowUpdated();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return this.result_set.rowInserted();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return this.result_set.rowDeleted();
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        this.result_set.updateNull(columnIndex);
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        this.result_set.updateBoolean(columnIndex,x);
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        this.result_set.updateByte(columnIndex, x);
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        this.result_set.updateShort(columnIndex,x);
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        this.result_set.updateInt(columnIndex,x);
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        this.result_set.updateLong(columnIndex,x);
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        this.result_set.updateFloat( columnIndex, x );
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        this.result_set.updateDouble( columnIndex, x );
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        this.result_set.updateBigDecimal(columnIndex, x);
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        this.result_set.updateString(columnIndex, x);
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        this.result_set.updateBytes(columnIndex, x);
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        this.result_set.updateDate(columnIndex, x);
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        this.result_set.updateTime(columnIndex, x );
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        this.result_set.updateTimestamp( columnIndex, x );
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        this.result_set.updateAsciiStream( columnIndex, x, length );
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        this.result_set.updateBinaryStream(columnIndex, x, length );
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        this.result_set.updateCharacterStream( columnIndex, x, length );
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        this.result_set.updateObject( columnIndex, x, scaleOrLength );
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        this.result_set.updateObject( columnIndex, x );
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        this.result_set.updateNull( columnLabel );
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        this.result_set.updateBoolean( columnLabel, x );
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        this.result_set.updateByte(columnLabel, x);
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        this.result_set.updateShort( columnLabel, x );
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        this.result_set.updateInt(columnLabel, x);
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        this.result_set.updateLong(columnLabel, x );
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        this.result_set.updateFloat( columnLabel, x );
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        this.result_set.updateDouble( columnLabel, x );
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        this.result_set.updateBigDecimal( columnLabel, x );
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        this.result_set.updateString ( columnLabel, x );
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        this.result_set.updateBytes( columnLabel, x );
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        this.result_set.updateDate(columnLabel, x );
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        this.result_set.updateTime(columnLabel, x );
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        this.result_set.updateTimestamp( columnLabel, x );
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        this.result_set.updateAsciiStream( columnLabel, x, length );
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        this.result_set.updateBinaryStream(columnLabel, x, length );
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        this.result_set.updateCharacterStream( columnLabel, reader, length );
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        this.result_set.updateObject( columnLabel, x, scaleOrLength );
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        this.result_set.updateObject( columnLabel, x );
    }

    @Override
    public void insertRow() throws SQLException {
        this.result_set.insertRow();
    }

    @Override
    public void updateRow() throws SQLException {
        this.result_set.updateRow();
    }

    @Override
    public void deleteRow() throws SQLException {
        this.result_set.deleteRow();
    }

    @Override
    public void refreshRow() throws SQLException {
        this.result_set.refreshRow();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        this.result_set.cancelRowUpdates();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        this.result_set.moveToInsertRow();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        this.result_set.moveToCurrentRow();
    }

    @Override
    public Statement getStatement() throws SQLException {
        return this.result_set.getStatement();
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return this.result_set.getObject( columnIndex, map);
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        return this.result_set.getRef(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return this.result_set.getBlob(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return this.result_set.getClob(columnIndex);
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        return this.result_set.getArray(columnIndex);
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return this.result_set.getObject(columnLabel, map);
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        return this.result_set.getRef(columnLabel);
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        return this.result_set.getBlob(columnLabel);
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        return this.result_set.getClob(columnLabel);
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        return this.result_set.getArray(columnLabel);
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return this.result_set.getDate(columnIndex, cal);
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return this.result_set.getDate(columnLabel, cal);
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return this.result_set.getTime(columnIndex, cal);
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return this.result_set.getTime(columnLabel, cal);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return this.result_set.getTimestamp(columnIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        return this.result_set.getTimestamp(columnLabel, cal);
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        return this.result_set.getURL(columnIndex);
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        return this.result_set.getURL(columnLabel);
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        this.result_set.updateRef(columnIndex,x );
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        this.result_set.updateRef(columnLabel, x);
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        this.result_set.updateBlob(columnIndex,x);
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        this.result_set.updateBlob(columnLabel, x );
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        this.result_set.updateClob(columnIndex, x);
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        this.result_set.updateClob(columnLabel, x);
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        this.result_set.updateArray(columnIndex,x);
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        this.result_set.updateArray(columnLabel, x);
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        return this.result_set.getRowId(columnIndex);
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        return this.result_set.getRowId(columnLabel);
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        this.result_set.updateRowId(columnIndex, x);
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        this.result_set.updateRowId(columnLabel, x);
    }

    @Override
    public int getHoldability() throws SQLException {
        return this.result_set.getHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.result_set.isClosed();
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        this.result_set.updateNString(columnIndex, nString );
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        this.result_set.updateNString( columnLabel, nString );
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        this.result_set.updateNClob( columnIndex, nClob);
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        this.result_set.updateNClob( columnLabel, nClob );
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        return this.result_set.getNClob(columnIndex);
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        return this.result_set.getNClob(columnLabel);
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return this.result_set.getSQLXML(columnIndex);
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return this.result_set.getSQLXML( columnLabel );
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        this.result_set.updateSQLXML(columnIndex, xmlObject );
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        this.result_set.updateSQLXML(columnLabel, xmlObject);
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return this.result_set.getNString(columnIndex);
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        return this.result_set.getNString(columnLabel);
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return this.result_set.getNCharacterStream(columnIndex);
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return this.result_set.getNCharacterStream(columnLabel);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.result_set.updateNCharacterStream(columnIndex, x, length );
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.result_set.updateNCharacterStream( columnLabel, reader, length );
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.result_set.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.result_set.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.result_set.updateCharacterStream( columnIndex, x, length );
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.result_set.updateAsciiStream( columnLabel, x, length );
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.result_set.updateBinaryStream(columnLabel, x, length );
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.result_set.updateCharacterStream( columnLabel, reader, length );
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        this.result_set.updateBlob( columnIndex, inputStream, length );
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        this.result_set.updateBlob(columnLabel, inputStream, length);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.result_set.updateClob( columnIndex, reader, length );
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.result_set.updateClob(columnLabel, reader, length );
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.result_set.updateNClob(columnIndex, reader, length );
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.result_set.updateNClob(columnLabel, reader, length );
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.result_set.updateNCharacterStream( columnIndex, x );
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.result_set.updateNCharacterStream( columnLabel, reader );
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        this.result_set.updateAsciiStream( columnIndex, x );
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        this.result_set.updateBinaryStream(columnIndex, x );
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.result_set.updateCharacterStream(columnIndex, x );
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        this.result_set.updateAsciiStream(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        this.result_set.updateBinaryStream(columnLabel, x );
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.result_set.updateCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        this.result_set.updateBlob( columnIndex, inputStream );
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        this.result_set.updateBlob( columnLabel, inputStream );
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        this.result_set.updateClob(columnIndex, reader);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        this.result_set.updateClob(columnLabel, reader);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        this.result_set.updateNClob(columnIndex,reader);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        this.result_set.updateNClob(columnLabel, reader);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return this.result_set.getObject( columnIndex, type );
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return this.result_set.getObject(columnLabel, type);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.result_set.unwrap( iface );
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.result_set.isWrapperFor( iface );
    }
}
