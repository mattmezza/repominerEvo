package it.unisa.sesa.repominer.db;

import java.sql.Connection;

import net.sf.jeasyorm.EntityManager;
import it.unisa.sesa.repominer.db.entities.Import;

public class ImportDAO {

	/**
	 * This method returns a name of package picked by its id, passed as parameter
	 * @param pId
	 * @return An Import object
	 */
	public Import getImportById(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		Import anImport = em.load(Import.class, pId);
		ConnectionPool.getInstance().releaseConnection(connection);
		return anImport;
	}
	
}
