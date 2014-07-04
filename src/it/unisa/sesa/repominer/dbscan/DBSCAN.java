package it.unisa.sesa.repominer.dbscan;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * DBSCAN (density-based spatial clustering of applications with noise)
 * algorithm
 * <p>
 * The DBSCAN algorithm forms clusters based on the idea of density
 * connectivity, i.e. a point p is density connected to another point q, if
 * there exists a chain of points p<sub>i</sub>, with i=1...n and
 * p<sub>1</sub>=p and p<sub>n</sub>=q, such that each pair p<sub>i+1</sub> is
 * directly density-reachable from p<sub>i</sub>. A point q is directly
 * density-reachable from point p if it is in the &epsilon;-neighborhood or this
 * point.
 * <p>
 * Any point that is not density-reachable from a formed cluster is treated as
 * noise, and will thus not be present in the results.
 * <p>
 * The algorithm requires two parameters:
 * <ul>
 * <li>eps: the distance that defines the
 * <li>epsilon-neighborhood of a point minPoints: the minimum number of
 * density-connected points required to form a cluster
 * </ul>
 * 
 * @author giograno
 * 
 */
public class DBSCAN {
	/* Maximum radius of the neighborhood to be considered */
	private double eps;

	/* Minimum number of points needed for a cluster */
	private int minPoints;

	/**
	 * Creates a new instance of a DBSCAN
	 * 
	 * @param eps
	 *            maximum radius of the neighborhood to be considered
	 * @param minPoints
	 *            minimum number of points needed for a cluster
	 */
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
	 * @return the list of Cluster
	 */
	public List<Cluster> cluster(List<ChangePoint> pPoints) {
		List<Cluster> clusters = new ArrayList<>();

		for (ChangePoint point : pPoints) {
			if (point.isNotVisited()) {
				// a point from here should be not visited
				List<ChangePoint> neighbors = getNeighbors(point, pPoints);

				if (neighbors.size() < minPoints) {
					point.setNoise();
				} else {
					Cluster cluster = new Cluster(null);
					clusters.add(this.expandCluster(cluster, point, neighbors,
							pPoints));
				}
			}
		}
		return clusters;

	}

	/**
	 * Expands the cluster to include density-reachable items
	 * 
	 * @param pCluster
	 *            Cluster to expand
	 * @param pPoint
	 *            point to add to cluster
	 * @param neighbors
	 *            list of neighbors
	 * @param points
	 *            the data set
	 * @return the expanded cluster
	 */
	private Cluster expandCluster(Cluster pCluster, ChangePoint pPoint,
			List<ChangePoint> neighbors, List<ChangePoint> points) {

		pCluster.addPoint(pPoint);
		pPoint.setAlreadyInACluster();

		for (ChangePoint neighborsPoint : neighbors) {
			if (neighborsPoint.isNotVisited()) {

				List<ChangePoint> currentNeighbors = getNeighbors(
						neighborsPoint, points);

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
		return neighbors;
	}

	/**
	 * Merge two list together with no duplicates
	 * 
	 * @param pNeighbors1
	 *            first list
	 * @param pNeighbors2
	 *            second list
	 * @return a merged list
	 */
	private List<ChangePoint> merge(List<ChangePoint> pNeighbors1,
			List<ChangePoint> pNeighbors2) {
		Set<ChangePoint> resultList = new LinkedHashSet<>();
		resultList.addAll(pNeighbors1);
		resultList.addAll(pNeighbors2);
		return new ArrayList<>(resultList);
	}
}
