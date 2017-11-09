package inClassAssignment_1;

public class Universe implements Comparable<Universe>{
	private String universeName; 
	private int numDimensions; 
	private Double numGalaxies;
	
	////////////////////////////////
	//Comparable
	////////////////////////////////
	/**
	 * Natural ordering for Universe class:
	 * Sort by number of dimensions in ascending order
	 */
	@Override
	public int compareTo(Universe universe) {
		if(this.getNumDimensions() < universe.getNumDimensions()) {
			return -1; 
		}
		else if(this.getNumDimensions() > universe.getNumDimensions()) {
			return 1;
		}
		
		return 0;
	}
	
	////////////////////////////////
	//Getters and Setters
	////////////////////////////////
	public String getUniverseName() {
		return universeName;
	}

	public void setUniverseName(String universeName) {
		this.universeName = universeName;
	}

	public int getNumDimensions() {
		return numDimensions;
	}

	public void setNumDimensions(int numDimensions) {
		this.numDimensions = numDimensions;
	}

	public Double getNumGalaxies() {
		return numGalaxies;
	}

	public void setNumGalaxies(Double numGalaxies) {
		this.numGalaxies = numGalaxies;
	}

	@Override
	public String toString() {
		return "Universe [universeName=" + universeName + ", numDimensions=" + numDimensions + ", numGalaxies="
				+ numGalaxies + "]";
	}
	
	////////////////////////////////
	//Constructor
	////////////////////////////////
	public Universe(String universeName, int numDimensions, Double numGalaxies) {
		super();
		this.universeName = universeName;
		this.numDimensions = numDimensions;
		this.numGalaxies = numGalaxies;
	} 
	
	
}
