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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageMetrics {

	/**
	 * This method calculate NR metric. The NR metric represent system number of
	 * revision
	 * 
	 * @param pSourceContainer
	 * @return NR metric value
	 */
	public int getNumberOfAuthor(SourceContainer pSourceContainer) {
		List<String> devMails = new ArrayList<>();
		List<Type> types = new TypeDAO().getClassesByPackage(pSourceContainer);
		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Change change : changes) {
			List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {
				for (Type currType : types) {
					if (currType.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						String devMail = change.getDevMail();
						if (!devMails.contains(devMail)) {
							devMails.add(devMail);
						}
						break;
					}
				}
			}
		}
		return devMails.size();
	}

	/**
	 * This method calculate mean_NCHANGE metric. The mean_NCHANGE metric
	 * represent mean number of file changes in a package
	 * 
	 * @param pSourceContainer
	 * @return mean_NCHANGE metric
	 */
	public float getMeanDimensionOfModifiedFiles(
			SourceContainer pSourceContainer) {
		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);

		int sumLines = 0;

		// Return 0 if package has not changes
		if (modifiedClassForPackage.size() == 0) {
			return 0;
		}

		for (Type type : modifiedClassForPackage) {
			sumLines += type.getLinesNumber();
		}
		return sumLines / modifiedClassForPackage.size();

	}

	public float getMeanNumberOfChange(SourceContainer pSourceContainer) {

		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		// We take all changes for a project
		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Type modifiedFile : modifiedClassForPackage) {
			// Initialization of occurrenceTable with 0 occurrences
			occurrenceTable.put(modifiedFile.getSrcFileLocation(), 0);
			for (Change change : changes) {
				List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
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
			return 0;
		}

		float counter = 0f;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			counter += occurenceValue;
		}

		return counter / occurrenceTable.size();
	}

	public float getMeanNumberOfChangeForRefactoring(
			SourceContainer pSourceContainer) {
		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		String keyWord = "refactoring";

		// We take all changes for a project
		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Type modifiedFile : modifiedClassForPackage) {
			// Initialization of occurrenceTable with 0 occurrences
			occurrenceTable.put(modifiedFile.getSrcFileLocation(), 0);
			for (Change change : changes) {

				Boolean isRefactoring = change.getMessage().contains(keyWord);
				if (!isRefactoring) {
					continue;
				}

				List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
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
			return 0;
		}

		float counter = 0f;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			counter += occurenceValue;
		}

		return counter / occurrenceTable.size();
	}

	public Integer getTotalInsertionsOrDeletionsNumber(
			SourceContainer pSourceContainer) {
		int sum = 0;

		List<Type> types = new TypeDAO().getClassesByPackage(pSourceContainer);

		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
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

	public Integer getMaxInsertionsOrDeletionsNumber(
			SourceContainer pSourceContainer) {
		int max = 0;

		List<Type> types = new TypeDAO().getClassesByPackage(pSourceContainer);

		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
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

	public Double getMeanInsertionsOrDeletionsNumber(
			SourceContainer pSourceContainer) {
		double sum = 0;
		int howMany = 0;

		List<Type> types = new TypeDAO().getClassesByPackage(pSourceContainer);

		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
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
		return new Double(sum / howMany);
	}

	public Double[] getInsertionsAndDelitionsInfo(
			SourceContainer pSourceContainer) {
		Double[] info = new Double[3];
		double sum = 0;
		int max = 0;
		int howMany = 0;

		List<Type> types = new TypeDAO().getClassesByPackage(pSourceContainer);

		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());

		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Change change : changes) {

			List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
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
		info[1] = new Double(sum / howMany);
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

		List<Type> types = new TypeDAO().getClassesByPackage(pSourceContainer);
		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
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
