package it.unisa.sesa.repominer.db;

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
		List<Type> types = entityManager.find(Type.class, "where id = ?",
				pSourceContainer.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return types;
	}
}
