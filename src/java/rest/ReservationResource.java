/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

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

    Gson gson = new Gson();

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        uf = UserFacade.getInstance();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllReservations() {
        List<String> roles = uf.getCurrentUser().getRolesAsStrings();
        Boolean switchCase=true;
        for (String role : roles) 
        {
            if (role.equals("admin")) switchCase=false;
            
        }
        if (switchCase==true) return getReservationsByUser();
        
        List<User> users = uf.getUsersWithReservations();
//        for (User user : users) {
//            System.out.println(user.toString());
//        }
        JsonObject userJObject = new JsonObject();
        List<Reservation> l = uf.getReservationsWithUsers();
        for (User user : users) {
            for (Reservation reservation : l) {
                if (user.getUserName().equals(reservation.getUser().getUserName())) user.addReservation(reservation);
            }
           // System.out.println(user.toString());
//            List<Passenger> pass = new ArrayList<>();
//            Reservation re = new Reservation(user, "shity", "shity", "shity", 432, 432, 342, "shity", "shity", pass);
//            user.addReservation(re);
           // List<Reservation> reservations = user.getReservations();
            
            JsonArray jArray = new JsonArray();

            for (Reservation r : user.getReservations()) {
                System.out.println(r.toString());
                JsonObject json = new JsonObject();
                json.addProperty("flightID", r.getFlightID());
                json.addProperty("flightDate", r.getFlightDate());
                json.addProperty("origin", r.getOrigin());
                json.addProperty("destination", r.getDestination());
                json.addProperty("reserveeName", user.getFirstName() + " " + user.getLastName());

                JsonArray passengers = new JsonArray();

                for (Passenger passenger : r.getPassengers()) {
                    JsonObject p = new JsonObject();
                    p.addProperty("firstName", passenger.getFirstName());
                    p.addProperty("lastName", passenger.getLastName());
                    passengers.add(p);
                }
                json.add("passengers", passengers);
                jArray.add(json);
            }
            userJObject.addProperty("user", user.getFirstName() + " " + user.getLastName());
            userJObject.add("reservations",jArray);
        }
        return gson.toJson(userJObject);
    }

    
//  @POST
//  @Path("/{airline}/{flightID}/{flightDate}/{numberOfSeats}/{travelTime}/{totalPrice}/{origin}/{destination}/{passengers}")
//  @Consumes(MediaType.APPLICATION_JSON)
//  @Produces(MediaType.APPLICATION_JSON)
//  public String newReservation(
//          @PathParam("airline") String airline,
//          @PathParam("flightID") String flightID,
//          @PathParam("flightDate") String flightDate,
//          @PathParam("numberOfSeats") Integer numberOfSeats,
//          @PathParam("travelTime") Integer travelTime,
//          @PathParam("totalPrice") Integer totalPrice,
//          @PathParam("origin") String origin,
//          @PathParam("destination") String destination,
//          @PathParam("flightDate") String from, 
//          @PathParam("passengers") String passengers) throws JOSEException 
//  {
//    if(flightDate.equals("")|| numberOfSeats<=0){
//        throw new BadParameterException("Make sure you enter something");
//    }
//    return null;
//  }
        @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getReservationsByUser() {

        User user = uf.getCurrentUser();

        List<Reservation> reservations = user.getReservations();

        JsonArray jArray = new JsonArray();

        for (Reservation r : reservations) {
            JsonObject json = new JsonObject();
            json.addProperty("flightID", r.getFlightID());
            json.addProperty("flightDate", r.getFlightDate());
            json.addProperty("origin", r.getOrigin());
            json.addProperty("destination", r.getDestination());
            json.addProperty("reserveeName", user.getFirstName() + " " + user.getLastName());

            JsonArray passengers = new JsonArray();

            for (Passenger passenger : r.getPassengers()) {
                JsonObject p = new JsonObject();
                p.addProperty("firstName", passenger.getFirstName());
                p.addProperty("lastName", passenger.getLastName());
                passengers.add(p);
            }
            json.add("passengers", passengers);
            jArray.add(json);
        }

        return gson.toJson(jArray);

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
            @PathParam("passengers") String passengers) throws JOSEException {

        if (flightDate.equals("") || numberOfSeats <= 0) {
            throw new BadParameterException("Make sure you enter something");
        }

        ReservationFacade rf = new ReservationFacade();

        User user = uf.getCurrentUser();
        if (user != null) {

            List<Passenger> passengerList = new ArrayList<>();

            String[] split = passengers.split(",");

            for (String s : split) {
                String[] a = s.split("-");
                passengerList.add(new Passenger(a[0], a[1]));
            }

            String response = rf.reserve(user, airline, flightID, flightDate, numberOfSeats, travelTime, totalPrice, origin, destination, passengerList);

            return response;
        }
        throw new BadParameterException("Make sure you are logged in!" + user);
    }
}
