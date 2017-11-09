package d3.revature.wrapper;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ScannerExample {

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

}
