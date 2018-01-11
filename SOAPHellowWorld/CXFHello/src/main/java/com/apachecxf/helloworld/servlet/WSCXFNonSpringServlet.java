package com.apachecxf.helloworld.servlet;

import com.apachecxf.helloworld.webservice.HelloWorldWSImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;

public class WSCXFNonSpringServlet extends CXFNonSpringServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void loadBus(ServletConfig servletConfig) {
        super.loadBus(servletConfig);

        System.out.println("---------WSCXFNonSpringServlet---------");
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        Endpoint.publish("/HelloWorldWSImpl", new HelloWorldWSImpl());
    }
}