package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Metric;
import it.unisa.sesa.repominer.db.entities.PackageMetric;
import it.unisa.sesa.repominer.db.entities.SourceContainer;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;
import net.sf.jeasyorm.RuntimeSQLException;

public class PackageMetricDAO {

	/**
	 * This method returns a list of package metrics for package as parameter
	 * 
	 * @param pSourceContainer
	 * @return A list of PackageMetric objects
	 */
	public List<PackageMetric> getMetricsOfPackage(
			SourceContainer pSourceContainer) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<PackageMetric> packageMetrics = em.find(PackageMetric.class,
				"where source_container=?", pSourceContainer.getId());
		List<Metric> metricsDescription = em
				.find(Metric.class,
						"where id in (select metric from package_metrics where package=?)",
						pSourceContainer.getId());
		for (int i = 0; i < packageMetrics.size(); i++) {
			if (metricsDescription.get(i) != null) {
				packageMetrics.get(i).setDescription(
						metricsDescription.get(i).getDescription());
				packageMetrics.get(i).setId(metricsDescription.get(i).getId());
				packageMetrics.get(i).setName(
						metricsDescription.get(i).getName());
			}
		}
		ConnectionPool.getInstance().releaseConnection(connection);
		return packageMetrics;
	}

	/**
	 * This method return a single package metric picked by metric and package
	 * id
	 * 
	 * @param pMetric
	 * @param pSourceContainer
	 * @return A PackageMetric object
	 */
	public PackageMetric getMetric(Metric pMetric,
			SourceContainer pSourceContainer) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		PackageMetric projectMetric = em.findUnique(PackageMetric.class,
				"where source_container=? and metric=?",
				pSourceContainer.getId(), pMetric.getId());
		projectMetric.setDescription(pMetric.getDescription());
		projectMetric.setName(pMetric.getName());
		projectMetric.setId(pMetric.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return projectMetric;
	}

	public void saveMetric(PackageMetric pPackageMetric) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		try {
			em.insert(pPackageMetric);
		} catch (RuntimeSQLException e) {
			e.printStackTrace();
		}
		ConnectionPool.getInstance().releaseConnection(connection);
	}

}
