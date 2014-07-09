package it.unisa.sesa.repominer.dbscan;

import it.unisa.sesa.repominer.db.entities.Change;

/**
 * An implementation of {@link Change} for points with integer coordinates A
 * single {@link ChangePoint} has two instance variables, a {@link Change}
 * instance and an integer coordinate
 * 
 * @author repominerEvo Team
 * 
 */
public class ChangePoint {
	/* Point coordinates */
	private final int point;
	/* Change which this point indicate to */
	private Change change;
	/* Flag for not-visited(0), noise(1) or already in a cluster(2) */
	private int visitedFlag;

	/**
	 * Build an instance of a point, wrapping a single {@link Change} instance
	 * its coordinate
	 * 
	 * @param x
	 * @param pChange
	 */
	public ChangePoint(int x, Change pChange) {
		this.point = x;
		this.change = pChange;
		this.visitedFlag = 0;
	}

	/**
	 * This method returns the {@link Change} wrapped by this point
	 * 
	 * @return a {@link Change}
	 */
	public Change getChange() {
		return change;
	}

	/**
	 * This method set the {@link Change} specified by this point
	 * 
	 * @param change
	 *            which the point refers
	 */
	public void setChange(Change change) {
		this.change = change;
	}

	/**
	 * This method returns the coordinate for this point
	 * 
	 * @return the coordinate for this point
	 */
	public int getX() {
		return point;
	}

	/**
	 * Set the point as noise (flag value 1)
	 */
	public void setNoise() {
		this.visitedFlag = 1;
	}

	/**
	 * Set the point as already in a cluster (flag value 2)
	 */
	public void setAlreadyInACluster() {
		this.visitedFlag = 2;
	}

	/**
	 * This method return the flag value used to checking the state of this
	 * point; 0 means the point has not been visited; 1 if the point has been
	 * classified as noise; 2 if the point has been already put in a cluster
	 * 
	 * @return the valued of the flag for this point
	 */
	public int getVisitedFlag() {
		return visitedFlag;
	}

	/**
	 * This method check if this point has been already visited by the algorithm
	 * 
	 * @return true if this point has been visited; false otherwise
	 */
	public boolean isNotVisited() {
		if (visitedFlag == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method check if this point has been classified as noise
	 * 
	 * @return true if the point is a noise point; false otherwise
	 */
	public boolean isNoise() {
		if (visitedFlag == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAlreadyInACluster() {
		if (visitedFlag == 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculate distance between two ChangePoints
	 * 
	 * @param pChangePoint
	 *            the point used to calculate distance from this point
	 * @return A double value that represent distance between two points
	 */
	public double distanceFrom(ChangePoint pChangePoint) {
		return Math.abs(this.point - pChangePoint.getX());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((change == null) ? 0 : change.hashCode());
		result = prime * result + point;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangePoint other = (ChangePoint) obj;
		if (change == null) {
			if (other.change != null)
				return false;
		} else if (!change.equals(other.change))
			return false;
		if (point != other.point)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChangePoint [point=" + point + ", change=" + change + "]";
	}
}
