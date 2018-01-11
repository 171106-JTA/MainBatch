package com.apachecxf.helloworld.resource;

import com.apachecxf.helloworld.bean.HelloWorldBean;

import javax.ws.GET;
import javax.ws.POST;
import javax.ws.Path;
import javax.ws.Produces;
import javax.ws.PathParam;
import javax.ws.Consumes;
import javax.ws.core.MediaType;
import java.util.logging.Logger;

/**
 * Created by valerie on 12/28/15.
 */
@Path("/hi")
public class HelloWorldResource {
    private static final Logger logger = Logger.getLogger(HelloWorldResource.class.getName());

    @GET
    @Path("/ping/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping(@PathParam("name") String name) {
        return "pong " + name;
    }

    @Path("/xml")
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public String postXml(HelloWorldBean helloWorldBean) {

        logger.info(helloWorldBean.toString());

        return helloWorldBean.toString();
    }

    @Path("/json")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String postJson(HelloWorldBean helloWorldBean) {

        logger.info(helloWorldBean.toString());

        return helloWorldBean.toString();
    }

}

