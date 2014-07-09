package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Metric;
import it.unisa.sesa.repominer.db.entities.PackageMetric;
import it.unisa.sesa.repominer.db.entities.SourceContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.jeasyorm.BasicEntityManager;
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

	/**
	 * This method saves or update a package metric into database
	 * 
	 * @param pPackageMetric
	 */
	public void saveMetric(PackageMetric pPackageMetric) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		try {
			connection.setAutoCommit(false);
			Metric metric = em.findUnique(Metric.class, "where name=?",
					pPackageMetric.getName());
			if (metric == null) {
				Metric newMetric = new Metric();
				newMetric.setDescription(pPackageMetric.getDescription());
				newMetric.setName(pPackageMetric.getName());
				Integer metricId = new MetricDAO().saveMetric(newMetric);
				pPackageMetric.setMetricId(metricId);
				em.insert(pPackageMetric);
			} else {
				pPackageMetric.setMetricId(metric.getId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				BasicEntityManager bem = (BasicEntityManager) em;
				bem.execute(
						"delete from package_metrics where source_container=? and metric=? and `start`=? and `end`=?",
						pPackageMetric.getPackageId(),
						pPackageMetric.getMetricId(),
						sdf.format(pPackageMetric.getStart()), sdf.format(pPackageMetric.getEnd()));
				em.insert(pPackageMetric);
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (RuntimeSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		ConnectionPool.getInstance().releaseConnection(connection);
	}

}
