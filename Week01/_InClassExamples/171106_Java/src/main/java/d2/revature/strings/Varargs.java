package d2.revature.strings;

public class Varargs {

	public static void main(String[] args) {
		Varargs v = new Varargs();
		v.addition(2,3,4,123,23,15,12,(int)'c', (int)'C');

	}
	
	public int addition(int...x){
		
		/*
		 * The method above uses varargs as a parameter.
		 * This allows for a dynamic amount of input that is stored as an 
		 * array upon processing.
		 * 
		 * We can use an enhanced for loop to iterate through the array and 
		 * add all numbers together.
		 */
		
		int result = 0;
		for(int i : x){
			System.err.println(result + " + " + i + " = " + (result += i));
		}
		

		
		return result;
	}

}
