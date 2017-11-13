package com.revature.strings;

public class FunWithStrings {

	public static void main(String[] args) {
		
		
		/* 
		 * There exist way in Java to handle MUTABLE Strings. This is done by wrapping a string in a class called 
		 * StringBuffer or StringBuilder.
		 * These two classes allow us to change strings without having to create a new string object.
		 * As result, theses are faster with string manipulation. 
		 */
		
		StringBuilder sBuild = new StringBuilder("bobbert");
		StringBuffer sBuff = new StringBuffer("bobbert");
		
		System.out.println("sBuild:   " + sBuild + "|" + System.identityHashCode(sBuild));
		sBuild.append("s");
		System.out.println("sBuild:   " + sBuild + "|" + System.identityHashCode(sBuild));
		
		/* The difference between StringBufer,StringBuilder: 
		 * StringBuffer is threadsafe (synchronized)
		 * StringBuilder is NOT threadsafe.
		 */
		
		String string = "bobberts";
		
		int size = 10000000;
		System.out.println("looping " + size + " times!");
		long curtime;
		
		curtime = System.currentTimeMillis();
		for (int i = 0; i < size; i++)
		{
			string += 'a';
		}
		System.out.println("String: " + (System.currentTimeMillis() - curtime));
		curtime = System.currentTimeMillis();
		for (int i = 0; i < size; i++)
		{
			sBuild.append('a');
		}
		System.out.println("String Builder : " + (System.currentTimeMillis() - curtime));
		curtime = System.currentTimeMillis();
		for (int i = 0; i < size; i++)
		{
			sBuff.append('a');
		}
		System.out.println("String Buffer : " + (System.currentTimeMillis() - curtime));
					
	}

}
