package d3.revature.wrapper;

public class ShortCircuit {
<<<<<<< HEAD
	/*
	 * Operators for AND & OR ARE : & and |
	 * Technically & and | are called bitwise operators. 
	 */
	public static boolean returnTrue() {
		System.out.println("return True");
		return true;
	}
	public static boolean returnFalse() {
		System.out.println("return False");
		return false;
	}
=======

	public static void main(String[] args) {
		/*
		 * operators for AND & OR are: & and |
		 * Technically & and | are called bitwise operators.
		 */
		
		if(returnFalse() & returnTrue()){
			
		}
		System.out.println("=======================");
		if(returnTrue() | returnFalse()){
			
		}
		System.out.println("=======================");
		
		/*
		 * There exists another way to use and n' or.
		 * That is with short-circuit operators.
		 * Represented as &&(and) n' ||(or)
		 */
		
		if(returnFalse() && (returnTrue() || returnFalse()) && returnTrue()){
			
		}
		System.out.println("=======================");
		if(returnTrue() || returnFalse()){
			
		}
		System.out.println("=======================");
		
	}
	public static boolean returnTrue(){
		System.out.println("Returned true");
		return true;
	}
	public static boolean returnFalse(){
		System.out.println("Returned false");
		return false;
	}

>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
}
