package d3.revature.commandline;

public class CommandLineExample {
	/*
	 * String[] args is the parameter used for user input on the commandline More
	 * common with C C++ Apps but java sups has well.
	 * 
	 */

	public static void main(String[] args) {
		short result = 0;
		System.out.println("Number of arfs: " + args.length);
		for (short i = 0; i < args.length; i++) {
			System.out.println("Arg 0" + (i + 1) + ": " + args[i]);
		}
	}
}
