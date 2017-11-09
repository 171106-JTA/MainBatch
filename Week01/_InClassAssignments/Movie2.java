package com.revature.collections;

import java.util.Comparator;

import javax.naming.directory.DirContext;

public class Movie2 implements Comparator<Movie>{
	
	@Override
	public int compare(Movie o1, Movie o2) {
		// TODO Auto-generated method stub
		
		return o1.getDirector().compareTo(o2.getDirector());
	}

}
