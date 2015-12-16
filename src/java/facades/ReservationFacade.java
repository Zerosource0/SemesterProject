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
import entity.AirlinesUrl;
import entity.Passenger;
import entity.Reservation;
import entity.User;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static javax.ws.rs.client.Entity.json;

/**
 *
 * @author Adam
 */
public class ReservationFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    private User currentUser = null;

    Gson gson = new Gson();

    
    
    public ReservationFacade() {

    }

    public ReservationFacade(User currentUser) {
        this.currentUser = currentUser;
    }

    public String reserve(User user, String airline, String flightId, String flightDate, Integer numberOfSeats, int travelTime, int totalPrice, String origin, String destination, List<Passenger> passengersl) {

        EntityManager em = emf.createEntityManager();

        Reservation reservation = new Reservation(user, airline, flightId, flightDate, numberOfSeats, travelTime, totalPrice, origin, destination, passengersl);
        try {
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();

        } finally {
            em.close();
        }

        try {
           return reserveAirline(user, airline, flightId, numberOfSeats, passengersl);
        } catch (IOException ex) {
            Logger.getLogger(ReservationFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JsonObject json = new JsonObject();
        json.addProperty("message", "Reservation failed");
        return gson.toJson(json);
    }

    public String reserveAirline(User user, String airline, String flightId, int numberOfSeats, List<Passenger> passengersl) throws MalformedURLException, IOException {

        FlightSearchFacade fsf = FlightSearchFacade.getInstance();
        List<AirlinesUrl> airlines = fsf.airlineUrls;

        String url = null;

        for (AirlinesUrl airline1 : airlines) {
            if (airline1.getName().equals(airline)) {
                url = airline1.getAirlineUrl() + "api/flightreservation";
            }
        }

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("flightID", flightId);
        jsonObj.addProperty("numberOfSeats", numberOfSeats);
        jsonObj.addProperty("ReserveeName", user.getFirstName() + " " + user.getLastName());
        jsonObj.addProperty("ReserveeEmail", user.getEmail());

        JsonArray passengers = new JsonArray();
        for (Passenger p : passengersl) {
            JsonObject j = new JsonObject();
            j.addProperty("firstName", p.getFirstName());
            j.addProperty("lastName", p.getLastName());
            passengers.add(j);
        }
        jsonObj.add("Passengers", passengers);

        String transformedJson = gson.toJson(jsonObj);

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty("Content-Type", "application/json;");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Method", "POST");
        con.setDoOutput(true);
        PrintWriter pw = new PrintWriter(con.getOutputStream());
        try (OutputStream os = con.getOutputStream()) {
            os.write(transformedJson.getBytes("UTF-8"));
        }
        int HttpResult = con.getResponseCode();
        InputStreamReader is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8")
                : new InputStreamReader(con.getErrorStream(), "utf-8");
        Scanner responseReader = new Scanner(is);
        String response = "";
        while (responseReader.hasNext()) {
            response += responseReader.nextLine() + System.getProperty("line.separator");
        }
        System.out.println(response);
        System.out.println(con.getResponseCode());
        System.out.println(con.getResponseMessage());
        
        return response;
    }
}
