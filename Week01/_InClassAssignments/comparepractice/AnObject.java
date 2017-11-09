package com.revature.comparepractice;

import java.util.Comparator;

public class AnObject implements Comparable<AnObject>, Comparator<AnObject>{
	
	private int something;
	private String somethingElse;
	private String nothing;
	
	public AnObject(int something, String somethingElse, String nothing) {
		this.something = something;
		this.somethingElse = somethingElse;
		this.nothing = nothing;
	}
	public int getSomething() {
		return something;
	}
	public void setSomething(int something) {
		this.something = something;
	}
	public String getSomethingElse() {
		return somethingElse;
	}
	public void setSomethingElse(String somethingElse) {
		this.somethingElse = somethingElse;
	}
	public String getNothing() {
		return nothing;
	}
	public void setNothing(String nothing) {
		this.nothing = nothing;
	}
	@Override
	public int compare(AnObject o1, AnObject o2) {

			return o1.getSomethingElse().compareTo(o2.getSomethingElse());
	}
	@Override
	public int compareTo(AnObject o) {

		if(this.getSomething() < o.getSomething()) {
			return -1;
		}
		else if(this.getSomething() > o.getSomething()) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
