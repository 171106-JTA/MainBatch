package com.rebuild.object;


public class RebuildObjectTools {
	
	
	public static boolean convertFromStringToBoolean(String value) {
		if(value.equalsIgnoreCase("true"))
			return true; 
		if(value.equalsIgnoreCase("yes"))
			return true; 
		return false; 
	}
}
