package com.apachecxf.helloworld.webservice;

import javax.jws.WebService;

@WebService(
        portName = "HelloWorldWSPort",
        serviceName = "HelloWorldWSService",
        targetNamespace = "http://helloworldcxfapache.com//wsdl",
        endpointInterface = "com.apachecxf.helloworld.webservice.HelloWorldWS")
public class HelloWorldWSImpl implements HelloWorldWS {

    public void hello(String name) {

        System.out.println("HELLO ["+name+"]");
    }

}