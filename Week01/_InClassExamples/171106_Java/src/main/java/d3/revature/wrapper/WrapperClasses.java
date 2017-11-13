package d3.revature.wrapper;

public class WrapperClasses {

	public static void main(String[] args) {
		/*
<<<<<<< HEAD
		 * A wrapper class is the Object representation of a primitive datatype of that
		 * wraps around the datatype
		 * All wrapper classes are simply the full name of a primitive value   Camel Case 
		 * */
		
		Integer i = new Integer(5);
		System.out.println(i);
		System.out.println(i+12);
		/* Auto boxing & auto unboxing. 
		 * - Autoboxing is simply the implicit conversion of a primitive datatype to its wrapper class
		 * - Auto-unboxing is the implicit conversion of a wrapper class to its primitive datatype
		 * 
		 */
=======
		 * A wrapper class is the Object representation of a primitive datatype that
		 * wraps around the datatype.
		 * All wrapper classes are simply the full name of a primitive value following Camel Case.
		 */
		
		Integer i = 5; //Autoboxing by instantiating
		
		int j = i; //auto-unboxing
		System.out.println(i);
		System.out.println(i + i);
		
		
		
		Double d = new Double(4.4);
		/*
		 * Integer
		 * Double
		 * Float
		 * Short
		 * Long
		 * Byte
		 * Character
		 * Boolean
		 * 
		 */
		
		/*
		 * Autoboxing and auto-unboxing
		 * -Autoboxing is simply the implicit conversion of a primitive datatype to its wrapper class.
		 * -Auto-unboxing is the implicit conversion of a wrapper class to its primitive datatype.
		 */
		
		WrapperClasses wc = new WrapperClasses();
		wc.doMath(new Double(5.5),new Double(3.3));

	}
	
	public void doMath(Double x, Double y){
		System.out.println(x/y);
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
	}

}
