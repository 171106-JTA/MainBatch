package com.revature.endpoint.soap;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class HelloFaultHandler implements SOAPHandler<SOAPMessageContext> {
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		System.out.println("Client : handleMessage()......");
		
		System.err.println(context.getMessage());
		return false;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.err.println(context.getMessage());
		return false;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
