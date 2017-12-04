package com.revature.model;

public class Constants {

	private static final String configsDir = "/resources/config/";
	public static final String servletConfig = configsDir + "servlet.properties";
	
	public static final String userKey = "User";
	public static final String passKey = "Pass";
	public static final int AuthFail = -1;
	
	public static final String LoginFail = "Failed to Login. Username does not exist";
	public static final String SessionType = "SessionType";
	
	public static final String ToFile = "ToFile";
	public static final String FromFile = "FromFile";
	public static final int BasicSession = 0;
	public static final String RegFail = "Failed to register. Username already exists";
	public static final int RegSuccess = 0;
	public static final int MAX_PASS = 32;	
}
