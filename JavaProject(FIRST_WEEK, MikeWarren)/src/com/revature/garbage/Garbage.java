package com.revature.garbage;

public class Garbage {
	public static void main(String[] args) {
		/*
		 * Java was hailed as a safe language by managing memory for the user. This is different from other languages 
		 * like C/C++ where the user had to handle their memory themselves.
		 * The JVM has something called the GarbageCollector, which will handle the cleaning up of an dead references/objects.
		 * 
		 * Three ways an object is queued up for garbage collection:
		 * 	• Object goes out of scope (e.g. code block ends in which the object was created in. )
		 * 	• Object loses all references.
		 * 	• Reference set to null
		 * 	• Object reference points to different object. Previous object will get collected.
		 * 
		 * You *cannot* force garbage collection. You can only request it via System.gc() .
		 */
		
		for (int i = 0; i < 100000000; i++)
		{
			EmptyCan can = new EmptyCan(i);
		}
	}
	
}
