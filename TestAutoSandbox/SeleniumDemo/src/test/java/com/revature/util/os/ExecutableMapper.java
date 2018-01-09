package com.revature.util.os;

public class ExecutableMapper {

	private static final String WIN_EXE = ".exe";
	
	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	public static String getExecutable(String base) {
		if(isWindows()) 
			return base + WIN_EXE;
		if(isMac())
			return base;
		if(isUnix())
			return base;
		if(isSolaris())
			return base;
		return null;
	}

}
