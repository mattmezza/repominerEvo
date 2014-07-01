package it.unisa.sesa.repominer.metrics;

import it.unisa.sesa.repominer.Activator;
import it.unisa.sesa.repominer.db.ChangeDAO;
import it.unisa.sesa.repominer.db.ChangeForCommitDAO;
import it.unisa.sesa.repominer.db.ProjectDAO;
import it.unisa.sesa.repominer.db.TypeDAO;
import it.unisa.sesa.repominer.db.entities.Change;
import it.unisa.sesa.repominer.db.entities.ChangeForCommit;
import it.unisa.sesa.repominer.db.entities.PackageMetric;
import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.SourceContainer;
import it.unisa.sesa.repominer.db.entities.Type;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;

public class PackageMetrics {

	private static final Pattern PATTERN_BUG_FIXING = Pattern.compile(".*[Bb][Uu][Gg]([sS])?([^a-zA-Z0-9])*[Ff][iI][xX].*", Pattern.DOTALL);
	private static final Pattern PATTERN_REFACTORING = Pattern.compile(".*[rR][eE][fF][aA][cC][tT][oO][rR].*", Pattern.DOTALL);

	/**
	 * This method calculate NR metric. The NR metric represent system number of
	 * revision
	 * 
	 * @param pSourceContainer
	 * @return NR metric value
	 */
	public int getNumberOfAuthor(SourceContainer pSourceContainer) {
		List<String> authors = new ArrayList<>();
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
						String author = change.getDevMail();
						if(author == null || author.isEmpty()) {
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
	public float getMeanDimensionOfModifiedFiles(
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
			return 0f;
		}

		float counter = 0f;
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
	public float getMeanNumberOfChangeForRefactoring(
			SourceContainer pSourceContainer) {
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

				Matcher matcher = PATTERN_REFACTORING.matcher(change.getMessage());
				Boolean isRefactoring = matcher.matches();
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
			return 0f;
		}

		float counter = 0f;
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
	public float getMeanNumberOfChangeForBugFix(SourceContainer pSourceContainer) {
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

				Matcher matcher = PATTERN_BUG_FIXING.matcher(change.getMessage());
				Boolean isBug = matcher.matches();
				if (!isBug) {
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
			return 0f;
		}

		float counter = 0f;
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
		if (howMany == 0) {
			info[1] = new Double(0.0);
		} else {
			info[1] = new Double(sum / howMany);
		}
		info[2] = new Double(max);
		return info;
	}

	/**
	 * This method calculate the value of BCC Metric; it calculates this value
	 * only considering changes occurred in time based period between the start
	 * and the end date specified in the preference panel
	 * 
	 * @param pSourceContainer
	 * @return The float value of BCC Metric for time period specified in
	 *         preference panel
	 */
	public PackageMetric getBCCMetric(SourceContainer pSourceContainer) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		Date startDate = this.StringToDate(store.getString("bccStart"));
		Date endDate = this.StringToDate(store.getString("bccEnd"));
		
		float bccValue = this.getBCCForPeriod(pSourceContainer, startDate, endDate);
		PackageMetric bccMetric = new PackageMetric();
		bccMetric.setValue(new Double(bccValue));
		bccMetric.setStart(startDate);
		bccMetric.setEnd(endDate);
		return bccMetric;
		
//		List<Type> modifiedClassForPackage = this
//				.getModifiedClassForPackage(pSourceContainer);
//		Map<String, Integer> occurrenceTable = new HashMap<>();
//
//		// We take all changes for a project
//		Project project = new ProjectDAO().getProject(pSourceContainer
//				.getProjectId());
//		// We use getChangesByDate method
//		List<Change> changes = new ChangeDAO()
//				.getChangesByDateInPreferences(project);
//		if (changes.isEmpty()) {
//			return 0;
//		}
//
//		float allFI = 0;
//		for (Type modifiedFile : modifiedClassForPackage) {
//			// Initialization of occurrenceTable with 0 occurrences
//			occurrenceTable.put(modifiedFile.getSrcFileLocation(), 0);
//			for (Change change : changes) {
//
//				Matcher matcher = PATTERN_BUG_FIXING.matcher(change.getMessage());
//				Boolean isBug = matcher.matches();
//				matcher = PATTERN_REFACTORING.matcher(change.getMessage());
//				Boolean isRef = matcher.matches();
//				boolean isNotFI = isBug || isRef;
//				if (isNotFI) {
//					// Reversed condition - only FI modification
//					continue;
//				}
//
//				List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
//						.getChangeForCommitOfChange(change);
//
//				for (ChangeForCommit changeForCommit : changesForCommit) {
//					if (changeForCommit.getModifiedFile().equals(
//							modifiedFile.getSrcFileLocation())) {
//						int aux = occurrenceTable.get(modifiedFile
//								.getSrcFileLocation());
//						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
//								aux+1);
//						allFI += 1;
//					}
//				}
//			}
//		}
//
//		if (occurrenceTable.size() == 0) {
//			return 0;
//		}
//
//		float[] probabilty = new float[occurrenceTable.size()];
//
//		int index = 0;
//		// Iterating over occurrenceTable values
//		for (Integer occurenceValue : occurrenceTable.values()) {
//			probabilty[index] = occurenceValue / allFI;
//			index++;
//		}
//
//		float BCCMetric = 0;
//		for (int i = 0; i < probabilty.length; i++) {
//			BCCMetric += probabilty[i] * this.log2(probabilty[i]);
//		}
//		BCCMetric = BCCMetric * -1;
//
//		return BCCMetric;

	}

	/**
	 * This method calculate a set of values of BCC Metric; it calculates this
	 * values broken the history of changes into equal length periods based on
	 * calendar time from the start of the project. The length of a single
	 * interval time is specified in preference panel
	 * 
	 * @param pSourceContainer
	 * @return A list of Value
	 */
	public List<PackageMetric> getBCCPeriodBased(SourceContainer pSourceContainer) {
		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());
		List<PackageMetric> listBCC = new ArrayList<>();

		int gregorianInterval = 0;

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int periodLenght = store.getInt("period");
		String interval = store.getString("interval");

		if (interval.equals("WEEK")) {
			gregorianInterval = GregorianCalendar.WEEK_OF_YEAR;
		} else if (interval.equals("YEAR")) {
			gregorianInterval = GregorianCalendar.YEAR;
		} else if (interval.equals("MONTH")) {
			gregorianInterval = GregorianCalendar.MONTH;
		}

		Calendar startDate = this.DateToCalendar(new ChangeDAO()
				.getProjectStartDate(project));
		Date endDate = new ChangeDAO().getProjectEndDate(project);

		while (startDate.getTime().before(endDate)) {
			Date auxStart = startDate.getTime();
			startDate.add(gregorianInterval, periodLenght);
			Date auxEnd = startDate.getTime();
			float bccValue = this.getBCCForPeriod(pSourceContainer, auxStart,
					auxEnd);
			PackageMetric currentBcc = new PackageMetric();			
			currentBcc.setValue(new Double(bccValue));
			currentBcc.setStart(auxStart);
			currentBcc.setEnd(auxEnd);
			listBCC.add(currentBcc);
		}

		return listBCC;
	}

