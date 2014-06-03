package net.sf.jeasyorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.sf.jeasyorm.Mapping.ColumnInfo;

public class OracleEntityManager extends BasicEntityManager {

    public OracleEntityManager(Connection connection) throws SQLException {
        super(connection);
        String prodName = connection.getMetaData().getDatabaseProductName();
        if (prodName.indexOf("Oracle") < 0) throw new RuntimeException("Database not supported");
    }

    protected Object getPrimaryKey(Mapping mapping, ColumnInfo ci) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement("select jeasyorm_sequence.nextval from dual", null);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        } finally {
            close(rs);
            close(stmt);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> find(Class<T> entityClass, int pageNum, int pageSize, String query, Object... params) {
        boolean isNative = Utils.isNativeType(entityClass);
        SqlInfo info = isNative ? null : getSqlInfo(this, entityClass);
        String sql = isNative ? query : getSql(info, query);
        List<T> entities = new ArrayList<T>();
        PreparedStatement stmt = null;
        try {
            String pagingSql = "select * from (select t__.*, rownum as rownum__ from (" + sql + 
                ") t__ where rownum <= " + (pageNum*pageSize+pageSize) + ") where rownum__ > " + (pageNum*pageSize);
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
