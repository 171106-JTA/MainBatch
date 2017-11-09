package com.revature.comparepractice;

import java.util.Comparator;

public class AnotherObject implements Comparator<AnotherObject> {
	private int something;
	private String somethingElse;
	private String nothing;
	
	public AnotherObject(int something, String somethingElse, String nothing) {
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
	public int compare(AnotherObject o1, AnotherObject o2) {

			return o2.getSomethingElse().compareTo(o1.getSomethingElse());
	}
}
