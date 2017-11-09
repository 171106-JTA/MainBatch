package d1.revature.hello;

public class Primitives {
	
	public static void main(String[] args) {
		/*
		 * Primitive Datatypes
		 * -A primitive datatype is simply a datatype that isn't an object.
		 * -Has a static memory allocation
		 */
		
		int i;
		System.out.println(i = Integer.MAX_VALUE);	//32 bits
		double d;									
		System.out.println(d = Double.MAX_VALUE);	//64 bits
		float f;
		System.out.println(f = Float.MAX_VALUE);    //32 bits
		char c;
		System.out.println((int)(c = Character.MAX_VALUE));//16 bits
		byte b;	
		System.out.println(b = Byte.MAX_VALUE); //8 bit
		short s;
		System.out.println(s = Short.MAX_VALUE); //16 bits
		long l;
		System.out.println(l = Long.MAX_VALUE); //64 bits
		boolean bool;
		/*
		 * Boolean size is up for debate. Arguabably it is 1 bit, either true or false;
		 * However, the memory that is reserved for a boolean, is closer 8 bits.
		 */
		
		System.out.println((int)(++c)); //Note, no negative characters
		
		/*
		 * Casting
		 * -Casting is the process of changing an object/variable's datatype.
		 * -There are two forms of casting
		 * -Explicit casting, where you must explicitly state the datatype you are
		 * casting too.
		 * -Implicit Casting, where a datatype is casted automatically for you.
		 * 
		 * Small rule of thumb, if you are changing from a datatype that takes less memory
		 * than the newer datatype, it will be implicit.
		 */
		
		d = i;		//implicit
		i = (int)d; //explicit
		f = (float)d;
		d = f;
		l = (long)d;
		d = l;
		b = (byte)c;
		c = (char)b;
		
	}

}
