package it.unisa.sesa.repominer.metrics;

import java.util.List;

import it.unisa.sesa.repominer.db.ChangeDAO;
import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.Project;

public class ProjectMetrics {

	/**
	 * Calculate the NR metric, that represents the system number of revision
	 * 
	 * @param pProject
	 * @return The NR metric
	 */
	public int getNumberOfRevision(Project pProject) {
		List<Change> changes = new ChangeDAO().getChangesOfProject(pProject);
		return changes.size();
	}
}
