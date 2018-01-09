package com.revature.ws;

import javax.jws.WebService;

import com.revature.exceptions.soap.SOAPException;
import com.revature.fault.soap.HelloFault;

@WebService(endpointInterface = "com.revature.ws.IHelloWorld")
public class HelloWorld implements IHelloWorld {

	@Override
	public String getHelloWorldAsString(String name) throws SOAPException {
		HelloFault fault;
		if (name == null) {
			fault = new HelloFault(HelloFault.TEST_CODE, "Message cannot be null");
			throw new SOAPException(fault);
		}

		try {
			if (name.length() > 10) {
				throw new Exception("Message is too long");
			}
		} catch (Exception ex) {
			fault = new HelloFault(HelloFault.TEST_CODE, "Server returned 500 error");
			throw new SOAPException(fault, ex);
		}
		return "Hello from JAX-WS SOAP " + name;

	}

}
