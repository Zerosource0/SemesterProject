/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facades.FlightSearchFacade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author marcj_000
 */
@Path("flightinfo")
public class FlightResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FlightResource
     */
    public FlightResource() {
    }

    /**
     * Retrieves representation of an instance of rest.FlightResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{from}/{date}/{seats}")
    @Produces("application/json")
    public String getJson(@PathParam("from") String from, @PathParam("date") String date, @PathParam("seats") String seats) throws UnsupportedEncodingException, MalformedURLException, IOException 
    {
        FlightSearchFacade facade = new FlightSearchFacade();
        
        
        //String url = "http://angularairline-plaul.rhcloud.com/api/flightinfo/" + from + "/" + date + "/" + seats;
        //for each ariline do this;
        return facade.read(from, date, Integer.SIZE);
        
            
    }

}
