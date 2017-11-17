package d3.revature.commandline;

public class CommandLineExample {
<<<<<<< HEAD
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
=======

	/*
	 * String[] args is the paremeter used for user input on the commandline.
	 * More common with C C++ applications, but java supports it as well.
	 */
	/*
	 * Steps for commandline success!
	 * Easy way:
	 *  run > run configurations > (click the arguments tab) > write arguments separated by spaces
	 * 
	 * The Brave way:
	 * 1. Navigate to the "src" folder of your project via commandPrompt (Or terminal)
	 * 2. Invoke compiler via: "javac"
	 * 2.5 e.g. javac d3/revature/commandline/filename.java
	 * 3.Next execute the application using java.
	 * 3.5 e.g. java d3.revature.commandline.filename arg1 arg2 arg3 arg4
	 */
	public static void main(String[] args) {

		
		System.out.println("Number of args: " + args.length);
		for(int i = 0; i < args.length; i++){
			System.out.println("Arg #" + (i+1) + ": " + args[i]);
		}
	}

>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
}