	/**
	 * This method calculate the BCC value for package passed as parameter
	 * considering only changes occurred between two Date always passed as
	 * parameters
	 * 
	 * @param pSourceContainer
	 * @param pDate1
	 * @param pDate2
	 * @return The float value for BCC Metric of this period
	 */
	private float getBCCForPeriod(SourceContainer pSourceContainer,
			Date pDate1, Date pDate2) {

		List<Type> modifiedClassForPackage = this
				.getModifiedClassForPackage(pSourceContainer);
		Map<String, Integer> occurrenceTable = new HashMap<>();

		String keyWord = "[^bB]*bug(s?)([^a-zA-Z0-9]?)fix.*";

		// We take all changes for a project
		Project project = new ProjectDAO().getProject(pSourceContainer
				.getProjectId());
		// We use getChangesByDateInterval getting all changes occurred in
		// selected period
		List<Change> changes = new ChangeDAO().getChangesByDateInterval(
				project, pDate1, pDate2);

		if (changes.isEmpty()) {
			return 0;
		}

		float allFI = 0;
		for (Type modifiedFile : modifiedClassForPackage) {
			int aux = 0; // counter for occurrence table
			for (Change change : changes) {

				Boolean isNotFI = change.getMessage().matches(keyWord);
				if (isNotFI) {
					// Reversed condition - only FI modification
					continue;
				}

				List<ChangeForCommit> changesForCommit = new ChangeForCommitDAO()
						.getChangeForCommitOfChange(change);

				for (ChangeForCommit changeForCommit : changesForCommit) {
					if (changeForCommit.getModifiedFile().equals(
							modifiedFile.getSrcFileLocation())) {
						aux += 1;
						occurrenceTable.put(modifiedFile.getSrcFileLocation(),
								aux);
						allFI += 1;
					}
				}
			}
		}

		if (occurrenceTable.size() == 0 || allFI == 0) {
			return 0f;
		}

		float[] probabilty = new float[occurrenceTable.size()];

		int index = 0;
		// Iterating over occurrenceTable values
		for (Integer occurenceValue : occurrenceTable.values()) {
			probabilty[index] = occurenceValue / allFI;
			index++;
		}

		float BCCMetric = 0;
		for (int i = 0; i < probabilty.length; i++) {
			BCCMetric += probabilty[i] * this.log2(probabilty[i]);
		}
		if (BCCMetric == 0) {
			return 0;
		}

		BCCMetric = BCCMetric * -1;

		return BCCMetric;
	}

	/**
	 * This function calculate the base-2 logarithm of a float number
	 * 
	 * @param pValue
	 * @return Base-2 logarithm value
	 */
	private float log2(float pValue) {
		return (float) (Math.log(pValue) / Math.log(2));
	}

	/**
	 * This method convert an instance of Date into an instance of Calendar
	 * 
	 * @param pDate
	 * @return A Calendar object
	 */
	private Calendar DateToCalendar(Date pDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(pDate);
		return calendar;
	}
	
	/**
	 * This method convert a String into a Date
	 * @param pString
	 * @return A Date Object
	 */
	private Date StringToDate(String pString){
		Date convertedDate = null;
		DateFormat formatter = null;
		
		formatter = new SimpleDateFormat("yyyy/MM/dd");
		try {
			convertedDate=(Date) formatter.parse(pString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedDate;
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
