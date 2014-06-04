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

		// ci fottiamo tutte le classi di un package
		List<Type> types = new TypeDAO().getClassesByPackage(pSourceContainer);
		// ci fottiamo il progetto a cui il package appartiene
		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());
		List<Change> changes = new ChangeDAO().getChangesOfProject(project);
		for (Change change : changes) {
			List<ChangeForCommit> cfc = new ChangeForCommitDAO()
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : cfc) {
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
}
