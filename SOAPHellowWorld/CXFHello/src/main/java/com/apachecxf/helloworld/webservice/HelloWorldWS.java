package com.apachecxf.helloworld.webservice;

import javax.jws.WebService;

@WebService(targetNamespace = "http://helloworldcxfapache.com/webservices/definitions")
public interface HelloWorldWS {

    public void hello(String name);

}

