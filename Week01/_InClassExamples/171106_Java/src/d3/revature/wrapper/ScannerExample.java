package d3.revature.wrapper;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ScannerExample {
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
}
