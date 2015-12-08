/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import entity.User;
import exceptions.BadParameterException;
import facades.ReservationFacade;
import facades.UserFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author marcj_000
 */
@Path("reservation")
public class ReservationResource {
    private ReservationFacade rf = new ReservationFacade();
    private UserFacade uf = new UserFacade();
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
    }

    /**
     * Retrieves representation of an instance of rest.ReservationResource
     * @return an instance of java.lang.String
     */
    @GET
    //@Path("/{airline}/{flightID}/{flightDate}/{numberOfSeats}/{travelTime}/{totalPrice}/{origin}/{destination}")
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
  @POST
  @Path("/{airline}/{flightID}/{flightDate}/{numberOfSeats}/{travelTime}/{totalPrice}/{origin}/{destination}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String newReservation(
          @PathParam("airline") String airline,
          @PathParam("flightID") String flightID,
          @PathParam("flightDate") String flightDate,
          @PathParam("numberOfSeats") Integer numberOfSeats,
          @PathParam("travelTime") Integer travelTime,
          @PathParam("totalPrice") Integer totalPrice,
          @PathParam("origin") String origin,
          @PathParam("destination") String destination,
          @PathParam("flightDate") String from) throws JOSEException 
  {
    
    
    
    
    
    if(flightDate.equals("")|| numberOfSeats<=0){
        throw new BadParameterException("Make sure you enter something");
    }
    
    ReservationFacade rf = new ReservationFacade();
    User u;
    Boolean exists=false;
    if (uf.getUserByUserId("user1")==null) u = new User("user1", "test");
    else 
    {
        exists=true;
        u=uf.getUserByUserId("user1");
    }
    
    Boolean sucess=rf.newReservation(exists, u, airline, flightID, flightDate, numberOfSeats, travelTime, totalPrice, origin, destination);
    
    if(sucess){
        System.out.println("good");
        return "it's ok";
    } else{ 
         System.out.println("bad");
         return "it's not ok";
    }
    
  }
}
