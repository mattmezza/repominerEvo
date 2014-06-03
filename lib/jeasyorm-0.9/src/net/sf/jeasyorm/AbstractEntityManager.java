package net.sf.jeasyorm;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import net.sf.jeasyorm.Mapping.FieldInfo;

public abstract class AbstractEntityManager extends EntityManager {

    private SqlTranslator sqlTranslator;
    
    protected AbstractEntityManager(Connection connection) {
        super(connection);
    }
    
    public void setSqlTranslator(SqlTranslator sqlTranslator) {
        this.sqlTranslator = sqlTranslator;
    }
    
    protected PreparedStatement prepareStatement(String sql, String[] columnNames) {
        try {
            String finalSql = sqlTranslator != null ? sqlTranslator.getTranslatedSql(sql) : sql;
            if (columnNames == null || columnNames.length <= 0) {
                return getConnection().prepareStatement(finalSql);
            } else {
                return getConnection().prepareStatement(finalSql, columnNames);
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException("Error preparing statement [" + sql + "]", e);
        } 
    }
    
    protected void close(ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (Exception e) { /* ignore */ }
        }
    }
    
    protected void close(PreparedStatement stmt) {
        if (stmt != null) {
            try { stmt.close(); } catch (Exception e) { /* ignore */ }
        }
    }
    
    protected Object getObject(ResultSet rs, Mapping mapping) {
        Object o;
        try {
            o = mapping.getConstructor() != null ? mapping.getConstructor().newInstance(this) : 
                    mapping.getEntityClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeSQLException("Can't create object of type '" + mapping.getEntityClass().getName() + "'", e);
        }
        try {
            ResultSetMetaData metadata = rs.getMetaData();
            for (int index=1; index<=metadata.getColumnCount(); index++) {
                int sqlType = metadata.getColumnType(index);
                String columnName = metadata.getColumnName(index);
                FieldInfo fi = mapping.getFieldForColumn(columnName);
                if (fi == null) {
                    columnName = metadata.getColumnLabel(index);
                    fi = mapping.getFieldForColumn(columnName);
                }
                if (fi == null) {
                    if (columnName.contains("__")) continue; // ignore 
                    throw new RuntimeSQLException("Can't find field for column '" + columnName + "'");
                }
                try {
                    Object v = getValue(rs, index, fi.getType(), sqlType);
                    set(o, fi, v);
                } catch (RuntimeSQLException e) {
                    throw new RuntimeSQLException("Can't read column '" + columnName + "'", e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
        return o;
    }
    
    protected Object get(Object o, FieldInfo fi) {
        try {
            if (fi.getGetter() != null) {
                return fi.getGetter().invoke(o);
            } else if (fi.getField() != null) {
                return fi.getField().get(o);
            } else {
                throw new RuntimeSQLException("Neither field nor getter found for field '" + fi.getName() + "'");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeSQLException("Error getting field '" + fi.getName() + "'");
        } catch (IllegalAccessException e) {
            throw new RuntimeSQLException("Error getting field '" + fi.getName() + "'");
        } catch (InvocationTargetException e) {
            throw new RuntimeSQLException("Error getting field '" + fi.getName() + "'");
        }
    }
    
    protected void set(Object o, FieldInfo fi, Object v) {
        try {
            if (fi.getSetter() != null) {
                fi.getSetter().invoke(o, v);
            } else if (fi.getField() != null) {
                fi.getField().set(o, v);
            } else {
                throw new RuntimeSQLException("Neither field nor setter found for field '" + fi.getName() + "'");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeSQLException("Error setting field '" + fi.getName() + "'");
        } catch (IllegalAccessException e) {
            throw new RuntimeSQLException("Error setting field '" + fi.getName() + "'");
        } catch (InvocationTargetException e) {
            throw new RuntimeSQLException("Error setting field '" + fi.getName() + "'");
        }
    }
    
    protected void setParameter(PreparedStatement stmt, int index, Object value, int sqlType) {
        try {
            if (value == null) {
                stmt.setNull(index, sqlType);
            } else if (value instanceof Boolean) {
                stmt.setBoolean(index, (Boolean) value);
            } else if (value instanceof Byte) {
                stmt.setByte(index, (Byte) value);
            } else if (value instanceof Short) {
                stmt.setShort(index, (Short) value);
            } else if (value instanceof Integer) {
                stmt.setInt(index, (Integer) value);
            } else if (value instanceof Long) {
                stmt.setLong(index, (Long) value);
            } else if (value instanceof Float) {
                stmt.setFloat(index, (Float) value);
            } else if (value instanceof Double) {
                stmt.setDouble(index, (Double) value);
            } else if (value instanceof byte[]) {
                stmt.setBytes(index, (byte[]) value);
            } else if (value instanceof char[]) {
                stmt.setString(index, new String((char[]) value));
            } else if (value instanceof String) {
                stmt.setString(index, (String) value);
            } else if (value instanceof BigDecimal) {
                stmt.setBigDecimal(index, (BigDecimal) value);
            } else if (value instanceof java.util.Date) {
                stmt.setTimestamp(index, new java.sql.Timestamp(((java.util.Date) value).getTime()));
            } else if (value instanceof java.sql.Date) {
                stmt.setDate(index, (java.sql.Date) value);
            } else if (value instanceof java.sql.Time) {
                stmt.setTime(index, (java.sql.Time) value);
            } else if (value instanceof java.sql.Timestamp) {
                stmt.setTimestamp(index, (java.sql.Timestamp) value);
            } else if (value instanceof java.sql.Clob) {
                stmt.setClob(index, (java.sql.Clob) value);
            } else if (value instanceof java.sql.Blob) {
                stmt.setBlob(index, (java.sql.Blob) value);
            } else {
                throw new RuntimeSQLException("Unsupported type '" + value.getClass().getName() + "'");
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }

    protected Object getValue(ResultSet rs, int index, Class<?> type, int sqlType) {
        try {
            if (type == Boolean.class) {
                return rs.getBoolean(index);
            } else if (type == Byte.class) {
                return rs.getByte(index);
            } else if (type == Short.class) {
                return rs.getShort(index);
            } else if (type == Integer.class) {
                return rs.getInt(index);
            } else if (type == Long.class) {
                return rs.getLong(index);
            } else if (type == Float.class) {
                return rs.getFloat(index);
            } else if (type == Double.class) {
                return rs.getDouble(index);
            } else if (type == byte[].class) {
                return rs.getBytes(index);
            } else if (type == char[].class) {
                return rs.getString(index).toCharArray();
            } else if (type == String.class) {
                return rs.getString(index);
            } else if (type == BigDecimal.class) {
                return rs.getBigDecimal(index);
            } else if (type == java.util.Date.class) {
                return new java.util.Date(rs.getTimestamp(index).getTime());
            } else if (type == java.sql.Date.class) {
                return rs.getDate(index);
            } else if (type == java.sql.Time.class) {
                return rs.getTime(index);
            } else if (type == java.sql.Timestamp.class) {
                return rs.getTimestamp(index);
            } else if (type == java.sql.Clob.class) {
                return rs.getClob(index);
            } else if (type == java.sql.Blob.class) {
                return rs.getBlob(index);
            } else {
                throw new RuntimeSQLException("Unsupported type '" + type.getName() + "'");
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }
 
}
