package it.unisa.sesa.repominer.db;

import java.sql.Connection;

import net.sf.jeasyorm.EntityManager;
import it.unisa.sesa.repominer.db.entities.Import;

public class ImportDAO {

	public Import getImportById(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		Import anImport = em.load(Import.class, pId);
		ConnectionPool.getInstance().releaseConnection(connection);
		return anImport;
	}
	
}
