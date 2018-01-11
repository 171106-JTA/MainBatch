package com.revature.fault.soap;

public class HelloFault {

	public static final int TEST_CODE = 1234;

	private int code;
	private String message;

	public HelloFault(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
