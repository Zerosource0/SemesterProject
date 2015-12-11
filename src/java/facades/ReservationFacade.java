/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import deploy.DeploymentConfiguration;
import entity.Passenger;
import entity.Reservation;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adam
 */
public class ReservationFacade {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    private User currentUser=null;
    
    Gson gson = new Gson();
    
    public ReservationFacade ()
    {
        
    }
    
    public ReservationFacade (User currentUser)
    {
        this.currentUser=currentUser;
    }
    
    
    public String reserve(User user, String airline, String flightId, String flightDate, int numberOfSeats, int travelTime, int totalPrice, String origin, String destination, List<Passenger> passengersl){
    
        EntityManager em = emf.createEntityManager();
        
        Reservation reservation = new Reservation(user, airline, flightId, flightDate, numberOfSeats, travelTime, totalPrice, origin, destination, passengersl);
        try{
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
            
        }finally{
            em.close();
        }
        
        JsonObject json = new JsonObject();
        json.addProperty("flightID", flightId);
        json.addProperty("origin", origin);
        json.addProperty("destination", destination);
        json.addProperty("date", flightDate);
        json.addProperty("flightTime", travelTime);
        json.addProperty("numberOfSeats", numberOfSeats);
        json.addProperty("reserveeName", user.getFirstName() + " " + user.getLastName());
        
        JsonArray passengers = new JsonArray();
        
        for (Passenger p : passengersl) {
            JsonObject j = new JsonObject();
            j.addProperty("firstName", p.getFirstName());
            j.addProperty("lastName", p.getLastName());
            passengers.add(j);
        }
        json.add("passengers", passengers);
        
        return gson.toJson(json);
        
        
    }
    
    /*
    public Boolean newReservation (Boolean exists, User user,String airline, String flightID, String flightDate, int numberOfSeats, int travelTime, int totalPrice, String origin, String destination)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
        Reservation r = new Reservation(user, airline, flightID, flightDate, numberOfSeats, travelTime, totalPrice, origin, destination);
        user.addReservation(r);
        System.out.println(exists+ " " +r.toString());
        em.getTransaction().begin();
        em.persist(r);
        if(!exists) em.persist(user);
        em.getTransaction().commit();
        em.close();
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    */
}
