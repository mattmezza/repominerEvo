package net.sf.jeasyorm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jeasyorm.Mapping.ColumnInfo;

public class HSQLDBEntityManager extends BasicEntityManager {

    public HSQLDBEntityManager(Connection connection) throws SQLException {
        super(connection);
        String prodName = connection.getMetaData().getDatabaseProductName();
        if (prodName.indexOf("HSQL") < 0) throw new RuntimeException("Database not supported");
    }

    protected Object getPrimaryKey(Mapping mapping, ColumnInfo ci) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement("call next value for JEASYORM_SEQUENCE", null);
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
    
    
}
