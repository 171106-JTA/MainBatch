package com.revature.client.soap;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.revature.exceptions.soap.SOAPException;
import com.revature.ws.IHelloWorld;

import java.net.MalformedURLException;
import java.net.URL;

public class HelloWorldClient {

	public static void main(String[] args) {

		try {
			URL url = new URL("http://localhost:8080/ws/hello?wsdl");
			QName qname = new QName("http://ws.revature.com/", "HelloWorldService");

			Service service = Service.create(url, qname);
			IHelloWorld hello = service.getPort(IHelloWorld.class);
			
			System.out.println(hello.getHelloWorldAsString("main"));
			
		} catch (MalformedURLException | SOAPException e) {
			e.printStackTrace();
		}

	}
}
