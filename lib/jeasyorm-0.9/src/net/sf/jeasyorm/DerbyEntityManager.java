package net.sf.jeasyorm;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DerbyEntityManager extends BasicEntityManager {

    private boolean pagingSupported = false;
    
    public DerbyEntityManager(Connection connection) throws SQLException {
        super(connection);
        DatabaseMetaData metadata = connection.getMetaData();
        String prodName = metadata.getDatabaseProductName();
        if (prodName.indexOf("Derby") < 0) throw new RuntimeException("Database not supported");
        int majorVersion = metadata.getDatabaseMajorVersion();
        int minorVersion = metadata.getDatabaseMinorVersion();
        if (majorVersion > 10 || (majorVersion == 10 && minorVersion >= 5)) pagingSupported = true;
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> find(Class<T> entityClass, int pageNum, int pageSize, String query, Object... params) {
        if (!pagingSupported) throw new UnsupportedOperationException();
        boolean isNative = Utils.isNativeType(entityClass);
        SqlInfo info = isNative ? null : getSqlInfo(this, entityClass);
        String sql = isNative ? query : getSql(info, query);
        List<T> entities = new ArrayList<T>();
        PreparedStatement stmt = null;
        try {
            String pagingSql = sql + " offset " + (pageNum*pageSize) + " rows fetch next " + pageSize + " rows only";
            stmt = prepareStatement(pagingSql, null);
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
    

}
