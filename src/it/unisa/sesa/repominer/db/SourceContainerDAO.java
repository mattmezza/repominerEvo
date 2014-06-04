package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.SourceContainer;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class SourceContainerDAO {

	/**
	 * Returns all packages contained in project passed as parameter
	 * 
	 * @param pProject
	 * @return List of SourceContainer objects
	 */
	public List<SourceContainer> getPackages(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		List<SourceContainer> packages = entityManager.find(
				SourceContainer.class, "where project = ?", pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return packages;
	}
}
