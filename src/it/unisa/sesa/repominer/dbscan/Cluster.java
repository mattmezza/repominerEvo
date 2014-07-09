package it.unisa.sesa.repominer.dbscan;

import java.util.ArrayList;
import java.util.List;

/**
 * Cluster holding a set of points
 * 
 * @author repominerEvo Team
 * 
 */
public class Cluster {

	/* A cluster contains some points */
	private List<ChangePoint> points;
	/* Center of the cluster */
	private ChangePoint center;

	/**
	 * Build a cluster centered at specified point
	 * 
	 * @param center
	 *            the point which is to be the center of this cluster
	 */
	public Cluster(ChangePoint center) {
		this.center = center;
		this.points = new ArrayList<>();
	}

	/**
	 * Add a point to this cluster
	 * 
	 * @param pPoint
	 *            point to add
	 */
	public void addPoint(ChangePoint pPoint) {
		this.points.add(pPoint);
	}

	/**
	 * Get all points contained in the cluster
	 * 
	 * @return points contained in the cluster
	 */
	public List<ChangePoint> getPoints() {
		return points;
	}

	/**
	 * Get the point chosen to be the center of this cluster
	 * 
	 * @return chosen cluster center
	 */
	public ChangePoint getCenter() {
		return center;
	}

}
