package d3.revature.wrapper;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ScannerExample {
<<<<<<< HEAD
	public static void main(String[] args) {
		/* Scanner is a class used to parse Strings.
		 * e.g. parse numbers/letters. etc from strings easily. 
		 * More commonly used for user input in console application 
		 * by default, it delimits by ' '<- whitespace
		 */
		
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		System.out.println("You entered" + s);
		StringTokenizer st = new StringTokenizer(s);
		String token;
		
		while(st.hasMoreTokens()) {
			token = st.nextToken();
			if(Integer.parseInt(s) == 0) {
				break;
			}
		}
	}
=======

	public static void main(String[] args) {
		/*
		 * Scanner is a class used to parse Strings.
		 * e.g. parse numbers/letters/etc from strings easily.
		 * 
		 * More commonly used for user input in console applications.
		 * By default, it delimits by ' ' <-(space)
		 */
		
		//system.in provides an inputStream that takes user input.
		Scanner scan = new Scanner(System.in);
		
		while(true){
			System.out.println("Please input a number!!");
			
			String s = scan.nextLine();
			String token = "";
			int input = -1;
			
			StringTokenizer st = new StringTokenizer(s);
			while(st.hasMoreTokens()){
				token = st.nextToken();
				if(Integer.parseInt(token) == 0){
					input = 0;
					break;
				}
			}
			
			System.out.println(s);
			if(input==0){
				break;
			}
			
		}
	}

>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
}
