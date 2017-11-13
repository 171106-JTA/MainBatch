package com.revature.example;

public class HelloWorld {
	
	public static void main(String[] args) {
		System.out.println("Hello world");
	}
	
	public String returnHello() {
		return "Hello world";
	}
	
	/*
	 * The maven lifecycle is encompassed of multiple phases.
	 * These phases are executed in order every time you choose to build and then deploy your app.
	 * 0. Validate
	 * 	• Confirms all required resources are available for building project.
	 * 1. clean
	 * 	• simply deletes the 'target' directory
	 * 	• the target directory is where maven builds, compiles, and stores your functional application.
	 * 2. compile
	 * 	• This compiles your Java files.
	 * 3. Test
	 * 	• Maven executes all of your unit tests that you have wrote.
	 * 	• If any tests fail, the rest of the lifecycle is aborted.
	 * 4. Package
	 * 	• Simply takes all of the class files and packages the into the configured type. In this case, JAR.
	 * 	• To the point, package creates our JAR file.
	 * 5. Verify
	 * 	• Performs checks to ensure integration tests are passing.
	 * 6. Install
	 * 	• Installs the application to your local repository.
	 * 7. Deploy
	 * 	• Deploys the application to a remote repository.
	 * 
	 */
}
