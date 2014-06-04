package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.ChangeForCommit;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class ChangeForCommitDAO {

	public List<ChangeForCommit> getChangeForCommitOfChange(Change pChange) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<ChangeForCommit> changesForCommit = em.find(ChangeForCommit.class, "where change_hash=?", pChange.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return changesForCommit;
	}
	
	public ChangeForCommit getChangeForCommitById(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		ChangeForCommit changeForCommit = em.load(ChangeForCommit.class, pId);
		ConnectionPool.getInstance().releaseConnection(connection);
		return changeForCommit;
	}
	
}
