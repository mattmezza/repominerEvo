package it.unisa.sesa.repominer.metrics;

import it.unisa.sesa.repominer.db.PackageMetricDAO;
import it.unisa.sesa.repominer.db.ProjectMetricDAO;
import it.unisa.sesa.repominer.db.entities.PackageMetric;
import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.ProjectMetric;
import it.unisa.sesa.repominer.db.entities.SourceContainer;

public class HistoryMetricsCalculator {

	/**
	 * This method calculate project metrics for project passed as argument
	 * 
	 * @param pProject
	 */
	public static void calculateMetrics(Project pProject) {
		ProjectMetricDAO projecMetricDAO = new ProjectMetricDAO();
		ProjectMetrics projectMetrics = new ProjectMetrics();
		int NR = projectMetrics.getNumberOfRevision(pProject);

		ProjectMetric nrMetric = new ProjectMetric();
		nrMetric.setName("NR");
		nrMetric.setDescription("Number of revision of the system");
		nrMetric.setValue(new Double(NR));
		nrMetric.setProjectId(pProject.getId());
		projecMetricDAO.saveMetric(nrMetric);
		System.out.println("Metric NR: " + NR + " correctly saved into db");
	}

	/**
	 * This method calculate package metrics for package passed as argument
	 * 
	 * @param pSourceContainer
	 */
	public static void calculateMetrics(SourceContainer pSourceContainer) {
		PackageMetrics packageMetrics = new PackageMetrics();
		PackageMetricDAO packageMetricDAO = new PackageMetricDAO();

		int NAUTH = packageMetrics.getNumberOfAuthor(pSourceContainer);

		PackageMetric nauth = new PackageMetric();
		nauth.setDescription("Number of authors of changes made on a package");
		nauth.setName("NAUTH");
		nauth.setPackageId(pSourceContainer.getId());
		nauth.setValue(new Double(NAUTH));
		packageMetricDAO.saveMetric(nauth);
		System.out.println("Metric NAUTH: " + NAUTH
				+ " correctly saved into db");

		float changeSetSize = packageMetrics
				.getMeanDimensionOfModifiedFiles(pSourceContainer);

		PackageMetric meanChangeSetSize = new PackageMetric();
		meanChangeSetSize
				.setDescription("Mean dimension of modified files of a package");
		meanChangeSetSize.setName("mean_CHANGE_SET_SIZE");
		meanChangeSetSize.setPackageId(pSourceContainer.getId());
		meanChangeSetSize.setValue(new Double(changeSetSize));
		packageMetricDAO.saveMetric(meanChangeSetSize);
		System.out.println("Metric mean_CHANGE_SET_SIZE: " + changeSetSize
				+ " correctly saved into db");

		float mean_NCHANGE_value = packageMetrics
				.getMeanNumberOfChange(pSourceContainer);

		PackageMetric meanNChange = new PackageMetric();
		meanNChange
				.setDescription("Mean number of time in which files of a package have been modified");
		meanNChange.setName("mean_NCHANGE");
		meanNChange.setPackageId(pSourceContainer.getId());
		meanNChange.setValue(new Double(mean_NCHANGE_value));
		packageMetricDAO.saveMetric(meanNChange);
		System.out.println("Metric mean_NCHANGE: " + mean_NCHANGE_value
				+ " correctly saved into db");

		float mean_NREF_value = packageMetrics
				.getMeanNumberOfChangeForRefactoring(pSourceContainer);

		PackageMetric meanNRefMetric = new PackageMetric();
		meanNRefMetric
				.setDescription("Mean number of time in which files of a package have been refactored");
		meanNRefMetric.setName("mean_NREF");
		meanNRefMetric.setPackageId(pSourceContainer.getId());
		meanNRefMetric.setValue(new Double(mean_NREF_value));
		packageMetricDAO.saveMetric(meanNRefMetric);
		System.out.println("Metric mean_NREF: " + mean_NREF_value
				+ " correctly saved into db");

		Double[] info = packageMetrics
				.getInsertionsAndDelitionsInfo(pSourceContainer);

		Double sum = info[0];
		PackageMetric sumInsertionsMetric = new PackageMetric();
		sumInsertionsMetric
				.setDescription("Sum of all the insertions or deletions made on the files of a package");
		sumInsertionsMetric.setName("Sum_LINES");
		sumInsertionsMetric.setPackageId(pSourceContainer.getId());
		sumInsertionsMetric.setValue(new Double(sum));
		packageMetricDAO.saveMetric(sumInsertionsMetric);
		System.out.println("Metric Sum_LINES: " + sum
				+ " correctly saved into db");

		Double mean = info[1];
		PackageMetric meanInsertionsMetric = new PackageMetric();
		meanInsertionsMetric
				.setDescription("Mean number of insertions or deletions made on the files of a package");
		meanInsertionsMetric.setName("Mean_LINES");
		meanInsertionsMetric.setPackageId(pSourceContainer.getId());
		meanInsertionsMetric.setValue(new Double(mean));
		packageMetricDAO.saveMetric(meanInsertionsMetric);
		System.out.println("Metric Mean_LINES: " + mean
				+ " correctly saved into db");

		Double max = info[2];
		PackageMetric maxInsertionsMetric = new PackageMetric();
		maxInsertionsMetric
				.setDescription("Maximum number of insertions or deletions made on the files of a package");
		maxInsertionsMetric.setName("Max_LINES");
		maxInsertionsMetric.setPackageId(pSourceContainer.getId());
		maxInsertionsMetric.setValue(new Double(max));
		packageMetricDAO.saveMetric(maxInsertionsMetric);
		System.out.println("Metric Max_LINES: " + max
				+ " correctly saved into db");
	}
}
