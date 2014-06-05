package it.unisa.sesa.repominer.metrics;

import it.unisa.sesa.repominer.db.entities.Project;
import it.unisa.sesa.repominer.db.entities.SourceContainer;

public class HistoryMetricsCalculator {

	/**
	 * This method calculate package metrics for project passed as argument
	 * 
	 * @param pProject
	 */
	public static void calculateMetrics(Project pProject) {
		ProjectMetrics projectMetrics = new ProjectMetrics();
		int NR = projectMetrics.getNumberOfRevision(pProject);
		System.out.println("Metrica Numero di Revisioni del Sistema: " + NR);
	}

	public static void calculateMetrics(SourceContainer pSourceContainer) {
		PackageMetrics packageMetrics = new PackageMetrics();
		int NAUTH = packageMetrics.getNumberOfAuthor(pSourceContainer);
		System.out.println("Metrica NAUTH: " + NAUTH);
		float changeSetSize = packageMetrics.getMeanDimensionOfModifiedFiles(pSourceContainer);
		System.out.println("Metrica changeSetSize: " + changeSetSize);
	}
}
