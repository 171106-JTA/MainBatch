package com.revature.garbage;

public class EmptyCan {
	private int _id;
	
	public EmptyCan(int id) {
		this._id = id;
		System.out.println(id  + " CREATED");
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("\t\t" + _id + " COLLECTED");
	}
}
