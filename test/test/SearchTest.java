/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import deploy.DeploymentConfiguration;
import entity.AirlinesUrl;
import entity.Airport;
import entity.Role;
import entity.User;
import facades.FlightSearchFacade;
import facades.ReservationFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import security.PasswordHash;

/**
 *
 * @author Marc
 */
public class SearchTest {

    public static String PU_NAME = "PU-Local";

    public static EntityManagerFactory emf;
    public static EntityManager em;

    @BeforeClass
    public static void setUpClass() {

        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        em = emf.createEntityManager();

        try {

            Role userRole = new Role("User");
            Role adminRole = new Role("Admin");

            User user = new User("user", PasswordHash.createHash("test"), "FirstName", "LastName", "Email@Test.com", "2134");
            User admin = new User("admin", PasswordHash.createHash("test"), "FirstName", "LastName", "Email@Test.com", "2134");
            User both = new User("user_admin", PasswordHash.createHash("test"), "FirstName", "LastName", "Email@Test.com", "2134");

            user.AddRole(userRole);
            admin.AddRole(adminRole);
            both.AddRole(userRole);
            both.AddRole(adminRole);

            try {
                em.getTransaction().begin();
                em.persist(userRole);
                em.persist(adminRole);

                em.persist(user);
                em.persist(admin);
                em.persist(both);
                em.getTransaction().commit();
            } finally {
                //persistDanishBank(em);
                persistAirports(em);
                persistAirlines(em);
                em.close();
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SearchTest() {

    }

    private static void persistAirlines(EntityManager em) {
        ArrayList<AirlinesUrl> airlines = new ArrayList<>();

        airlines.add(new AirlinesUrl("http://angularairline-plaul.rhcloud.com/", "Angular Airline"));
        airlines.add(new AirlinesUrl("http://wildfly-x.cloudapp.net/airline/", "Cos-Group2"));
        airlines.add(new AirlinesUrl("http://thebighornairline-ebski.rhcloud.com/GiantHornAirlineServer/", "ClassA-19"));
        airlines.add(new AirlinesUrl("http://justfly.azurewebsites.net/MomondoProjekt/", "ClassA-42"));
        airlines.add(new AirlinesUrl("http://timetravel-tocvfan.rhcloud.com/", "ClassB-TimeTravel"));
        airlines.add(new AirlinesUrl("https://frenchyairlines-zerosource.rhcloud.com/", "Frenchy Airlines"));

        em.getTransaction().begin();
        for (AirlinesUrl airline : airlines) {
            em.persist(airline);
        }
        em.getTransaction().commit();

    }

    private static void persistAirports(EntityManager em) {
        ArrayList<Airport> ap = new ArrayList<>();

        ap.add(new Airport("BCN", "Barcelona", "Barcelona International"));
        ap.add(new Airport("CDG", "Paris", "Charles de Gaulle International"));
        ap.add(new Airport("CPH", "Copenhagen", "Copenhagen Kastrup"));
        ap.add(new Airport("STN", "London", "London Stansted"));
        ap.add(new Airport("SXF", "Berlin", "Berlin-Schönefeld International"));
        ap.add(new Airport("LAX", "Los Angeles", "Los Angeles International"));
        ap.add(new Airport("SFO", "San Francisco", "San Francisco International"));
        ap.add(new Airport("JFK", "New York", "John F Kennedy International"));
        ap.add(new Airport("HND", "Tokyo", "Tokyo International"));
        ap.add(new Airport("DME", "Moscow", "Domodedovo International"));
        ap.add(new Airport("PEK", "Beijing", "Beijing International"));
        ap.add(new Airport("AMS", "Amsterdam", "Amsterdam Schiphol Airport"));
        ap.add(new Airport("IST", "Istanbul", "Istanbul Atatürk Airport"));
        ap.add(new Airport("GRU", "São Paulo", "São Paulo-Guarulhos International Airport"));
        ap.add(new Airport("YYZ", "Toronto", "Toronto Pearson International Airport"));
        ap.add(new Airport("DXB", "Dubai", "Dubai International Airport"));
        ap.add(new Airport("ICN", "Seoul", "Seoul Incheon International Airport"));
        ap.add(new Airport("FCO", "Rome", "Leonardo da Vinci-Fiumicino Airport"));
        ap.add(new Airport("MEL", "Melbourne", "Tullamarine International Airport"));
        ap.add(new Airport("JNB", "Johannesburg", "O. R. Tambo International Airport"));
        ap.add(new Airport("CAI", "Cairo", "Cairo International"));
        ap.add(new Airport("MEX", "Mexico City", "Benito Juárez International Airport"));
        ap.add(new Airport("BBU", "Bucharest", "Băneasa International Airport"));
        ap.add(new Airport("HEM", "Helsinki", "Helsinki Malmi Airpor"));
        ap.add(new Airport("SIN", "Singapore", "Singapore Changi International Airport"));

        if (em.isOpen()) {
            em.getTransaction().begin();
        }
        for (Airport ap1 : ap) {
            em.persist(ap1);
        }
        em.getTransaction().commit();

    }

    @Test
    public void getAirports() {
        em = emf.createEntityManager();
        List<Airport> airports = em.createQuery("Select a from Airport a").getResultList();
        assertTrue(airports.size() == 25);
    }

    @Test
    public void getAirlines() {
        em = emf.createEntityManager();
        List<AirlinesUrl> airlines = em.createQuery("Select a from AirlinesUrl a").getResultList();
        assertTrue(airlines.size() == 6);
    }

    @Test
    public void getUsers() {
        em = emf.createEntityManager();
        List<User> users = em.createQuery("Select u from User u").getResultList();
        assertTrue(users.size() == 3);

    }
 /*
    @Test
    public void getFlightFromAirline() {
        FlightSearchFacade fsf = FlightSearchFacade.getInstance();
        
        String results = fsf.getJsonFromSpecificAirlineFrom("Angular Airline","CPH", "2016-1-1T01:00:00.000Z", 2);
        System.out.println("results: " + results);
  
        assertTrue(results.contains("\"CPH\","));
        assertTrue(results.contains("\"AngularJS Airline\","));
        assertTrue(results.contains("\"flights\":"));
    }
    
    @Test
    public void getFlightFromToAirline() {
        FlightSearchFacade fsf = FlightSearchFacade.getInstance();
        
        String results = fsf.getJsonFromSpecificAirlineFromTo("Angular Airline","CPH","SXF","2016-1-1T01:00:00.000Z", 2);
        System.out.println("results: " + results);
  
        assertTrue(results.contains("\"CPH\","));
        assertTrue(results.contains("\"SXF\""));
        assertTrue(results.contains("\"AngularJS Airline\","));
        assertTrue(results.contains("\"flights\":"));
    }
*/
}
