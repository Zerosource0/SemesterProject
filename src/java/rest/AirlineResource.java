/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.AirlinesUrl;
import exceptions.BadParameterException;
import exceptions.NotFoundException;
import facades.FlightSearchFacade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Marc
 */
@Path("airline")
public class AirlineResource {

    @Context
    private UriInfo context;
    FlightSearchFacade facade;
    Gson gson = new Gson();
    /**
     * Creates a new instance of AirlineResource
     */
    public AirlineResource() {
        facade = new FlightSearchFacade();
    }

    /**
     * Retrieves representation of an instance of rest.AirlineResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        List<AirlinesUrl> airlines = facade.getAirlines();
        JsonArray jArray = new JsonArray();
        for (AirlinesUrl airline : airlines) {
            JsonObject json = new JsonObject();
            json.addProperty("name", airline.getName());
            json.addProperty("url", airline.getAirlineUrl());
            jArray.add(json);
        }
        return gson.toJson(jArray);
    }

    @GET
    @Path("/{airline}/{from}/{date}/{seats}")
    @Produces("application/json")
    @Consumes("aaplication/json")
    public String getJsonFromSpecific(@PathParam("airline") String airline, @PathParam("from") String from, @PathParam("date") String date, @PathParam("seats") int seats) throws UnsupportedEncodingException, MalformedURLException, IOException 
    {

        if(from.equals("")|| date.equals("")){
            throw new BadParameterException("Missing input or badly formatted");
        }
        if(seats<=0){
            throw new BadParameterException("Select a number of seats");
        }
        
        String result = facade.getJsonFromSpecificAirlineFrom(airline, from, date, seats);
        
        
        if(result.contains("httpError")){
            throw new NotFoundException("No flights available");
        }
        
        return result;
    }
    
    @GET
    @Path("/{airline}/{from}/{to}/{date}/{seats}")
    @Produces("application/json")
    public String getJsonFromSpecificTo(@PathParam("airline") String airline, @PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("seats") int seats) throws UnsupportedEncodingException, MalformedURLException, IOException 
    {

        if(from.equals("")|| date.equals("")){
            throw new BadParameterException("Missing input or badly formatted");
        }
        if(seats<=0){
            throw new BadParameterException("Select a number of seats");
        }
        
        String result = facade.getJsonFromSpecificAirlineFromTo(airline, from, to, date, seats);
        
        
        if(result.contains("httpError")){
            throw new NotFoundException("No flights available");
        }
        
        return result;
    }
    
}
