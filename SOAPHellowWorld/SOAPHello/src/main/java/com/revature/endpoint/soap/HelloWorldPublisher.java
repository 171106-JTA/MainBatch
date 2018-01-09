package com.revature.endpoint.soap;

import javax.xml.ws.Endpoint;

import com.revature.ws.HelloWorld;

public class HelloWorldPublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/ws/hello", new HelloWorld());
	}
}
