package it.unisa.sesa.repominer.db;

import java.sql.Connection;

import net.sf.jeasyorm.EntityManager;
import it.unisa.sesa.repominer.db.entities.Project;

public class ProjectDAO {

	/**
	 * Returns project searched by id
	 * 
	 * @param pId
	 * @return A Project class
	 */
	public Project getProject(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		Project project = entityManager.findUnique(Project.class,
				"where id = ?", pId);
		ConnectionPool.getInstance().releaseConnection(connection);
		return project;
	}

	/**
	 * Returns a project searched by name
	 * 
	 * @param pName
	 * @return A Project class
	 */
	public Project getProject(String pName) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		Project project = entityManager.findUnique(Project.class,
				"where name = ?", pName);
		ConnectionPool.getInstance().releaseConnection(connection);
		return project;
	}
}
