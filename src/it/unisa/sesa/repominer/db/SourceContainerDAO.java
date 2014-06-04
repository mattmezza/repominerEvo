package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Import;
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
		List<SourceContainer> packages = entityManager
				.find(SourceContainer.class,
						"select source_containers.id, project, import_id, name from source_containers join import on import_id=import.id where project = ?",
						pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return packages;
	}

	/**
	 * Returns a single package filled by id
	 * 
	 * @param pId
	 * @return A SourceContainer object
	 */
	public SourceContainer getPackagesById(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		SourceContainer aPackage = entityManager.findUnique(
				SourceContainer.class, "where id = ?", pId);
		Import anImport = entityManager.load(Import.class,
				aPackage.getImportId());
		aPackage.setName(anImport.getName());
		ConnectionPool.getInstance().releaseConnection(connection);
		return aPackage;
	}
}
