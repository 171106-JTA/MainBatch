package com.revature.collections;

public class Movie implements Comparable<Movie>{
	
	private String name;
	private String director;
	private int year;
	
	public Movie(String name, String director, int year) {
		super();
		this.name = name;
		this.director = director;
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public int compareTo(Movie m) {
		if(this.getYear() < m.getYear())
			return -1;
		else if(this.getYear() > m.getYear())
			return 1;
		return 0;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name: " + name + " Director: " + director + " Year: " + year;
	}

	
	
	

}
