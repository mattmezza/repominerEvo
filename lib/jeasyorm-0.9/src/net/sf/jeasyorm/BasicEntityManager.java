package net.sf.jeasyorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jeasyorm.Mapping.ColumnInfo;
import net.sf.jeasyorm.Mapping.FieldInfo;

public class BasicEntityManager extends AbstractEntityManager {

    public BasicEntityManager(Connection connection) {
        super(connection);
    }
    
    protected SqlInfo newSqlInfo(Class<?> entityClass) {
        Mapping mapping = Mapping.getMapping(this, entityClass);
        SqlInfo info = new SqlInfo(mapping);
        StringBuffer selectColumns = new StringBuffer();
        StringBuffer insertColumns = new StringBuffer();
        StringBuffer insertPlaceholders = new StringBuffer();
        StringBuffer updateSet = new StringBuffer();
        StringBuffer where = new StringBuffer();
        for (ColumnInfo ci : mapping.getColumns()) {
            if (selectColumns.length() > 0) selectColumns.append(", ");
            selectColumns.append(ci.getName());
            if (!ci.isAutoIncrement()) {
                if (insertColumns.length() > 0) insertColumns.append(", ");
                insertColumns.append(ci.getName());
                if (insertPlaceholders.length() > 0) insertPlaceholders.append(", ");
                insertPlaceholders.append("?");
            }
            if (ci.isPrimaryKey()) {
                if (where.length() > 0) where.append(" and ");
                where.append(ci.getName());
                where.append(" = ?");
            } else {
                if (updateSet.length() > 0) updateSet.append(", ");
                updateSet.append(ci.getName());
                updateSet.append(" = ?");
            }
        }
        String tableName = (mapping.getSchemaName() != null ? mapping.getSchemaName() + "." : "") + 
                mapping.getTableName();
        info.loadSql = "select " + selectColumns + " from " + tableName + " where " + where;
        info.selectSql = "select " + selectColumns + " from " + tableName;
        info.insertSql = "insert into " + tableName + " ("+ insertColumns + ") values (" + insertPlaceholders + ")";
        info.updateSql = "update " + tableName + " set "+ updateSet + " where " + where;
        info.deleteSql = "delete from " + tableName + " where " + where;
        return info;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T load(Class<T> entityClass, Object... pk) {
        SqlInfo info = getSqlInfo(this, entityClass);
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement(info.loadSql, null);
            int i=0;
            for (ColumnInfo ci : info.mapping.getColumns()) {
                if (ci.isPrimaryKey()) {
                    setParameter(stmt, i+1, pk[i], ci.getType());
                    i++;
                }
            }
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery();
                T entity = null;
                if (rs.next()) entity = (T) getObject(rs, info.mapping);
                if (rs.next()) {
                    throw new RuntimeSQLException("Multiple rows returned for statement [" + info.loadSql + "]");
                }
                return entity;
            } catch (RuntimeSQLException se) {
                throw se;
            } catch (Exception e) {
                throw new RuntimeSQLException("Error executing statement [" + info.loadSql + "]", e);
            } finally {
                close(rs);
            }
        } finally {
            close(stmt);
        }
    }
    
    protected String getSql(SqlInfo info, String query) {
        String lcQuery = query.trim().toLowerCase();
        if (lcQuery.startsWith("from")) {
            int pos = info.selectSql.indexOf(" from ");
            return info.selectSql.substring(0, pos+1) + query;
        } else if (lcQuery.startsWith("where") || lcQuery.startsWith("order")) {
            return info.selectSql + " " + query;
        } else {
            return query;
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> T findUnique(Class<T> entityClass, String query, Object... params) {
        boolean isNative = Utils.isNativeType(entityClass);
        SqlInfo info = isNative ? null : getSqlInfo(this, entityClass);
        String sql = isNative ? query : getSql(info, query);
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement(sql, null);
            for (int i=0; i<params.length; i++) {
                setParameter(stmt, i+1, params[i], Types.VARCHAR);
            }
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery();
                T entity = null;
                if (rs.next()) {
                    entity = !isNative ? (T) getObject(rs, info.mapping) : (T) getValue(rs, 1, entityClass, Types.VARCHAR);
                }
                if (rs.next()) {
                    throw new RuntimeSQLException("Multiple rows returned for statement [" + sql + "]");
                }
                return entity;
            } catch (RuntimeSQLException se) {
                throw se;
            } catch (Exception e) {
                throw new RuntimeSQLException("Error executing statement [" + sql + "]", e);
            } finally {
                close(rs);
            }
        } finally {
            close(stmt);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> entityClass, String query, Object... params) {
        boolean isNative = Utils.isNativeType(entityClass);
        SqlInfo info = isNative ? null : getSqlInfo(this, entityClass);
        String sql = isNative ? query : getSql(info, query);
        List<T> entities = new ArrayList<T>();
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement(sql, null);
            for (int i=0; i<params.length; i++) {
                setParameter(stmt, i+1, params[i], Types.VARCHAR);
            }
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery();
                while (rs.next()) {
                    T entity = !isNative ? (T) getObject(rs, info.mapping) : (T) getValue(rs, 1, entityClass, Types.VARCHAR);
                    entities.add(entity);
                }
                return entities;
            } catch (RuntimeSQLException se) {
                throw se;
            } catch (Exception e) {
                throw new RuntimeSQLException("Error executing statement [" + sql + "]", e);
            } finally {
                close(rs);
            }
        } finally {
            close(stmt);
        }
    }
    
    public int count(String sql, Object... params) {
        PreparedStatement stmt = null;
        try {
            int pos1 = sql.indexOf(" from ");
            int pos2 = sql.lastIndexOf(" order by ");
            stmt = prepareStatement("select count(*)" + sql.substring(pos1, pos2 > pos1 ? pos2 : sql.length()), null);
            for (int i=0; i<params.length; i++) {
                setParameter(stmt, i+1, params[i], Types.VARCHAR);
            }
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery();
                if (rs.next()) return rs.getInt(1);
            } catch (RuntimeSQLException se) {
                throw se;
            } catch (Exception e) {
                throw new RuntimeSQLException("Error executing statement [" + sql + "]", e);
            } finally {
                close(rs);
            }
        } finally {
            close(stmt);
        }
        return 0;
    }
    
    @SuppressWarnings("unchecked")
    public <T> Page<T> find(Class<T> entityClass, int pageNum, int pageSize, String query, Object... params) {
        boolean isNative = Utils.isNativeType(entityClass);
        SqlInfo info = isNative ? null : getSqlInfo(this, entityClass);
        String sql = isNative ? query : getSql(info, query);
        List<T> entities = new ArrayList<T>();
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement(sql + " limit " + pageSize + " offset " + (pageNum*pageSize), null);
            for (int i=0; i<params.length; i++) {
                setParameter(stmt, i+1, params[i], Types.VARCHAR);
            }
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery();
                while (rs.next()) {
                    T entity = !isNative ? (T) getObject(rs, info.mapping) : (T) getValue(rs, 1, entityClass, Types.VARCHAR);
                    entities.add(entity);
                }
                int totalSize = (pageNum*pageSize) + entities.size();
                if (entities.size() >= pageSize) {
                    totalSize = Math.max(totalSize, count(sql, params));
                }
                return new Page<T>(entities, pageNum, pageSize, totalSize);
            } catch (RuntimeSQLException se) {
                throw se;
            } catch (Exception e) {
                throw new RuntimeSQLException("Error executing statement [" + sql + "]", e);
            } finally {
                close(rs);
            }
        } finally {
            close(stmt);
        }
    }
    
    public <T> Iterator<T> iterator(Class<T> entityClass, String query, Object... params) {
        SqlInfo info = getSqlInfo(this, entityClass);
        String sql = getSql(info, query);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement(sql, null);
            for (int i=0; i<params.length; i++) {
                setParameter(stmt, i+1, params[i], Types.VARCHAR);
            }
            rs = stmt.executeQuery();
            return new ResultSetIterator<T>(stmt, rs, entityClass);
        } catch (RuntimeSQLException se) {
            close(rs);
            close(stmt);
            throw se;
        } catch (Exception e) {
            close(rs);
            close(stmt);
            throw new RuntimeSQLException("Error executing statement [" + sql + "]", e);
        }
    }
    
    public int execute(String sql, Object... params) {
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement(sql, null);
            for (int i=0; i<params.length; i++) {
                setParameter(stmt, i+1, params[i], Types.VARCHAR);
            }
            return stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeSQLException("Error executing statement [" + sql + "]", e);
        } finally {
            close(stmt);
        }
    }

    public <T> T insert(T entity) {
        SqlInfo info = getSqlInfo(this, entity.getClass());
        PreparedStatement stmt = null;
        try {
            List<String> columnNames = new ArrayList<String>();
            for (ColumnInfo ci : info.mapping.getColumns()) {
                if (ci.isAutoIncrement()) columnNames.add(ci.getName());
            }
            stmt = prepareStatement(info.insertSql, columnNames.toArray(new String[columnNames.size()]));
            int i = 0;
            for (ColumnInfo ci : info.mapping.getColumns()) {
                if (!ci.isAutoIncrement()) {
                    FieldInfo fi = info.mapping.getFieldForColumn(ci);
                    Object value = get(entity, fi);
                    if (ci.isPrimaryKey() && value == null) {
                        value = getPrimaryKey(info.mapping, ci);
                        set(entity, fi, Utils.convertTo(value, fi.getType()));
                    }
                    setParameter(stmt, i+1, value, ci.getType());
                    i++;
                }
            }
            int num = stmt.executeUpdate();
            if (num < 1) {
                throw new RuntimeSQLException("Error inserting entity");
            }
            if (columnNames.size() > 0) {
                ResultSet rs = null;
                try {
                    rs = stmt.getGeneratedKeys();
                    for (String columnName : columnNames) {
                        FieldInfo fi = info.mapping.getFieldForColumn(columnName);
                        ColumnInfo ci = info.mapping.getColumnForColumn(columnName);
                        rs.next();
                        set(entity, fi, getValue(rs, 1, fi.getType(), ci.getType()));
                    }
                } catch (SQLException e) {
                    throw new RuntimeSQLException("Error updating generated keys", e);
                } finally {
                    close(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException("Error inserting entity", e);
        } finally {
            close(stmt);
        }
        return null;
    }
    
    protected Object getPrimaryKey(Mapping mapping, ColumnInfo ci) {
        return null;
    }
    
    public <T> void update(T entity) {
        SqlInfo info = getSqlInfo(this, entity.getClass());
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement(info.updateSql, null);
            int i = 0;
            for (ColumnInfo ci : info.mapping.getColumns()) {
                if (!ci.isPrimaryKey()) {
                    FieldInfo fi = info.mapping.getFieldForColumn(ci);
                    Object value = get(entity, fi);
                    setParameter(stmt, i+1, value, ci.getType());
                    i++;
                }
            }
            for (ColumnInfo ci : info.mapping.getColumns()) {
                if (ci.isPrimaryKey()) {
                    FieldInfo fi = info.mapping.getFieldForColumn(ci);
                    Object value = get(entity, fi);
                    setParameter(stmt, i+1, value, ci.getType());
                    i++;
                }
            }
            int num = stmt.executeUpdate();
            if (num < 1) {
                throw new RuntimeSQLException("Error updating entity");
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException("Error updating entity", e);
        } finally {
            close(stmt);
        }
    }
    
    public <T> void delete(T entity) {
        SqlInfo info = getSqlInfo(this, entity.getClass());
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement(info.deleteSql, null);
            int i = 0;
            for (ColumnInfo ci : info.mapping.getColumns()) {
                FieldInfo fi = info.mapping.getFieldForColumn(ci);
                if (ci.isPrimaryKey()) {
                    Object value = get(entity, fi);
                    setParameter(stmt, i+1, value, ci.getType());
                    i++;
                }
            }
            int num = stmt.executeUpdate();
            if (num < 1) {
                throw new RuntimeSQLException("Error deleting entity");
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException("Error deleting entity", e);
        } finally {
            close(stmt);
        }
    }
    
   
    
    public class ResultSetIterator<T> implements Iterator<T> {

        private PreparedStatement stmt;
        private ResultSet rs;
        boolean isNative;
        private Class<T> entityClass;
        private Mapping mapping;
        boolean hasNext;
        
        protected ResultSetIterator(PreparedStatement stmt, ResultSet rs, Class<T> entityClass) {
            this.stmt = stmt;
            this.rs = rs;
            this.isNative = Utils.isNativeType(entityClass);
            this.entityClass = entityClass;
            this.mapping = isNative ? null : Mapping.getMapping(BasicEntityManager.this, entityClass);
            try {
                this.hasNext = rs.next();
            } catch (SQLException e) {
                this.hasNext = false;
            }
        }
        
        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            T entity = !isNative ? (T) getObject(rs, mapping) : (T) getValue(rs, 1, entityClass, Types.VARCHAR);
            try {
                this.hasNext = rs.next();
            } catch (SQLException e) {
                this.hasNext = false;
            }
            return entity;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public void close() {
            BasicEntityManager.this.close(rs);
            BasicEntityManager.this.close(stmt);
        }
        
    }

}
