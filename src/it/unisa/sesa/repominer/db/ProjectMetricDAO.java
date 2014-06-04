package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Metric;
import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.ProjectMetric;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;
import net.sf.jeasyorm.RuntimeSQLException;

public class ProjectMetricDAO {

	public List<ProjectMetric> getMetricsOfProject(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<ProjectMetric> projectMetrics = em.find(ProjectMetric.class,
				"where project=?", pProject.getId());
		List<Metric> metricsDescription = em
				.find(Metric.class,
						"where id in (select metric from project_metrics where project=?)",
						pProject.getId());
		for (int i = 0; i < projectMetrics.size(); i++) {
			if (metricsDescription.get(i) != null) {
				projectMetrics.get(i).setDescription(
						metricsDescription.get(i).getDescription());
				projectMetrics.get(i).setId(metricsDescription.get(i).getId());
				projectMetrics.get(i).setName(
						metricsDescription.get(i).getName());
			}
		}
		ConnectionPool.getInstance().releaseConnection(connection);
		return projectMetrics;
	}

	public ProjectMetric getMetric(Metric pMetric, Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		ProjectMetric projectMetric = em.findUnique(ProjectMetric.class,
				"where project=? and metric=?", pProject.getId(),
				pMetric.getId());
		projectMetric.setDescription(pMetric.getDescription());
		projectMetric.setName(pMetric.getName());
		projectMetric.setId(pMetric.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return projectMetric;
	}

	public void saveMetric(ProjectMetric pProjectMetric) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		try {
			em.insert(pProjectMetric);
		} catch (RuntimeSQLException e) {
			System.err.println("eccezione sollevata ProjectMetricDAO.java");
			e.printStackTrace();
		}
		ConnectionPool.getInstance().releaseConnection(connection);
	}

}
