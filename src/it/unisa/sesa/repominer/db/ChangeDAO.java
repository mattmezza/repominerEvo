package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.Project;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

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
		List<Change> changes = em.find(Change.class, "where project=? order by commit_date ASC",
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

//	/**
//	 * This method return all commit occurred in project passed as parameter,
//	 * filled by the start and the end date specified in History Metric
//	 * Calculator preference panel
//	 * 
//	 * @param pProject
//	 * @return A list of Change objects
//	 */
//	public List<Change> getChangesByDateInPreferences(Project pProject) {
//		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//		String startDate = store.getString("bccStart");
//		String endDate = store.getString("bccEnd");
//
//		Connection connection = ConnectionPool.getInstance().getConnection();
//		EntityManager em = EntityManager.getInstance(connection);
//		List<Change> changes = em.find(Change.class,
//				"where project=? and commit_date between ? and ?",
//				pProject.getId(), startDate, endDate);
//		ConnectionPool.getInstance().releaseConnection(connection);
//		return changes;
//	}

	/**
	 * This method return all commit occurred between two given dates in project
	 * passed as parameter
	 * 
	 * @param pProject
	 * @param pDate1 
	 * @param pDate2
	 * @return A list of Change objects
	 */
	public List<Change> getChangesByDateInterval(Project pProject, Date pDate1,
			Date pDate2) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		List<Change> changes = em.find(Change.class,
				"where project=? and commit_date between ? and ?",
				pProject.getId(), pDate1, pDate2);
		ConnectionPool.getInstance().releaseConnection(connection);
		return changes;
	}

	/**
	 * This method return the date of first change occurred in project passed as
	 * parameter
	 * 
	 * @param pProject
	 * @return A Date (first in project)
	 */
	public Date getProjectStartDate(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		Change change = em.findUnique(Change.class,
				"where project=? order by commit_date ASC limit 1",
				pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return change.getCommitDate();
	}

	/**
	 * This method return the date of last change occurred in project passed as
	 * parameter
	 * 
	 * @param pProject
	 * @return A Date (last in project)
	 */
	public Date getProjectEndDate(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager em = EntityManager.getInstance(connection);
		Change change = em.findUnique(Change.class,
				"where project=? order by commit_date DESC limit 1",
				pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return change.getCommitDate();
	}

}
