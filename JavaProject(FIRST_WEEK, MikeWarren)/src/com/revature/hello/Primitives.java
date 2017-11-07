package com.revature.hello;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Primitives {
	public static void main(String[] args) {
		/* 
		 * Primitive Data types
		 * • A primitive data type is simply a data type that isn't an object
		 * • Has a static memory allocation
		 */
		
		int i;
		System.out.println(i = Integer.MAX_VALUE);			// 32 bits 
		double d;											
		System.out.println(d = Double.MAX_VALUE);			// 64 bits
		float f;
		System.out.println(f = Float.MAX_VALUE); 			// 32 bits
		char c;												
		System.out.println((int)(c = Character.MAX_VALUE)); 		// 16 bits
		byte b;												
		System.out.println(b = Byte.MAX_VALUE);				// 8 bits
		short s; 
		System.out.println(s = Short.MAX_VALUE); 			// 16 bits
		long l;
		System.out.println(l = Long.MAX_VALUE); 			// 64 bits
		boolean bool;
		/* Boolean size is up for debate. Arguably, it is 1 bit, either true or false;
		 * However, the memory that is reserved fro a boolean, is closer to 8 bits
		 */
		System.out.println((int)(++c));	// NOTE: No negative character
		/* Casting: 
		 * • process of changing an object's/variable's data type
		 * • Two forms of casting:
		 * 	• explicit casting: where you must explicitly state the data type you are casting to.
		 * 	• implicit casting: data type is casted automatically for you.
		 * 
		 * Small rule of thumb, if you are changing from a data type that takes less memory than the newer data type, 
		 * 	it will be implicit.
		 */
		d = i;		// implicit
		i = (int)d;	// explicit
		f = (float)d;
		d = f;
		l = (long)d;
		d = l;
		b = (byte)c;
		c = (char)b;
	}
}
