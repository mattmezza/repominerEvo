package it.unisa.sesa.repominer.metrics;

import java.util.Map;

import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.SourceContainer;

public class HistoryMetricsCalculator {

	/**
	 * This method calculate project metrics for project passed as argument
	 * 
	 * @param pProject
	 */
	public static void calculateMetrics(Project pProject) {
		ProjectMetrics projectMetrics = new ProjectMetrics();
		int NR = projectMetrics.getNumberOfRevision(pProject);
		System.out.println("Metrica Numero di Revisioni del Sistema: " + NR);
	}

	/**
	 * This method calculate package metrics for package passed as argument
	 * 
	 * @param pSourceContainer
	 */
	public static void calculateMetrics(SourceContainer pSourceContainer) {
		PackageMetrics packageMetrics = new PackageMetrics();
		int NAUTH = packageMetrics.getNumberOfAuthor(pSourceContainer);
		System.out.println("Metrica NAUTH: " + NAUTH);
		float changeSetSize = packageMetrics
				.getMeanDimensionOfModifiedFiles(pSourceContainer);
		System.out.println("Metrica changeSetSize: " + changeSetSize);
		float mean_NCHANGE = packageMetrics
				.getMeanNumberOfChange(pSourceContainer);
		System.out.println("Metrica mean_NCHANGE " + mean_NCHANGE);
		float mean_NREF = packageMetrics
				.getMeanNumberOfChangeForRefactoring(pSourceContainer);
		System.out.println("Metrica mean_NREF " + mean_NREF);
		Map<String, Double> info = packageMetrics
				.getInsertionsAndDelitionsInfo(pSourceContainer);
		System.out.println(info);
	}
}
