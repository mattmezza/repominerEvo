package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.db.entities.Issue;
import it.unisa.sesa.repominer.db.entities.Project;

import java.sql.Connection;
import java.util.List;

import net.sf.jeasyorm.EntityManager;

public class IssueDAO {
	/**
	 * Returns all issues for a project passed as parameter
	 * 
	 * @param pProject
	 * @return A list of Issue objects
	 */
	public List<Issue> getIssueByProject(Project pProject) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		EntityManager entityManager = EntityManager.getInstance(connection);
		List<Issue> issues = entityManager.find(Issue.class,
				"where project = ?", pProject.getId());
		ConnectionPool.getInstance().releaseConnection(connection);
		return issues;
	}
}
