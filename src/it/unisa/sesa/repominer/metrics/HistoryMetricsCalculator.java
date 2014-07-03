package it.unisa.sesa.repominer.metrics;

import it.unisa.sesa.repominer.db.ChangeDAO;
import it.unisa.sesa.repominer.db.PackageMetricDAO;
import it.unisa.sesa.repominer.db.ProjectDAO;
import it.unisa.sesa.repominer.db.ProjectMetricDAO;
import it.unisa.sesa.repominer.db.entities.PackageMetric;
import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.ProjectMetric;
import it.unisa.sesa.repominer.db.entities.SourceContainer;
import it.unisa.sesa.repominer.preferences.PreferenceConstants;
import it.unisa.sesa.repominer.preferences.Preferences;
import it.unisa.sesa.repominer.preferences.exceptions.IntegerPreferenceException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class HistoryMetricsCalculator {

	/**
	 * This method calculate project metrics for project passed as argument
	 * 
	 * @param pProject
	 */
	public static void calculateMetrics(Project pProject) {
		ProjectMetricDAO projecMetricDAO = new ProjectMetricDAO();
		ProjectMetrics projectMetrics = new ProjectMetrics();

		ProjectMetric nrMetric = projectMetrics.getNumberOfRevision(pProject);
		projecMetricDAO.saveMetric(nrMetric);
		System.out.println("Metric NR: " + nrMetric.getValue()
				+ " correctly saved into db");

		Date periodStart = null;
		Date periodEnd = null;
		try {
			periodStart = Preferences.getPeriodStartingDate();
			periodEnd = Preferences.getPeriodEndingDate();
			ProjectMetric bcc = projectMetrics.getBCCMMetric(pProject,
					periodStart, periodEnd);
			projecMetricDAO.saveMetric(bcc);
			System.out.println("Metric Basic Code Change Model: "
					+ bcc.getValue() + " correctly saved into db");
		} catch (ParseException e) {
			System.err
					.println("BCCM: invalid value for period start/end field into preferences page.");
		}

		try {
			int periodLength = Preferences.getPeriodLength();
			String periodType = Preferences.getPeriodType();
			List<ProjectMetric> bbcPeriods = projectMetrics.getBCCPeriodBased(
					pProject, periodLength, periodType);
			int index = 1;
			for (ProjectMetric singleBCCM : bbcPeriods) {
				projecMetricDAO.saveMetric(singleBCCM);
				System.out
						.println("Metric Basic Code Change Model calculate for period no. "
								+ index
								+ ": "
								+ singleBCCM.getValue()
								+ " correctly saved into db");
				index++;
			}
		} catch (IntegerPreferenceException ex) {
			System.err.println(ex.getMessage());
		}

		String eccmModality = Preferences.getECCMModality();
		if (eccmModality.equals(PreferenceConstants.ECCM_TIME_VALUE)) {
			try {
				int periodLength = Preferences.getPeriodLength();
				String periodType = Preferences.getPeriodType();
				List<ProjectMetric> staticEccPeriods = projectMetrics
						.getECCPeriodBased(pProject, periodLength, periodType,
								true);
				int index = 1;
				for (ProjectMetric projectMetric : staticEccPeriods) {
					projecMetricDAO.saveMetric(projectMetric);
					System.out.println("Normalized ECCM period #" + index
							+ ": " + projectMetric.getValue() + " saved");
					index++;
				}

				index = 1;
				List<ProjectMetric> adaptiveEccPeriods = projectMetrics
						.getECCPeriodBased(pProject, periodLength, periodType,
								false);

				for (ProjectMetric projectMetric : adaptiveEccPeriods) {
					projecMetricDAO.saveMetric(projectMetric);
					System.out.println("Adaptive ECCM period #" + index + ": "
							+ projectMetric.getValue() + " saved");
					index++;
				}
			} catch (IntegerPreferenceException ex) {
				System.err.println(ex.getMessage());
			}
		} else if (eccmModality
				.equals(PreferenceConstants.ECCM_MODIFICATION_VALUE)) {
			try {
				int modificationLimit = Preferences.getECCMModificationLimit();
				List<ProjectMetric> staticEccPeriods = projectMetrics
						.getECCModificationBased(pProject, modificationLimit, true);
				int index = 1;
				for (ProjectMetric projectMetric : staticEccPeriods) {
					projecMetricDAO.saveMetric(projectMetric);
					System.out.println("Normalized ECCM period #" + index
							+ ": " + projectMetric.getValue() + " saved");
					index++;
				}

				index = 1;
				List<ProjectMetric> adaptiveEccPeriods = projectMetrics
						.getECCModificationBased(pProject, modificationLimit, false);

				for (ProjectMetric projectMetric : adaptiveEccPeriods) {
					projecMetricDAO.saveMetric(projectMetric);
					System.out.println("Adaptive ECCM period #" + index + ": "
							+ projectMetric.getValue() + " saved");
					index++;
				}
			} catch (IntegerPreferenceException ex) {
				System.err.println(ex.getMessage());
			}
		} else if (eccmModality.equals(PreferenceConstants.ECCM_BURST_VALUE)) {
			// calculate clusters of changes
			// call eccmBurstBased
			System.out.println("ECCM-burst: ");
		}

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

		int NAUTH = packageMetrics.getNumberOfAuthor(pSourceContainer);

		PackageMetric nauth = new PackageMetric();
		nauth.setDescription("Number of authors of changes made on a package");
		nauth.setName("NAUTH");
		nauth.setPackageId(pSourceContainer.getId());
		nauth.setValue(new Double(NAUTH));
		nauth.setStart(startDate);
		nauth.setEnd(endDate);
		packageMetricDAO.saveMetric(nauth);
		System.out.println("Metric NAUTH: " + NAUTH
				+ " correctly saved into db");

		double changeSetSize = packageMetrics
				.getMeanDimensionOfModifiedFiles(pSourceContainer);

		PackageMetric meanChangeSetSize = new PackageMetric();
		meanChangeSetSize
				.setDescription("Mean dimension of modified files of a package");
		meanChangeSetSize.setName("mean_CHANGE_SET_SIZE");
		meanChangeSetSize.setPackageId(pSourceContainer.getId());
		meanChangeSetSize.setValue(new Double(changeSetSize));
		meanChangeSetSize.setStart(startDate);
		meanChangeSetSize.setEnd(endDate);
		packageMetricDAO.saveMetric(meanChangeSetSize);
		System.out.println("Metric mean_CHANGE_SET_SIZE: " + changeSetSize
				+ " correctly saved into db");

		double mean_NCHANGE_value = packageMetrics
				.getMeanNumberOfChange(pSourceContainer);

		PackageMetric meanNChange = new PackageMetric();
		meanNChange
				.setDescription("Mean number of time in which files of a package have been modified");
		meanNChange.setName("mean_NCHANGE");
		meanNChange.setPackageId(pSourceContainer.getId());
		meanNChange.setValue(new Double(mean_NCHANGE_value));
		meanNChange.setStart(startDate);
		meanNChange.setEnd(endDate);
		packageMetricDAO.saveMetric(meanNChange);
		System.out.println("Metric mean_NCHANGE: " + mean_NCHANGE_value
				+ " correctly saved into db");

		double mean_NREF_value = packageMetrics
				.getMeanNumberOfChangeForRefactoring(pSourceContainer);

		PackageMetric meanNRefMetric = new PackageMetric();
		meanNRefMetric
				.setDescription("Mean number of time in which files of a package have been refactored");
		meanNRefMetric.setName("mean_NREF");
		meanNRefMetric.setPackageId(pSourceContainer.getId());
		meanNRefMetric.setValue(new Double(mean_NREF_value));
		meanNRefMetric.setStart(startDate);
		meanNRefMetric.setEnd(endDate);
		packageMetricDAO.saveMetric(meanNRefMetric);
		System.out.println("Metric mean_NREF: " + mean_NREF_value
				+ " correctly saved into db");

		double mean_NFIX_value = packageMetrics
				.getMeanNumberOfChangeForBugFix(pSourceContainer);

		PackageMetric meanNFixMetric = new PackageMetric();
		meanNFixMetric
				.setDescription("Mean number of time in which files of a package have been fixed for a bug");
		meanNFixMetric.setName("mean_NFIX");
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
				.setDescription("Sum of all the insertions or deletions made on the files of a package");
		sumInsertionsMetric.setName("Sum_LINES");
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
				.setDescription("Mean number of insertions or deletions made on the files of a package");
		meanInsertionsMetric.setName("Mean_LINES");
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
				.setDescription("Maximum number of insertions or deletions made on the files of a package");
		maxInsertionsMetric.setName("Max_LINES");
		maxInsertionsMetric.setPackageId(pSourceContainer.getId());
		maxInsertionsMetric.setValue(new Double(max));
		maxInsertionsMetric.setStart(startDate);
		maxInsertionsMetric.setEnd(endDate);
		packageMetricDAO.saveMetric(maxInsertionsMetric);
		System.out.println("Metric Max_LINES: " + max
				+ " correctly saved into db");

	}
}
