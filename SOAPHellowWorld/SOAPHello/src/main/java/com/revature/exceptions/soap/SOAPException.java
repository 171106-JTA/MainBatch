package com.revature.exceptions.soap;

import javax.xml.ws.WebFault;

import com.revature.fault.soap.HelloFault;

@WebFault(name = "HelloWorldFault", targetNamespace="http://ws.revature.com/")
public class SOAPException extends Throwable {
	
	private HelloFault fault;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1742749222236931796L;

	public SOAPException(HelloFault fault, Exception cause) {
		super(fault.getMessage(), cause);
		this.fault = fault;
	}
	
	public SOAPException(HelloFault fault) {
		super(fault.getMessage());
		this.fault = fault;
	}
	
	public HelloFault getFault() {
		return fault;
	}
	
}
