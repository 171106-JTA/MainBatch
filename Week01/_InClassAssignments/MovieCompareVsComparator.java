package com.revature.collections;

import java.util.ArrayList;
import java.util.List;

public class MovieCompareVsComparator {
	
	
	public static void main(String[] args) {
		List<Movie> m1 = new ArrayList<>();
		//List<Movie2> m2 = new ArrayList<>();
		
		m1.add(new Movie("Inception", "Christopher Nolan", 2011));
		m1.add(new Movie("Star Wars", "George Lucas", 1977));
		m1.add(new Movie("Deadpool", "Tim Miller", 2016));
		m1.add(new Movie("1st Movie in History", "Person", 1));
		
		/*m2.add(new Movie2("Inception", "Christopher Nolan", 2011));
		m2.add(new Movie2("Star Wars", "George Lucas", 1977));
		m2.add(new Movie2("Deadpool", "Tim Miller", 2016));
		*/
		m1.sort(null);
		//System.out.println(m1);
		
		//m2.sort(null);
		//System.out.println(m1);
		
		System.out.println("===========comparable==========");//sort by year
		for(Movie m : m1){
			System.out.println(m);
		}
		
		System.out.println("==========comparator===========");//sort by director name
		m1.sort(new Movie2());
		for(Movie m: m1){
			System.out.println(m);
		}
	}

}
