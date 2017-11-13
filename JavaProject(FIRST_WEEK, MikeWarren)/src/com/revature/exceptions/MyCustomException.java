package com.revature.exceptions;

public class MyCustomException extends Exception {

	@Override
	public String getMessage() {
		return "This was custom Exception";
	}
	
}
