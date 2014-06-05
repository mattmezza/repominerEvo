package it.unisa.sesa.repominer.db;

import java.sql.Connection;

import net.sf.jeasyorm.EntityManager;
import net.sf.jeasyorm.RuntimeSQLException;
import it.unisa.sesa.repominer.db.entities.Metric;

public class MetricDAO {

	/**
	 * This method saves in database a metric passed as parameter
	 * 
	 * @param pMetric
	 */
	public Integer saveMetric(Metric pMetric) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		Integer id = null;
		EntityManager em = EntityManager.getInstance(connection);
		try {
			em.insert(pMetric);
			id = pMetric.getId();
		} catch(RuntimeSQLException e) {
			e.printStackTrace();
		}
		ConnectionPool.getInstance().releaseConnection(connection);
		return id;
	}
	
}
