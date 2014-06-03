package net.sf.jeasyorm;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class Utils {
    
    public static String join(String[] arr, String sep) {
        StringBuffer sb = new StringBuffer();
        for (String s : arr) {
            if (sb.length() > 0) sb.append(sep);
            sb.append(s);
        }
        return sb.toString();
    }
    
    /**
     * Returns true if the provided class is a type supported natively (as
     * opposed to a bean).
     * 
     * @param type {@link java.lang.Class} type to be tested
     * @since 1.0
     */
    public static boolean isNativeType(final Class<?> type) {
        return type == boolean.class || type == Boolean.class || 
               type == byte.class || type == Byte.class || 
               type == short.class || type == Short.class || 
               type == int.class || type == Integer.class || 
               type == long.class || type == Long.class || 
               type == float.class || type == Float.class || 
               type == double.class || type == Double.class || 
               type == char.class || type == Character.class || 
               type == byte[].class ||  
               type == char[].class ||  
               type == String.class || 
               type == BigDecimal.class || 
               type == java.util.Date.class || type == java.sql.Date.class || 
               type == java.sql.Time.class || type == java.sql.Timestamp.class || 
               java.sql.Clob.class.isAssignableFrom(type) || 
               java.sql.Blob.class.isAssignableFrom(type) || 
               type == Object.class;
    }
    
    public static Object convertTo(Object value, final Class<?> type) {
        if (value == null || value.getClass() == type) {
            return value;
        } else if (value instanceof Number && Number.class.isAssignableFrom(type)) {
            Number n = (Number) value;
            if (type == Byte.class) {
                return n.byteValue();
            } else if (type == Short.class) {
                return n.shortValue();
            } else if (type == Integer.class) {
                return n.intValue();
            } else if (type == Long.class) {
                return n.longValue();
            } else if (type == Float.class) {
                return n.floatValue();
            } else if (type == Double.class) {
                return n.doubleValue();
            }
        } else if (value instanceof java.util.Date && java.util.Date.class.isAssignableFrom(type)) {
            java.util.Date d = (java.util.Date) value;
            if (type == java.util.Date.class) {
                return new java.util.Date(d.getTime());
            } else if (type == java.sql.Date.class) {
                return new java.sql.Date(d.getTime());
            } else if (type == java.sql.Time.class) {
                return new java.sql.Time(d.getTime());
            } else if (type == java.sql.Timestamp.class) {
                return new java.sql.Timestamp(d.getTime());
            }
        }
        return value;
    }

    public static int executeScript(Connection connection, BufferedReader reader, boolean ignoreException) 
            throws IOException, SQLException {
        int num = 0;
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!"".equals(line)) {
                if (sb.length() > 0) sb.append(" ");
                if (line.endsWith(";")) {
                    sb.append(line.substring(0, line.length()-1));
                    if (!ignoreException) {
                        num += connection.createStatement().execute(sb.toString()) ? 1 : 0;
                    } else {
                        try {
                            num += connection.createStatement().execute(sb.toString()) ? 1 : 0;
                        } catch (SQLException e) {
                            e = e;
                        }
                    }
                    sb.delete(0, sb.length());
                } else {
                    sb.append(line);
                }
            }
        }
        return num;
    }

}
