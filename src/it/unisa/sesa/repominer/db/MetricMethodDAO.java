package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Method;
import it.unisa.sesa.repominer.db.entities.Metric;
import it.unisa.sesa.repominer.db.entities.MetricMethod;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class MetricMethodDAO {

	public List<MetricMethod> getMetricsOfMethod(Method pMethod) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<MetricMethod> methodMetrics = em.find(MetricMethod.class, "where method=?", pMethod.getId());
		List<Metric> metricsDescription = em.find(Metric.class, "where id in (select metric from metrics_method where method=?)", pMethod.getId());
		for (int i = 0; i < methodMetrics.size(); i++) {
			if(metricsDescription.get(i) != null) {
				methodMetrics.get(i).setDescription(metricsDescription.get(i).getDescription());
				methodMetrics.get(i).setId(metricsDescription.get(i).getId());
				methodMetrics.get(i).setName(metricsDescription.get(i).getName());
			}
		}
		ConnectionPool.getInstance().releaseConnection(connection);
		return methodMetrics;
	}
	
}
