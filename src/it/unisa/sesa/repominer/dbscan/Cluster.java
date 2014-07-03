package it.unisa.sesa.repominer.dbscan;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

	/* A cluster contains some points */
	private List<ChangePoint> points;
	/* Center of the cluster */
	private ChangePoint center;

	/**
	 * Build a cluster centered at specified point
	 * @param center
	 */
	public Cluster(ChangePoint center) {
		this.center = center;
		this.points= new ArrayList<>();
	}
	
	/**
	 * Add a point to this cluster
	 * @param pPoint
	 */
	public void addPoint(ChangePoint pPoint){
		this.points.add(pPoint);
	}

	/**
	 * Get all points contained in the cluster
	 * 
	 * @return
	 */
	public List<ChangePoint> getPoints() {
		return points;
	}
	
	/**
	 * Get the point chosen to be the center of this cluster
	 * 
	 * @return
	 */
	public ChangePoint getCenter() {
		return center;
	}

}
