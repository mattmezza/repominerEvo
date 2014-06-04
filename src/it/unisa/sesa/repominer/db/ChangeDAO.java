package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.Project;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class ChangeDAO {

	public List<Change> getChangesOfProject(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<Change> changes = em.find(Change.class, "where project=?", pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return changes;
	}
	
}
