package it.unisa.sesa.repominer.db;

import java.sql.Connection;

import net.sf.jeasyorm.EntityManager;
import net.sf.jeasyorm.RuntimeSQLException;
import it.unisa.sesa.repominer.db.entities.Metric;

public class MetricDAO {

	public void saveMetric(Metric pMetric) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		try {
			em.insert(pMetric);
		} catch(RuntimeSQLException e) {
			e.printStackTrace();
		}
		ConnectionPool.getInstance().releaseConnection(connection);
	}
	
}
