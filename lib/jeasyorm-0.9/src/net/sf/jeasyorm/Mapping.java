package net.sf.jeasyorm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;
import net.sf.jeasyorm.annotation.Transient;

public class Mapping {

    private static Map<String, Mapping> mappingsByClassName = new HashMap<String, Mapping>();
    
    protected Class<?> entityClass;
    protected Constructor<?> constructor;
    protected Map<String, FieldInfo> fieldsByName = new HashMap<String, FieldInfo>();
    protected Map<String, FieldInfo> fieldsByLcColumnName = new HashMap<String, FieldInfo>();

    protected String catalogName;
    protected String schemaName;
    protected String tableName;
    protected List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
    protected Map<String, ColumnInfo> columnsByLcColumnName = new HashMap<String, ColumnInfo>();
    
    public static Mapping getMapping(EntityManager manager, Class<?> entityClass) {
        String key = (manager.getCache() != null ? manager.getCache() + ":" : "") + entityClass.getName();
        Mapping mapping = mappingsByClassName.get(key);
        if (mapping == null && !mappingsByClassName.containsKey(key)) {
            try {
                mapping = manager.newMapping(entityClass);
                mappingsByClassName.put(key, mapping);
            } catch (SQLException e) {
                mappingsByClassName.put(key, null);
            }
        }
        return mapping;
    }
    
