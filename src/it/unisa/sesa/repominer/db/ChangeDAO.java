package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.Activator;
import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.Project;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

import org.eclipse.jface.preference.IPreferenceStore;

public class ChangeDAO {

	/**
	 * This method returns all commits occurred in project passed as parameter
	 * 
	 * @param pProject
	 * @return A list of Change objects
	 */
	public List<Change> getChangesOfProject(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<Change> changes = em.find(Change.class, "where project=?",
				pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return changes;
	}

	/**
	 * This method return a single commit picked by id
	 * 
	 * @param pId
	 * @return A Change object
	 */
	public Change getChangeById(Integer pId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		Change change = em.load(Change.class, pId);
		ConnectionPool.getInstance().releaseConnection(connection);
		return change;
	}

	/**
	 * This method return all commit occurred in project passed as parameter,
	 * filled by the start and the end date specified in History Metric
	 * Calculator preference panel
	 * 
	 * @param pProject
	 * @return
	 */
	public List<Change> getChangesByDate(Project pProject) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String startDate = store.getString("bccStart");
		String endDate = store.getString("bccEnd");

		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<Change> changes = em.find(Change.class,
				"where project=? and commit_date between ? and ?",
				pProject.getId(), startDate, endDate);
		ConnectionPool.getInstance().releaseConnection(connection);
		return changes;
	}

}
