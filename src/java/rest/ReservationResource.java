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
import entity.Passenger;
import entity.Reservation;
import entity.User;
import exceptions.BadParameterException;
import facades.ReservationFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
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
    private UserFacade uf;
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        uf = UserFacade.getInstance();
    }

    
  @POST
  @Path("/{airline}/{flightID}/{flightDate}/{numberOfSeats}/{travelTime}/{totalPrice}/{origin}/{destination}/{passengers}")
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
          @PathParam("flightDate") String from, 
          @PathParam("passengers") String passengers) throws JOSEException 
  {


    if(flightDate.equals("")|| numberOfSeats<=0){
        throw new BadParameterException("Make sure you enter something");
    }
    
    ReservationFacade rf = new ReservationFacade();
    
    User user = uf.getCurrentUser();
    if(user!=null){
    
    List<Passenger> passengerList = new ArrayList<>();
    
    String[] split = passengers.split(",");
    
      for (String s : split) {
          String[] a = s.split("-");
          passengerList.add(new Passenger(a[0],a[1]));
      }
    
    String response = rf.reserve(user, airline, flightID, flightDate, numberOfSeats, travelTime, totalPrice, origin, destination, passengerList);
    
    return response;
    }
    throw new BadParameterException("Make sure you are logged in!" + user);
  }
}
