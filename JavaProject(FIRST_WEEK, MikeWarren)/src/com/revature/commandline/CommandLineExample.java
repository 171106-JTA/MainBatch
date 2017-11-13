package com.revature.commandline;

public class CommandLineExample {
	/*
	 * String[] args is the parameter used for user input on the command line
	 * More common with C/C++ applications, but Java supports it as well.
	 * 
	 */
	/*
	 * Steps for command line success!
	 * Easy way: 
	 * 	run > run configurations > (click the arguments tab) > write arguments separated by spaces
	 * 
	 * The brave way (the way the end user of your command line app has to run it)
	 * 1) Navigate to the "src" folder of your project via CMD/shell
	 * 2) Invoke compiler via : "javac"
	 * 2.5) e.g. javac com/revature/commandline/[FileName].java
	 * 3) Next execute the application using Java.
	 * 3.5) e.g. java com.revature.commandline.[filename] arg1 arg2 arg3 arg4
	 */
	public static void main(String[] args) {
		int result = 0;
		System.out.println("Number of args: " + args.length);
		for (int i = 0; i < args.length; i++)
		{
			System.out.println("Arg #" + (i+1) + ": " + args[i]);
		}
	}
}
