package it.unisa.sesa.repominer.metrics;

import java.util.Date;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import it.unisa.sesa.repominer.Activator;
import it.unisa.sesa.repominer.db.ChangeDAO;
import it.unisa.sesa.repominer.db.PackageMetricDAO;
import it.unisa.sesa.repominer.db.ProjectDAO;
import it.unisa.sesa.repominer.db.ProjectMetricDAO;
import it.unisa.sesa.repominer.db.entities.Metric;
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

		Date startDate = new ChangeDAO().getProjectStartDate(new ProjectDAO()
				.getProject(pSourceContainer.getProjectId()));
		Date endDate = new ChangeDAO().getProjectEndDate(new ProjectDAO()
				.getProject(pSourceContainer.getProjectId()));

		// Get eclipse preferences
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int periodLenght = store.getInt("period");
		String interval = store.getString("interval");
		String mode = store.getString("eccModality");
		String pStartDate = store.getString("bccStart");
		String pEndDateString = store.getString("bccEnd");

		int NAUTH = packageMetrics.getNumberOfAuthor(pSourceContainer);

		PackageMetric nauth = new PackageMetric();
		nauth.setDescription(Metric.NAUTH_DESCRIPTION);
		nauth.setName(Metric.NAUTH_NAME);
		nauth.setPackageId(pSourceContainer.getId());
		nauth.setValue(new Double(NAUTH));
		nauth.setStart(startDate);
		nauth.setEnd(endDate);
		packageMetricDAO.saveMetric(nauth);
		System.out.println("Metric NAUTH: " + NAUTH
				+ " correctly saved into db");

		float changeSetSize = packageMetrics
				.getMeanDimensionOfModifiedFiles(pSourceContainer);

		PackageMetric meanChangeSetSize = new PackageMetric();
		meanChangeSetSize
				.setDescription(Metric.CHANGE_SET_SIZE_DESCRIPTION);
		meanChangeSetSize.setName(Metric.CHANGE_SET_SIZE_NAME);
		meanChangeSetSize.setPackageId(pSourceContainer.getId());
		meanChangeSetSize.setValue(new Double(changeSetSize));
		meanChangeSetSize.setStart(startDate);
		meanChangeSetSize.setEnd(endDate);
		packageMetricDAO.saveMetric(meanChangeSetSize);
		System.out.println("Metric mean_CHANGE_SET_SIZE: " + changeSetSize
				+ " correctly saved into db");

		float mean_NCHANGE_value = packageMetrics
				.getMeanNumberOfChange(pSourceContainer);

		PackageMetric meanNChange = new PackageMetric();
		meanNChange
				.setDescription(Metric.NCHANGE_DESCRIPTION);
		meanNChange.setName(Metric.NCHANGE_NAME);
		meanNChange.setPackageId(pSourceContainer.getId());
		meanNChange.setValue(new Double(mean_NCHANGE_value));
		meanNChange.setStart(startDate);
		meanNChange.setEnd(endDate);
		packageMetricDAO.saveMetric(meanNChange);
		System.out.println("Metric mean_NCHANGE: " + mean_NCHANGE_value
				+ " correctly saved into db");

		float mean_NREF_value = packageMetrics
				.getMeanNumberOfChangeForRefactoring(pSourceContainer);

		PackageMetric meanNRefMetric = new PackageMetric();
		meanNRefMetric
				.setDescription(Metric.NREF_DESCRIPTION);
		meanNRefMetric.setName(Metric.NREF_NAME);
		meanNRefMetric.setPackageId(pSourceContainer.getId());
		meanNRefMetric.setValue(new Double(mean_NREF_value));
		meanNRefMetric.setStart(startDate);
		meanNRefMetric.setEnd(endDate);
		packageMetricDAO.saveMetric(meanNRefMetric);
		System.out.println("Metric mean_NREF: " + mean_NREF_value
				+ " correctly saved into db");

		float mean_NFIX_value = packageMetrics
				.getMeanNumberOfChangeForBugFix(pSourceContainer);

		PackageMetric meanNFixMetric = new PackageMetric();
		meanNFixMetric
				.setDescription(Metric.NFIX_DESCRIPTION);
		meanNFixMetric.setName(Metric.NFIX_NAME);
		meanNFixMetric.setPackageId(pSourceContainer.getId());
		meanNFixMetric.setValue(new Double(mean_NFIX_value));
		meanNFixMetric.setStart(startDate);
		meanNFixMetric.setEnd(endDate);
		packageMetricDAO.saveMetric(meanNFixMetric);
		System.out.println("Metric mean_NFIX: " + mean_NFIX_value
				+ " correctly saved into db");

		Double[] info = packageMetrics
				.getInsertionsAndDelitionsInfo(pSourceContainer);

		Double sum = info[0];
		PackageMetric sumInsertionsMetric = new PackageMetric();
		sumInsertionsMetric
				.setDescription(Metric.SUM_LINES_DESCRIPTION);
		sumInsertionsMetric.setName(Metric.SUM_LINES_NAME);
		sumInsertionsMetric.setPackageId(pSourceContainer.getId());
		sumInsertionsMetric.setValue(new Double(sum));
		sumInsertionsMetric.setStart(startDate);
		sumInsertionsMetric.setEnd(endDate);
		packageMetricDAO.saveMetric(sumInsertionsMetric);
		System.out.println("Metric Sum_LINES: " + sum
				+ " correctly saved into db");

		Double mean = info[1];
		PackageMetric meanInsertionsMetric = new PackageMetric();
		meanInsertionsMetric
				.setDescription(Metric.MEAN_LINES_DESCRIPTION);
		meanInsertionsMetric.setName(Metric.MEAN_LINES_NAME);
		meanInsertionsMetric.setPackageId(pSourceContainer.getId());
		meanInsertionsMetric.setValue(new Double(mean));
		meanInsertionsMetric.setStart(startDate);
		meanInsertionsMetric.setEnd(endDate);
		packageMetricDAO.saveMetric(meanInsertionsMetric);
		System.out.println("Metric Mean_LINES: " + mean
				+ " correctly saved into db");

		Double max = info[2];
		PackageMetric maxInsertionsMetric = new PackageMetric();
		maxInsertionsMetric
				.setDescription(Metric.MAX_LINES_DESCRIPTION);
		maxInsertionsMetric.setName(Metric.MEAN_LINES_NAME);
		maxInsertionsMetric.setPackageId(pSourceContainer.getId());
		maxInsertionsMetric.setValue(new Double(max));
		maxInsertionsMetric.setStart(startDate);
		maxInsertionsMetric.setEnd(endDate);
		packageMetricDAO.saveMetric(maxInsertionsMetric);
		System.out.println("Metric Max_LINES: " + max
				+ " correctly saved into db");

		PackageMetric bcc = packageMetrics.getBCCMetric(pSourceContainer,
				pStartDate, pEndDateString);
		bcc.setDescription(Metric.BCC_DESCRIPTION);
		bcc.setName(Metric.BCC_NAME);
		bcc.setPackageId(pSourceContainer.getId());
		packageMetricDAO.saveMetric(bcc);
		System.out.println("Metric Basic Code Change Model: " + bcc.getValue()
				+ " correctly saved into db");

		List<PackageMetric> bbcPeriods = packageMetrics.getBCCPeriodBased(
				pSourceContainer, periodLenght, interval);
		int index = 1;
		for (PackageMetric singleBCC : bbcPeriods) {
			singleBCC.setDescription(Metric.BCC_DESCRIPTION);
			singleBCC.setName(Metric.BCC_NAME);
			singleBCC.setPackageId(pSourceContainer.getId());
			packageMetricDAO.saveMetric(singleBCC);
			System.out
					.println("Metric Basic Code Change Model calculate for period no. "
							+ index
							+ ": "
							+ singleBCC.getValue()
							+ " correctly saved into db");
			index++;
		}

		List<PackageMetric> ecc = packageMetrics.getECC(pSourceContainer,
				periodLenght, interval, mode);
		int index2 = 1;
		for (PackageMetric packageMetric : ecc) {
			packageMetric.setDescription(Metric.ECC_DESCRIPTION);
			packageMetric.setName(Metric.ECC_NAME);
			packageMetric.setPackageId(pSourceContainer.getId());
			packageMetricDAO.saveMetric(packageMetric);
			System.out
					.println("Metric Extended Code Change Model calculate for period no. "
							+ index2
							+ ": "
							+ packageMetric.getValue()
							+ " correctly saved into db");
			index2++;
		}

	}
}