    public Mapping(EntityManager manager, Class<?> entityClass) throws SQLException {
        this.entityClass = entityClass;
        NameGuesser nameGuesser = manager.getNameGuesser();
        // transient fields
        Set<String> transientFieldNames = new HashSet<String>(); 
        // column names as specified with @Column annotation
        Map<String, String> columnNamesByFieldName = new HashMap<String, String>();
        // get fields
        for (Field field : entityClass.getDeclaredFields()) {
            Transient t = field.getAnnotation(Transient.class); 
            if (t != null) {
                transientFieldNames.add(field.getName());
            } else if (isAccessible(field)) {
                this.fieldsByName.put(field.getName(), new FieldInfo(field.getName(), field.getType(), field, null, null));
            }
            Column c = field.getAnnotation(Column.class);
            if (c != null && !"".equals(c.name())) columnNamesByFieldName.put(field.getName(), c.name());
        }
        // get methods (getters and setters)
        for (Method m : entityClass.getDeclaredMethods()) {
            String name = m.getName();
            boolean getter = false;
            if (name.length() > 3 && ((getter = name.startsWith("get")) || name.startsWith("set")) && 
                    Character.isUpperCase(name.charAt(3))) {
                name = name.substring(3,4).toLowerCase() + name.substring(4);
                Transient t = m.getAnnotation(Transient.class);
                if (t != null) {
                    transientFieldNames.add(name);
                    this.fieldsByName.remove(name);
                } else if (!transientFieldNames.contains(name) && isAccessible(m)) {
                    if (!this.fieldsByName.containsKey(name)) {
                        this.fieldsByName.put(name, new FieldInfo(name, m.getReturnType(), null, 
                                getter ? m : null, getter ? null : m));
                    } else if (getter) {
                        this.fieldsByName.get(name).getter = m;
                    } else {
                        this.fieldsByName.get(name).setter = m;
                    }
                }
                Column c = m.getAnnotation(Column.class);
                if (c != null && !"".equals(c.name())) columnNamesByFieldName.put(name, c.name());
            }
        }
        try {
            constructor = entityClass.getConstructor(EntityManager.class);
        } catch (Exception e) {
            // ignore
        }
        for (FieldInfo fi : this.fieldsByName.values()) {
            if (columnNamesByFieldName.get(fi.name) != null) {
                this.fieldsByLcColumnName.put(columnNamesByFieldName.get(fi.name).toLowerCase(), fi);
            } else {
                for (String columnName : nameGuesser.guessColumnName(entityClass, fi.name)) {
                    this.fieldsByLcColumnName.put(columnName.toLowerCase(), fi);
                }
            }
        }
        Transient t = entityClass.getAnnotation(Transient.class);
        if (t == null) {
            String schemaName = "%";
            String[] tableNames;
            Table ta = entityClass.getAnnotation(Table.class);
            if (ta != null && !"".equals(ta.schema())) {
                schemaName = ta.schema();
            }
            if (ta != null && !"".equals(ta.name())) {
                tableNames = new String[] { ta.name() };
            } else {
                tableNames = nameGuesser.guessTableName(entityClass);
            }
            DatabaseMetaData metadata = manager.getConnection().getMetaData();
            String[] name = getTableName(metadata, schemaName, tableNames);
            this.catalogName = name[0];
            this.schemaName = name[1];
            this.tableName = name[2];
            if (this.tableName == null) {
                throw new RuntimeException("Class '" + entityClass.getName() + "': Table '" + 
                        Utils.join(tableNames, "'/'") + "' does not exist!");
            }
            ResultSet rs = metadata.getColumns(this.catalogName, this.schemaName, this.tableName, null);
            int numColumns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String columnName = rs.getString(4);
                int columnType = rs.getInt(5);
                String nullable = rs.getString(18);
                String autoIncrement = numColumns >= 23 ? rs.getString(23) : null;
                ColumnInfo column = new ColumnInfo(columnName, columnType, false,
                        nullable == null || "YES".equals(nullable), "YES".equals(autoIncrement));
                this.columns.add(column);
                this.columnsByLcColumnName.put(columnName.toLowerCase(), column);
                if (fieldsByLcColumnName.get(columnName.toLowerCase()) == null) {
                    throw new RuntimeException("Class '" + entityClass.getName() + 
                            "': No field for column '" + this.tableName + "." + columnName + "'!");
                }
            }
            rs.close();
            rs = metadata.getPrimaryKeys(this.catalogName, this.schemaName, this.tableName);
            while (rs.next()) {
                String columnName = rs.getString(4);
                for (ColumnInfo ci : this.columns) {
                    if (columnName.equals(ci.name)) ci.primaryKey = true;
                }
            }
            rs.close();
        }
    }

    protected String[] getTableName(DatabaseMetaData metadata, String schemaName, String tableNames[]) throws SQLException {
        String[] name = null;
        for (String tableName : tableNames) {
            ResultSet rs = metadata.getTables(null, schemaName, tableName.toLowerCase(), null);
            if (rs.next()) name = new String[] { rs.getString(1), rs.getString(2), rs.getString(3) };
            rs.close();
            if (name != null) break;
            rs = metadata.getTables(null, schemaName, tableName.toUpperCase(), null);
            if (rs.next()) name = new String[] { rs.getString(1), rs.getString(2), rs.getString(3) };
            rs.close();
            if (name != null) break;
            rs = metadata.getTables(null, schemaName, "%", null);
            while (rs.next()) {
                String dbTableName = rs.getString(3);
                if (tableName.equalsIgnoreCase(dbTableName)) {
                    name = new String[] { rs.getString(1), rs.getString(2), dbTableName };
                    break;
                }
            }
            rs.close();
        }
        return name;
    }
    
    protected boolean isAccessible(Field field) {
        if ((field.getModifiers() & Modifier.PUBLIC) > 0) return true;
        try {
            field.setAccessible(true);
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }
    
    protected boolean isAccessible(Method m) {
        if ((m.getModifiers() & Modifier.PUBLIC) > 0) return true;
        try {
            m.setAccessible(true);
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }
    
    public Class<?> getEntityClass() { return entityClass; }
    public Constructor<?> getConstructor() { return constructor; }
    
    public String getCatalogName() { return catalogName; }
    public String getSchemaName() { return schemaName; }
    public String getTableName() { return tableName; }
    
    public Collection<FieldInfo> getFields() { return fieldsByName.values(); }
    public FieldInfo getFieldForColumn(String columnName) { 
        return fieldsByLcColumnName.get(columnName.toLowerCase()); 
    }
    public FieldInfo getFieldForColumn(ColumnInfo column) {
        return fieldsByLcColumnName.get(column.name.toLowerCase());
    }
    public List<ColumnInfo> getColumns() { return columns; }
    public ColumnInfo getColumnForColumn(String columnName) {
        return columnsByLcColumnName.get(columnName.toLowerCase());
    }
    
    
    public static class FieldInfo {
        
        protected String name;
        protected Class<?> type;
        
        protected Field field;
        protected Method getter;
        protected Method setter;
        
        protected FieldInfo(String name, Class<?> type, Field field, Method getter, Method setter) { 
            this.name = name;
            this.type = type;
            this.field = field;
            this.getter = getter;
            this.setter = setter;
        }
        
        public String getName() { return name; }
        public Class<?> getType() { return type; }
        public Field getField() { return field; }
        public Method getGetter() { return getter; }
        public Method getSetter() { return setter; }

    }
    
    
    public static class ColumnInfo {

        protected String name;
        protected int type;
        protected boolean primaryKey;
        protected boolean nullable;
        protected boolean autoIncrement; 
        
        protected ColumnInfo(String name, int type, boolean primaryKey, boolean nullable, boolean autoIncrement) {
            this.name = name;
            this.type = type;
            this.nullable = nullable;
            this.autoIncrement = autoIncrement;
        }
        
        public String getName() { return name; }
        public int getType() { return type; }
        public boolean isPrimaryKey() { return primaryKey; }
        public boolean isAutoIncrement() { return autoIncrement; }
        public boolean isNullable() { return nullable; }

    }
    
}
