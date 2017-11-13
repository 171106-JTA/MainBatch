package com.revature.strings;

public class Donut {
	private String type;
	static int donutCount = 0;
	public Donut(String type) { 
		this.type = type;
		donutCount++;
	}
	public String getType() {
		return type;
	}
	public static int getDonutCount() {
		return donutCount;
	}
	
	
}
