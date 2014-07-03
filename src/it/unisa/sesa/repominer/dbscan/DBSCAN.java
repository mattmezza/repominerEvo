package it.unisa.sesa.repominer.dbscan;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DBSCAN {
	/* Maximum radius of the neighborhood to be considered */
	private double eps;

	/* Minimum number of points needed for a cluster */
	private int minPoints;

	public DBSCAN(double eps, int minPoints) {
		this.eps = eps;
		this.minPoints = minPoints;
	}

	/**
	 * Return the eps value (maximum radius of the neighborhood to be
	 * considered)
	 * 
	 * @return maximum radius of the neighborhood
	 */
	public double getEps() {
		return eps;
	}

	/**
	 * Returns the minimum number of points needed for a cluster
	 * 
	 * @return minimum number of points needed for a cluster
	 */
	public int getMinPoints() {
		return minPoints;
	}

	/**
	 * Perform DBSCAN cluster analysis
	 * 
	 * @param pPoints
	 *            the points to cluster
	 * @return the list of cluster
	 */
	public List<Cluster> cluster(List<ChangePoint> pPoints) {
		/* C (list of cluster) initialization */
		List<Cluster> clusters = new ArrayList<>();
		/* Map need to mapping noise or points already in a cluster */

		/* Itera i punti */
		for (ChangePoint point : pPoints) {
			/* Scorre tutti i punti non visitati */
			if (point.isNotVisited()) {

				/* Prendiamo lista dei vicini */
				List<ChangePoint> neighbors = getNeighbors(point, pPoints);

//				System.out.println("Neightbor of point : " + point.getX());
//				for (ChangePoint changePoint : neighbors) {
//					System.out.println(changePoint.getX());
//				}
//				System.out.println("******************");
				if (neighbors.size() < minPoints) {
					// classificato come rumore
					point.setNoise();
				} else {
					// crea un nuovo cluster e espandilo
					Cluster cluster = new Cluster(null); // DBSCAN doesn't care
															// center
					clusters.add(this.expandCluster(cluster, point, neighbors,
							pPoints));
				}
			}
		}
		return clusters;

	}

	private Cluster expandCluster(Cluster pCluster, ChangePoint pPoint,
			List<ChangePoint> neighbors, List<ChangePoint> points) {

		// aggiungiamo punto al cluster
		pCluster.addPoint(pPoint);
		pPoint.setAlreadyInACluster();

		// scorriamo tutti i punti del vicinato
		for (ChangePoint neighborsPoint : neighbors) {
			if (neighborsPoint.isNotVisited()) {
//				neighborsPoint.setAlreadyInACluster();
				List<ChangePoint> currentNeighbors = getNeighbors(
						neighborsPoint, points);
				// mergiamo currentNeighbors e neighbors senza ripetizioni
				if (currentNeighbors.size() >= this.minPoints) {
					neighbors = this.merge(neighbors, currentNeighbors);
				}
			}

			if (!neighborsPoint.isAlreadyInACluster()) {
				neighborsPoint.setAlreadyInACluster();
				pCluster.addPoint(neighborsPoint);
			}
		}
		return pCluster;
	}

	/**
	 * Returns a list of density-reachable neighbors of a point
	 * 
	 * @param pPoint
	 *            point to look for
	 * @param points
	 *            point possible neighbors
	 * @return the List of neighbors
	 */
	private List<ChangePoint> getNeighbors(ChangePoint pPoint,
			List<ChangePoint> points) {
		List<ChangePoint> neighbors = new ArrayList<>();
		for (ChangePoint changePoint : points) {
			if (changePoint.distanceFrom(pPoint) <= this.eps) {
				neighbors.add(changePoint);
			}
		}
//		neighbors.add(pPoint);
		return neighbors;
	}

	/**
	 * Merge two list together with no duplicates
	 * 
	 * @param pNeighbors1
	 *            first list
	 * @param pNeighbors2
	 *            second list
	 * @return merged list
	 */
	private List<ChangePoint> merge(List<ChangePoint> pNeighbors1,
			List<ChangePoint> pNeighbors2) {
		Set<ChangePoint> resultList = new LinkedHashSet<>();
		resultList.addAll(pNeighbors1);
		resultList.addAll(pNeighbors2);
		return new ArrayList<>(resultList);
	}
}
