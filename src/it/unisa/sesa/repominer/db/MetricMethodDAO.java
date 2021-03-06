package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Method;
import it.unisa.sesa.repominer.db.entities.Metric;
import it.unisa.sesa.repominer.db.entities.MetricMethod;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class MetricMethodDAO {

	/**
	 * This method return a list of metrics method
	 * 
	 * @param pMethod
	 * @return A list of MetricMethod objects
	 */
	public List<MetricMethod> getMetricsOfMethod(Method pMethod) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<MetricMethod> methodMetrics = em.find(MetricMethod.class,
				"where method=?", pMethod.getId());
		List<Metric> metricsDescription = em
				.find(Metric.class,
						"where id in (select metric from metrics_method where method=?)",
						pMethod.getId());
		for (int i = 0; i < methodMetrics.size(); i++) {
			if (metricsDescription.get(i) != null) {
				methodMetrics.get(i).setDescription(
						metricsDescription.get(i).getDescription());
				methodMetrics.get(i).setId(metricsDescription.get(i).getId());
				methodMetrics.get(i).setName(
						metricsDescription.get(i).getName());
			}
		}
		ConnectionPool.getInstance().releaseConnection(connection);
		return methodMetrics;
	}

	/**
	 * This method return a metric of a method
	 * 
	 * @param pMetric
	 * @param pMethod
	 * @return A MetricMethod object
	 */
	public MetricMethod getMetric(Metric pMetric, Method pMethod) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		MetricMethod metricMethod = em
				.findUnique(MetricMethod.class, "where method=? and metric=?",
						pMethod.getId(), pMetric.getId());
		metricMethod.setDescription(pMetric.getDescription());
		metricMethod.setName(pMetric.getName());
		metricMethod.setId(pMetric.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return metricMethod;
	}

}
