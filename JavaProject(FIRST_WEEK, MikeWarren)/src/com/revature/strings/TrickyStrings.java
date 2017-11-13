package com.revature.strings;

public class TrickyStrings {

	public static void main(String[] args) {
		/* 
		 * expressions are evaluated left-to-right; // as soon as String is encountered, everything else is treated as
		 * String.
		 */
		System.out.println(1 + 2 + 3 + 4); // 10
		System.out.println(1 + 2 + 3 + "4"); // 64
		System.out.println(1 + 2 + "3" + "4"); // 334
		System.out.println(1 + "2" + 3 + "4"); // 1234
		System.out.println(1 + "2" + 3 + 4); // 1234
		
		
	}

}
