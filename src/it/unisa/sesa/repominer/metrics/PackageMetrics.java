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
import java.util.List;

public class PackageMetrics {

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

	public float getMeanDimensionOfModifiedFiles(
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

		int sumLines = 0;
		
		// Return 0 if package has not changes
		if (modifiedClassForPackage.size()==0){
			return 0;
		}
		
		for (Type type : modifiedClassForPackage) {
			sumLines += type.getLinesNumber();
		}
		return sumLines / modifiedClassForPackage.size();

	}
}
