package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.SourceContainer;
import it.unisa.sesa.repominer.db.entities.Type;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class TypeDAO {

	/**
	 * Returns all classes contained in the package passed as parameter
	 * 
	 * @param pSourceContainer
	 * @return A list of Type objects
	 */
	public List<Type> getClassesByPackage(SourceContainer pSourceContainer) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		List<Type> types = entityManager.find(Type.class,
				"where source_container = ?", pSourceContainer.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return types;
	}

	/**
	 * Returns a single class picked by id
	 * 
	 * @param pId
	 * @return A Type object
	 */
	public Type getClassById(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		Type type = entityManager.findUnique(Type.class, "where id = ?", pId);
		ConnectionPool.getInstance().releaseConnection(connection);
		return type;
	}

	/**
	 * Return the overall number of types in project passed as parameter
	 * 
	 * @param pProject
	 *            selected project
	 * @return number of types in project
	 */
	public int getSystemNumberOfTypes(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		int num = entityManager
				.count(" from types join source_containers on types.source_container = source_containers.id where source_containers.project = ? ",
						pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return num;
	}

}
