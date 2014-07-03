package it.unisa.sesa.repominer.dbscan;

import it.unisa.sesa.repominer.db.entities.Change;

public class ChangePoint {
	/* Point coordinates */
	private final int point;
	/* Change which this point indicate to */
	private Change change;
	/* Flag for not-visited(0), noise(1) or already in a cluster(2) */
	private int visitedFlag;

	public ChangePoint(int x, Change pChange) {
		this.point = x;
		this.change = pChange;
		this.visitedFlag = 0;
	}

	public Change getChange() {
		return change;
	}

	public void setChange(Change change) {
		this.change = change;
	}

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

	public int getVisitedFlag() {
		return visitedFlag;
	}
	
	public boolean isNotVisited(){
		if(visitedFlag==0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isNoise(){
		if(visitedFlag==1){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isAlreadyInACluster(){
		if (visitedFlag==2){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * Calculate distance between two ChangePoints
	 * 
	 * @param pChangePoint
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
