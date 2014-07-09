package it.unisa.sesa.repominer.metrics;

import it.unisa.sesa.repominer.db.ChangeDAO;
import it.unisa.sesa.repominer.db.ChangeForCommitDAO;
import it.unisa.sesa.repominer.db.ProjectDAO;
import it.unisa.sesa.repominer.db.TypeDAO;
import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.ChangeForCommit;
import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.SourceContainer;
import it.unisa.sesa.repominer.db.entities.Type;
import it.unisa.sesa.repominer.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible to calculate all the history metrics relative to a
 * package.
 * 
 * @author RepominerEvo Team
 * 
 */

public class PackageMetrics {

	private ChangeDAO changeDAO = new ChangeDAO();
	private ProjectDAO projectDAO = new ProjectDAO();
	private TypeDAO typeDAO = new TypeDAO();
	private ChangeForCommitDAO changeForCommitDAO = new ChangeForCommitDAO();

	/**
	 * This method calculate NR metric. The NR metric represent system number of
	 * revision
	 * 
	 * @param pSourceContainer
	 * @return NR metric value
	 */
	public int getNumberOfAuthor(SourceContainer pSourceContainer) {
		List<String> authors = new ArrayList<>();
		List<Type> types = this.typeDAO.getClassesByPackage(pSourceContainer);
		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Change change : changes) {
			List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {
				for (Type currType : types) {
					if (currType.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						String author = change.getDevMail();
						if (author == null || author.isEmpty()) {
							author = change.getDevId();
						}
						if (!authors.contains(author)) {
							authors.add(author);
						}
						break;
					}
				}
			}
		}
		return authors.size();
	}

	/**
	 * This method calculate ChangeSetSize metric. The ChangeSetSize metric
	 * represent mean dimension of modified files in a package
	 * 
	 * @param pSourceContainer
	 * @return ChangeSetSize metric value
	 */
	public double getMeanDimensionOfModifiedFiles(
			SourceContainer pSourceContainer) {
		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);

		float sumLines = 0;

		// Return 0 if package has not changes
		if (modifiedClassForPackage.size() == 0) {
			return 0f;
		}

		for (Type type : modifiedClassForPackage) {
			sumLines += type.getLinesNumber();
		}
		float meanLines = sumLines / modifiedClassForPackage.size();
		return meanLines;
	}

	/**
	 * This method calculate mean_NCHANGE metric. The mean_NCHANGE metric
	 * represent mean number of file changes in a package
	 * 
	 * @param pSourceContainer
	 * @return mean_NCHANGE metric value
	 */
	public double getMeanNumberOfChange(SourceContainer pSourceContainer) {

		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		// We take all changes for a project
		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Type modifiedFile : modifiedClassForPackage) {
			// Initialization of occurrenceTable with 0 occurrences
			occurrenceTable.put(modifiedFile.getSrcFileLocation(), 0);
			for (Change change : changes) {
				List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
						.getChangeForCommitOfChange(change);

				for (ChangeForCommit changeForCommit : changesForCommit) {
					if (changeForCommit.getModifiedFile().equals(
							modifiedFile.getSrcFileLocation())) {
						int aux = occurrenceTable.get(modifiedFile
								.getSrcFileLocation());
						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
								aux + 1);
					}
				}
			}
		}
		if (occurrenceTable.size() == 0) {
			return 0.0;
		}

		double counter = 0.0;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			counter += occurenceValue;
		}

		return counter / occurrenceTable.size();
	}

	/**
	 * This method calculate mean_NREF metric. The mean_NREF metric represent
	 * mean number of file changes in a package caused by refactoring operation
	 * 
	 * @param pSourceContainer
	 * @return mean_NREF metric value
	 */
	public double getMeanNumberOfChangeForRefactoring(
			SourceContainer pSourceContainer) {
		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		// We take all changes for a project
		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Type modifiedFile : modifiedClassForPackage) {
			// Initialization of occurrenceTable with 0 occurrences
			occurrenceTable.put(modifiedFile.getSrcFileLocation(), 0);
			for (Change change : changes) {

				if (!Utils.msgIsRefactoring(change.getMessage())) {
					continue;
				}

				List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
						.getChangeForCommitOfChange(change);

				for (ChangeForCommit changeForCommit : changesForCommit) {
					if (changeForCommit.getModifiedFile().equals(
							modifiedFile.getSrcFileLocation())) {
						int aux = occurrenceTable.get(modifiedFile
								.getSrcFileLocation());
						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
								aux + 1);
					}
				}
			}
		}

		if (occurrenceTable.size() == 0) {
			return 0.0;
		}

		double counter = 0.0;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			counter += occurenceValue;
		}

		return counter / occurrenceTable.size();
	}

	/**
	 * This method calculate mean_NFIX metric. The mean_NFIX metric represent
	 * mean number of file changes in a package caused by bug fixes
	 * 
	 * @param pSourceContainer
	 * @return mean_NFIX metric value
	 */
	public double getMeanNumberOfChangeForBugFix(
			SourceContainer pSourceContainer) {
		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		// We take all changes for a project
		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Type modifiedFile : modifiedClassForPackage) {
			// Initialization of occurrenceTable with 0 occurrences
			occurrenceTable.put(modifiedFile.getSrcFileLocation(), 0);
			for (Change change : changes) {

				if (!Utils.msgIsBugFixing(change.getMessage())) {
					continue;
				}

				List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
						.getChangeForCommitOfChange(change);

				for (ChangeForCommit changeForCommit : changesForCommit) {
					if (changeForCommit.getModifiedFile().equals(
							modifiedFile.getSrcFileLocation())) {
						int aux = occurrenceTable.get(modifiedFile
								.getSrcFileLocation());
						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
								aux + 1);
					}
				}
			}
		}

		if (occurrenceTable.size() == 0) {
			return 0.0;
		}

		double counter = 0.0;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			counter += occurenceValue;
		}

		return counter / occurrenceTable.size();
	}

	/**
	 * This method calculate the first value of Lines metric, namely sum of
	 * insertion or deletion
	 * 
	 * @param pSourceContainer
	 * @return First value for Lines metric
	 */
	public Integer getTotalInsertionsOrDeletionsNumber(
			SourceContainer pSourceContainer) {
		int sum = 0;

		List<Type> types = this.typeDAO.getClassesByPackage(pSourceContainer);

		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {

				for (Type currType : types) {
					if (currType.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						int insOrDel = changeForCommit.getInsertions()
								+ changeForCommit.getDeletions();
						sum += insOrDel;
						break;
					}
				}
			}
		}
		return sum;
	}

	/**
	 * This method calculate the second value of Lines metric, namely max number
	 * of insertion or deletion
	 * 
	 * @param pSourceContainer
	 * @return Second value for Lines metric
	 */
	public Integer getMaxInsertionsOrDeletionsNumber(
			SourceContainer pSourceContainer) {
		int max = 0;

		List<Type> types = this.typeDAO.getClassesByPackage(pSourceContainer);

		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {

				for (Type currType : types) {
					if (currType.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						int insOrDel = changeForCommit.getInsertions()
								+ changeForCommit.getDeletions();
						if (insOrDel > max)
							max = insOrDel;
						break;
					}
				}
			}
		}
		return max;
	}

	/**
	 * This method calculate the third value of Lines metric, namely mean number
	 * of insertion or deletion
	 * 
	 * @param pSourceContainer
	 * @return Third value for Lines metric
	 */
	public Double getMeanInsertionsOrDeletionsNumber(
			SourceContainer pSourceContainer) {
		double sum = 0;
		int howMany = 0;

		List<Type> types = this.typeDAO.getClassesByPackage(pSourceContainer);

		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {

				for (Type currType : types) {
					if (currType.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						int insOrDel = changeForCommit.getInsertions()
								+ changeForCommit.getDeletions();
						sum += insOrDel;
						howMany++;
						break;
					}
				}
			}
		}
		if (howMany == 0) {
			return new Double(0.0);
		} else {
			return new Double(sum / howMany);
		}
	}

	/**
	 * This method calculate the Lines metric. The Lines metric represent the
	 * total, mean an maximum number of insertion or deletion
	 * 
	 * @param pSourceContainer
	 * @return Lines metric value
	 */
	public Double[] getInsertionsAndDelitionsInfo(
			SourceContainer pSourceContainer) {
		Double[] info = new Double[3];
		double sum = 0.0;
		int max = 0;
		double howMany = 0.0;

		List<Type> types = this.typeDAO.getClassesByPackage(pSourceContainer);

		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {

				for (Type currType : types) {
					if (currType.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						int insOrDel = changeForCommit.getInsertions()
								+ changeForCommit.getDeletions();
						if (insOrDel > max)
							max = insOrDel;
						sum += insOrDel;
						howMany++;
						break;
					}
				}
			}
		}

		info[0] = new Double(sum);
		if (howMany == 0) {
			info[1] = new Double(0.0);
		} else {
			info[1] = new Double(sum / howMany);
		}
		info[2] = new Double(max);
		return info;
	}

	/**
	 * This method get a list of class that have been changed in a package
	 * 
	 * @param pSourceContainer
	 * @return A list of Type objects
	 */
	private List<Type> getModifiedClassForPackage(
			SourceContainer pSourceContainer) {
		List<Type> modifiedClassForPackage = new ArrayList<>();

		List<Type> types = this.typeDAO.getClassesByPackage(pSourceContainer);
		Project project = this.projectDAO.getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = this.changeDAO.getChangesOfProject(project);
		for (Change change : changes) {
			// We take and iterate all changes_for_commit of this package
			List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {
				for (Type type : types) {
					if (type.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						if (!modifiedClassForPackage.contains(type)) {
							modifiedClassForPackage.add(type);
						}
					}
				}
			}
		}
		return modifiedClassForPackage;
	}
}
