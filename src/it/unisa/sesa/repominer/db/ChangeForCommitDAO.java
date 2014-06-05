package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.ChangeForCommit;
import it.unisa.sesa.repominer.db.entities.Project;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class ChangeForCommitDAO {

	/**
	 * This method returns all changes occurred for a single commit passed as
	 * parameter
	 * 
	 * @param pChange
	 * @return A list of ChangeForCommit objects
	 */
	public List<ChangeForCommit> getChangeForCommitOfChange(Change pChange) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<ChangeForCommit> changesForCommit = em.find(ChangeForCommit.class,
				"where change_hash=?", pChange.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return changesForCommit;
	}

	/**
	 * This method returns a single change occurred in a commit picked by id
	 * 
	 * @param pId
	 * @return A ChangeForCommit object
	 */
	public ChangeForCommit getChangeForCommitById(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		ChangeForCommit changeForCommit = em.load(ChangeForCommit.class, pId);
		ConnectionPool.getInstance().releaseConnection(connection);
		return changeForCommit;
	}

	/**
	 * This method returns all changes occurred in all commits which refer to a
	 * project passed as parameter
	 * 
	 * @param pProject
	 * @return A list of ChangeForCommit objects
	 */
	public List<ChangeForCommit> getChangesForCommitByProject(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<ChangeForCommit> changesForCommit = em
				.find(ChangeForCommit.class,
						"select * from changes_for_commit where change_hash in (select id from changes where project = ? ",
						pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return changesForCommit;
	}
}
