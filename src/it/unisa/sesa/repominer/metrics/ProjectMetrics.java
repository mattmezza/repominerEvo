package it.unisa.sesa.repominer.metrics;

import it.unisa.sesa.repominer.db.ChangeDAO;
import it.unisa.sesa.repominer.db.ChangeForCommitDAO;
import it.unisa.sesa.repominer.db.SourceContainerDAO;
import it.unisa.sesa.repominer.db.TypeDAO;
import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.ChangeForCommit;
import it.unisa.sesa.repominer.db.entities.Metric;
import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.ProjectMetric;
import it.unisa.sesa.repominer.db.entities.SourceContainer;
import it.unisa.sesa.repominer.db.entities.Type;
import it.unisa.sesa.repominer.dbscan.ChangePoint;
import it.unisa.sesa.repominer.dbscan.Cluster;
import it.unisa.sesa.repominer.dbscan.DBSCAN;
import it.unisa.sesa.repominer.metrics.exception.NoChangesException;
import it.unisa.sesa.repominer.preferences.PreferenceConstants;
import it.unisa.sesa.repominer.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectMetrics {

	private ChangeDAO changeDAO = new ChangeDAO();
	private ChangeForCommitDAO changeForCommitDAO = new ChangeForCommitDAO();
	private TypeDAO typeDAO = new TypeDAO();
	private SourceContainerDAO sourceContainerDAO = new SourceContainerDAO();

	/**
	 * Calculate the NR metric, that represents the system number of revision
	 * 
	 * @param pProject
	 *            project analyzed
	 * @return the NR metric value
	 */
	public ProjectMetric getNumberOfRevision(Project pProject) {
		List<Change> changes = this.changeDAO.getChangesOfProject(pProject);
		ProjectMetric nr = new ProjectMetric();
		nr.setDescription(Metric.NUM_REVISION_DESCRIPTION);
		nr.setName(Metric.NUM_REVISION_NAME);
		nr.setValue(new Double(changes.size()));
		nr.setProjectId(pProject.getId());
		nr.setEnd(this.changeDAO.getProjectEndDate(pProject));
		nr.setStart(this.changeDAO.getProjectStartDate(pProject));
		return nr;
	}

	/**
	 * This method break history into burst based periods and then calculates
	 * the value of ECC Model on this periods
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param pEps
	 *            eps parameter for dbscan analysis
	 * @param pMinPoints
	 *            minPoint parameter for dbscan analysis
	 * @param pIsStatic
	 *            if true calculates ECCM with Normalized Static Entropy; if
	 *            false calculates ECCM with Adaptive Sizing Entropy
	 * @return a list of ECC Model values
	 */
	public List<ProjectMetric> getECCBurstBased(Project pProject, int pEps,
			int pMinPoints, boolean pIsStatic) throws NoChangesException {
		List<Change> projectChanges = this.changeDAO
				.getChangesOfProject(pProject);
		if(projectChanges.isEmpty()) {
			throw new NoChangesException();
		}
		Calendar startDate = Utils.dateToCalendar(projectChanges.get(0)
				.getCommitDate());

		List<ChangePoint> changePoints = new ArrayList<>();
		for (Change change : projectChanges) {
			int xCoordinate = Utils.daysBetween(startDate,
					Utils.dateToCalendar(change.getCommitDate()));
			changePoints.add(new ChangePoint(xCoordinate, change));
		}

		List<ProjectMetric> listECC = new ArrayList<>();

		DBSCAN clusterizator = new DBSCAN(pEps, pMinPoints);
		List<Cluster> clusters = clusterizator.cluster(changePoints);
		for (Cluster cluster : clusters) {
			try {
				List<ChangePoint> clusterPoints = cluster.getPoints();
				Date clusterStartDate = clusterPoints.get(0).getChange()
						.getCommitDate();
				Date clusterEndDate = clusterPoints
						.get(clusterPoints.size() - 1).getChange()
						.getCommitDate();
				double eccValue = this.calculateECCMValue(pProject,
						clusterStartDate, clusterEndDate, pIsStatic);
				ProjectMetric currentEccm = new ProjectMetric();
				currentEccm.setValue(new Double(eccValue));
				currentEccm.setStart(clusterStartDate);
				currentEccm.setEnd(clusterEndDate);

				if (pIsStatic) {
					currentEccm.setDescription(Metric.ECCM_STATIC_DESCRIPTION);
					currentEccm.setName(Metric.ECCM_STATIC_NAME);
				} else {
					currentEccm.setDescription(Metric.ECCM_DESCRIPTION);
					currentEccm.setName(Metric.ECCM_NAME);
				}

				currentEccm.setProjectId(pProject.getId());
				listECC.add(currentEccm);
			} catch (NoChangesException ex) {
				System.err.println(ex.getMessage());
			}
		}

		return listECC;
	}

	/**
	 * This method calculate the value of BCC Metric; it calculates this value
	 * only considering changes occurred in time based period between the start
	 * and the end date specified in preference panel
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param pPeriodStart
	 *            start date for analysis
	 * @param pPeriodEnd
	 *            end date for analysis
	 * @return BCC Metric
	 */
	public ProjectMetric getBCCMMetric(Project pProject, Date pPeriodStart,
			Date pPeriodEnd) throws NoChangesException {
		double bccValue = this.calculateBCCMMetricValue(pProject, pPeriodStart,
				pPeriodEnd);
		ProjectMetric bccmMetric = new ProjectMetric();
		bccmMetric.setValue(new Double(bccValue));
		bccmMetric.setStart(pPeriodStart);
		bccmMetric.setEnd(pPeriodEnd);
		bccmMetric.setDescription(Metric.BCCM_DESCRIPTION);
		bccmMetric.setName(Metric.BCCM_NAME);
		bccmMetric.setProjectId(pProject.getId());
		return bccmMetric;
	}

	/**
	 * This method calculate the BCC value for project passed as parameter
	 * considering only changes occurred between two Date always passed as
	 * parameters
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param pPeriodStart
	 *            start date for analysis
	 * @param pPeriodEnd
	 *            end date for analysis
	 * @return value for BCC Metric of this period
	 */
	private double calculateBCCMMetricValue(Project pProject,
			Date pPeriodStart, Date pPeriodEnd) throws NoChangesException {

		List<Type> modifiedClassForProject = this
				.getModifiedClassForProject(pProject);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		// We use getChangesByDateInterval getting all changes occurred in
		// selected period
		List<Change> changes = this.changeDAO.getChangesByDateInterval(
				pProject, pPeriodStart, pPeriodEnd);

		if (changes.isEmpty()) {
			throw new NoChangesException();
		}

		int FIcounter = 0;
		for (Type modifiedFile : modifiedClassForProject) {
			int aux = 0; // counter for occurrence table
			for (Change change : changes) {

				String changeMsg = change.getMessage();

				if (Utils.msgIsBugFixing(changeMsg)
						|| Utils.msgIsRefactoring(changeMsg)) {
					// skip this change
					continue;
				}
				// this is likely to be a FEATURE INTRODUCTION change at this
				// point

				List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
						.getChangeForCommitOfChange(change);

				for (ChangeForCommit changeForCommit : changesForCommit) {
					if (changeForCommit.getModifiedFile().equals(
							modifiedFile.getSrcFileLocation())) {
						aux += 1;
						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
								aux);
						FIcounter += 1;
					}
				}
			}
		}

		if (occurrenceTable.size() == 0 || FIcounter == 0) {
			return .0;
		}

		double[] probabilty = new double[occurrenceTable.size()];

		int index = 0;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			probabilty[index] = occurenceValue / (double) FIcounter;
			index++;
		}

		double bccmMetric = 0;
		for (int i = 0; i < probabilty.length; i++) {
			if (probabilty[i] != 0) {
				bccmMetric += probabilty[i] * Utils.log2(probabilty[i]);
			}
		}
		if (bccmMetric == 0) {
			return 0;
		}

		bccmMetric = bccmMetric * -1;

		return bccmMetric;
	}

	/**
	 * This method calculate a set of values of BCC Metric; it calculates this
	 * values broken the history of changes into equal length periods based on
	 * calendar time from the start of the project. The length of a single
	 * interval time is specified in preference panel
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param periodLength
	 *            length of period for analysis
	 * @param periodType
	 *            weeks, months or year for period of analysis
	 * @return some BCC Model values time period based
	 */
	public List<ProjectMetric> getBCCPeriodBased(Project pProject,
			int periodLength, String periodType) {
		List<ProjectMetric> listBCC = new ArrayList<>();

		int gregorianInterval = 0;

		if (periodType.equals(PreferenceConstants.PERIOD_TYPE_WEEK)) {
			gregorianInterval = GregorianCalendar.WEEK_OF_YEAR;
		} else if (periodType.equals(PreferenceConstants.PERIOD_TYPE_YEAR)) {
			gregorianInterval = GregorianCalendar.YEAR;
		} else if (periodType.equals(PreferenceConstants.PERIOD_TYPE_MONTH)) {
			gregorianInterval = GregorianCalendar.MONTH;
		}

		Calendar startDate = Utils.dateToCalendar(this.changeDAO
				.getProjectStartDate(pProject));
		Date endDate = this.changeDAO.getProjectEndDate(pProject);

		startDate.roll(GregorianCalendar.DAY_OF_MONTH, -1);
		while (startDate.getTime().before(endDate)) {
			try {
				startDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
				Date auxStart = startDate.getTime();
				startDate.add(gregorianInterval, periodLength);
				Date auxEnd = startDate.getTime();
				if (startDate.getTime().after(endDate)) {
					auxEnd = endDate;
				}
				double bccValue = this.calculateBCCMMetricValue(pProject,
						auxStart, auxEnd);
				ProjectMetric currentBccm = new ProjectMetric();
				currentBccm.setValue(new Double(bccValue));
				currentBccm.setStart(auxStart);
				currentBccm.setEnd(auxEnd);
				currentBccm.setDescription(Metric.BCCM_DESCRIPTION);
				currentBccm.setName(Metric.BCCM_NAME);
				currentBccm.setProjectId(pProject.getId());
				listBCC.add(currentBccm);
			} catch (NoChangesException ex) {
				System.err.println(ex.getMessage());
			}
		}

		return listBCC;
	}

	/**
	 * This method get a list of types that have been changed in a project
	 * 
	 * @param pProject
	 *            project analyzed
	 * @return all types changed
	 */
	private List<Type> getModifiedClassForProject(Project pProject) {
		List<Type> modifiedClassesForProject = new ArrayList<>();
		List<Type> allClassesForProject = new ArrayList<>();
		List<SourceContainer> packages = this.sourceContainerDAO
				.getPackages(pProject);
		for (SourceContainer sourceContainer : packages) {
			List<Type> classes = this.typeDAO
					.getClassesByPackage(sourceContainer);
			allClassesForProject.addAll(classes);
		}
		// now in modifiedClassForProject we have all the classes of a project:
		// we need to delete the ones not changed during history

		List<Change> changes = this.changeDAO.getChangesOfProject(pProject);
		for (Change change : changes) {
			// We take and iterate all changes_for_commit of this package
			List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
					.getChangeForCommitOfChange(change);
			for (ChangeForCommit changeForCommit : changesForCommit) {
				for (Type type : allClassesForProject) {
					if (type.getSrcFileLocation().equals(
							changeForCommit.getModifiedFile())) {
						if (!modifiedClassesForProject.contains(type)) {
							modifiedClassesForProject.add(type);
						}
					}
				}
			}
		}
		return modifiedClassesForProject;
	}

	/**
	 * This method calculate a set of values of ECC Model using time based
	 * periods method; it calculates this values broken the history of changes
	 * into equal length periods based on calendar time from the start of the
	 * project. The length of a single interval time is specified in preference
	 * panel
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param pPeriod
	 *            length of period for analysis
	 * @param periodType
	 *            weeks, months or years for period of analysis
	 * @param pIsStatic
	 *            if true calculates ECCM with Normalized Static Entropy; if
	 *            false calculates ECCM with Adaptive Sizing Entropy
	 * @return some ECC Model values calculated with time based periods method
	 */
	public List<ProjectMetric> getECCPeriodBased(Project pProject, int pPeriod,
			String periodType, Boolean pIsStatic) {

		int gregorianInterval = 0;

		if (periodType.equals("WEEK")) {
			gregorianInterval = GregorianCalendar.WEEK_OF_YEAR;
		} else if (periodType.equals("YEAR")) {
			gregorianInterval = GregorianCalendar.YEAR;
		} else if (periodType.equals("MONTH")) {
			gregorianInterval = GregorianCalendar.MONTH;
		}

		Calendar startDate = Utils.dateToCalendar(this.changeDAO
				.getProjectStartDate(pProject));

		Date endDate = this.changeDAO.getProjectEndDate(pProject);

		List<ProjectMetric> listECC = new ArrayList<>();

		startDate.roll(GregorianCalendar.DAY_OF_MONTH, -1);
		while (startDate.getTime().before(endDate)) {
			try {
				startDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
				Date auxStart = startDate.getTime();
				startDate.add(gregorianInterval, pPeriod);
				Date auxEnd = startDate.getTime();
				double eccValue = this.calculateECCMValue(pProject, auxStart,
						auxEnd, pIsStatic);
				ProjectMetric currentEccm = new ProjectMetric();
				currentEccm.setValue(new Double(eccValue));
				currentEccm.setStart(auxStart);
				currentEccm.setEnd(auxEnd);

				if (pIsStatic) {
					currentEccm.setDescription(Metric.ECCM_STATIC_DESCRIPTION);
					currentEccm.setName(Metric.ECCM_STATIC_NAME);
				} else {
					currentEccm.setDescription(Metric.ECCM_DESCRIPTION);
					currentEccm.setName(Metric.ECCM_NAME);
				}

				currentEccm.setProjectId(pProject.getId());
				listECC.add(currentEccm);
			} catch (NoChangesException ex) {
				System.err.println(ex.getMessage());
			}
		}

		return listECC;
	}

	/**
	 * This method calculate the ECC value for project passed as parameter
	 * considering only changes occurred between two Dates always passed as
	 * parameters
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param pStart
	 *            start date for analysis
	 * @param pEnd
	 *            end date for analysis
	 * @param pIsStatic
	 *            if true calculated with Normalized Static Entropy; if false
	 *            calculated with our Adaptive Sizing Entropy
	 * @return the double value for ECC Metric of this period
	 */
	private double calculateECCMValue(Project pProject, Date pStart, Date pEnd,
			Boolean pIsStatic) throws NoChangesException {

		List<Type> modifiedClassForProject = this
				.getModifiedClassForProject(pProject);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		// We use getChangesByDateInterval getting all changes occurred in
		// selected period
		List<Change> changes = this.changeDAO.getChangesByDateInterval(
				pProject, pStart, pEnd);

		if (changes.isEmpty()) {
			throw new NoChangesException();
		}

		double FIcounter = 0; // Total of FI changes occurred int this period
		for (Type modifiedFile : modifiedClassForProject) {
			int aux = 0; // counter for occurrence table
			for (Change change : changes) {

				String changeMsg = change.getMessage();

				if (Utils.msgIsBugFixing(changeMsg)
						|| Utils.msgIsRefactoring(changeMsg)) {
					// skip this change
					continue;
				}
				// this is likely to be a FEATURE INTRODUCTION change at this
				// point

				List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
						.getChangeForCommitOfChange(change);

				for (ChangeForCommit changeForCommit : changesForCommit) {
					if (changeForCommit.getModifiedFile().equals(
							modifiedFile.getSrcFileLocation())) {
						aux += 1;
						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
								aux);
						FIcounter += 1;
					}
				}
			}
		}

		if (occurrenceTable.size() == 0 || FIcounter == 0) {
			return 0.0;
		}

		double[] probabilty = new double[occurrenceTable.size()];

		int index = 0;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			probabilty[index] = occurenceValue / FIcounter;
			index++;
		}

		double ECCMetric = 0;
		for (int i = 0; i < probabilty.length; i++) {
			ECCMetric += probabilty[i] * Utils.log2(probabilty[i]);
		}
		if (ECCMetric == 0) {
			return 0;
		}

		if (pIsStatic) {
			ECCMetric = ECCMetric
					* (1 / Utils.log2(new Double(this.typeDAO
							.getSystemNumberOfTypes(pProject))) * -1);
		} else {
			ECCMetric = ECCMetric * (1 / Utils.log2(probabilty.length) * -1);
		}

		return ECCMetric;
	}

	/**
	 * This method calculate the ECC value for project to analyze considering
	 * only changes passed as parameters
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param changes
	 *            changes for ECC Model analysis
	 * @param pIsStatic
	 *            if true calculated with Normalized Static Entropy; if false
	 *            calculated with our Adaptive Sizing Entropy
	 * @return the double value for ECC Metric of this period
	 */
	private double calculateECCMValue(Project pProject, List<Change> changes,
			boolean pIsStatic) throws NoChangesException {

		List<Type> modifiedClassForProject = this
				.getModifiedClassForProject(pProject);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		if (changes.isEmpty()) {
			throw new NoChangesException();
		}

		double FIcounter = 0; // Total of FI changes occurred int this period
		for (Type modifiedFile : modifiedClassForProject) {
			int aux = 0; // counter for occurrence table
			for (Change change : changes) {

				String changeMsg = change.getMessage();

				if (Utils.msgIsBugFixing(changeMsg)
						|| Utils.msgIsRefactoring(changeMsg)) {
					// skip this change
					continue;
				}
				// this is likely to be a FEATURE INTRODUCTION change at this
				// point

				List<ChangeForCommit> changesForCommit = this.changeForCommitDAO
						.getChangeForCommitOfChange(change);

				for (ChangeForCommit changeForCommit : changesForCommit) {
					if (changeForCommit.getModifiedFile().equals(
							modifiedFile.getSrcFileLocation())) {
						aux += 1;
						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
								aux);
						FIcounter += 1;
					}
				}
			}
		}

		if (occurrenceTable.size() == 0 || FIcounter == 0) {
			return 0.0;
		}

		double[] probabilty = new double[occurrenceTable.size()];

		int index = 0;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			probabilty[index] = occurenceValue / FIcounter;
			index++;
		}

		double eccmValue = 0;
		for (int i = 0; i < probabilty.length; i++) {
			eccmValue += probabilty[i] * Utils.log2(probabilty[i]);
		}
		if (eccmValue == 0) {
			return 0;
		}

		if (pIsStatic) {
			eccmValue = eccmValue
					* (1 / Utils.log2(new Double(this.typeDAO
							.getSystemNumberOfTypes(pProject))) * -1);
		} else {
			eccmValue = eccmValue * (1 / Utils.log2(probabilty.length) * -1);
		}

		return eccmValue;
	}

	/**
	 * This method calculate a set of values of ECC Model using modification
	 * limit period based; it calculates this values broken the history of
	 * changes into period based on number of modifications specified in
	 * preferences panel. To prevent a period where little development may have
	 * occurred from spanning a long time, we impose a limit of 3 months on a
	 * period even if the modification limit was no reached
	 * 
	 * @param pProject
	 *            project analyzed
	 * @param pLimit
	 *            limit of modifications for breaking periods
	 * @param pIsStatic
	 *            if true calculated with Normalized Static Entropy; if false
	 *            calculated with our Adaptive Sizing Entropy
	 * @return some ECC Model value calculated with modification limit period
	 *         method
	 */
	public List<ProjectMetric> getECCModificationBased(Project pProject,
			int pLimit, boolean pIsStatic) throws NoChangesException {
		List<ProjectMetric> eccmMetrics = new ArrayList<>();
		List<Change> projectChanges = this.changeDAO
				.getChangesOfProject(pProject);

		List<List<Change>> periods = new ArrayList<>();
		List<Change> buffer = new ArrayList<>();
		Date periodStart = null;
		if (!projectChanges.isEmpty()) {
			periodStart = projectChanges.get(0).getCommitDate();
		} else {
			throw new NoChangesException();
		}
		for (int i = 0; i < projectChanges.size(); i++) {
			Change currentChange = projectChanges.get(i);
			buffer.add(currentChange);
			Change nextChange = null;
			if (i + 1 < projectChanges.size()) {
				// current change IS NOT last change
				nextChange = projectChanges.get(i + 1);
				Calendar next3months = Utils.dateToCalendar(periodStart);
				next3months.add(Calendar.MONTH, 3);
				Date monthsLimit = next3months.getTime();

				if (nextChange.getCommitDate().after(monthsLimit)
						|| buffer.size() == pLimit) {
					periods.add(new ArrayList<Change>(buffer));
					buffer.clear();
					periodStart = nextChange.getCommitDate();
				}
			} else {
				// current change is last change
				periods.add(new ArrayList<Change>(buffer));
				buffer.clear();
			}
		}

		for (List<Change> changes : periods) {

			double eccmValue = this.calculateECCMValue(pProject, changes,
					pIsStatic);
			ProjectMetric projectMetric = new ProjectMetric();
			if (pIsStatic) {
				projectMetric.setDescription(Metric.ECCM_STATIC_DESCRIPTION);
				projectMetric.setName(Metric.ECCM_STATIC_NAME);
			} else {
				projectMetric.setDescription(Metric.ECCM_DESCRIPTION);
				projectMetric.setName(Metric.ECCM_NAME);
			}
			projectMetric.setValue(eccmValue);
			projectMetric.setProjectId(pProject.getId());
			projectMetric.setStart(changes.get(0).getCommitDate());
			projectMetric.setEnd(changes.get(changes.size() - 1)
					.getCommitDate());
			eccmMetrics.add(projectMetric);
		}
		return eccmMetrics;
	}

}
