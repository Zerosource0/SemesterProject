/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import exceptions.BadParameterException;
import exceptions.NotFoundException;
import facades.FlightSearchFacade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.ws.rs.Consumes;
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
    
    FlightSearchFacade facade;
    
    Gson gson = new Gson();
   
    public FlightResource() {
        facade = FlightSearchFacade.getInstance();
    }

 //String url = "http://angularairline-plaul.rhcloud.com/api/flightinfo/" + from + "/" + date + "/" + seats;
        //for each ariline do this;
    
    

    
    @GET
    @Path("/{from}/{date}/{seats}")
    @Produces("application/json")
    public String getJsonFrom(@PathParam("from") String from, @PathParam("date") String date, @PathParam("seats") int seats) throws UnsupportedEncodingException, MalformedURLException, IOException 
    {

        if(from.equals("")|| date.equals("")){
            throw new BadParameterException("Missing input or badly formatted");
        }
        if(seats<=0){
            throw new BadParameterException("Select a number of seats");
        }
        
        String result = facade.getJsonFromAirlinesFrom(from, date, seats);  
        
        
        if(result.contains("httpError")){
            throw new NotFoundException("No flights available");
        }
        
        return result;
    }
    
    @GET
    @Path("/{from}/{to}/{date}/{seats}")
    @Produces("application/json")
    public String getJsonTo(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("seats") int seats) throws UnsupportedEncodingException, MalformedURLException, IOException 
    {
        
           if(from.equals("")|| to.equals("")|| date.equals("")){
             throw new BadParameterException("Missing input or badly formatted");
         }
         if(seats<=0){
             throw new BadParameterException("Select a number of seats");
         }
         
                 String result = facade.getJsonFromAirlinesFromTo(from,to, date, seats);  

         
         if(result == null){
            throw new NotFoundException("This flight is not available");
        }
         
        return result;            
    }

}
