package com.revature.core;

public final class StringFormater {
	public static String currency(float number) {
		float epsilon = 0.004f; // 4 tenths of a cent
		
		 if (Math.abs(Math.round(number) - number) < epsilon) {
		     return String.format("%10.0f", number); // sdb
		 } else {
		     return String.format("%10.2f", number); // dj_segfault
		 }
	}
}
