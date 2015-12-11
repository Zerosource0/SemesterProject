/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import deploy.DeploymentConfiguration;
import entity.AirlinesUrl;
import entity.Airport;
import entity.Log;
import exceptions.BadParameterException;
import exceptions.NotFoundException;
import static facades.smallTester.airlineUrls;
import static facades.smallTester.fsFacade;
import static facades.smallTester.results;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author Adam
 */
public class FlightSearchFacade {

    URL url;
    Gson gson = new Gson();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    public List<AirlinesUrl> airlineUrls;
    public ArrayList<String> results;

    public FlightSearchFacade() {
        airlineUrls = getAirlines();
    }

    
    public void log(String message){
    
        Log log = new Log(message);
        
        EntityManager em = emf.createEntityManager();
        try{
        em.getTransaction().begin();
        em.persist(log);
        em.getTransaction().commit();
        }finally{
        em.close();
        }
    }
    
    public List<AirlinesUrl> getAirlines() {
        
        EntityManager em = emf.createEntityManager();
        return em.createQuery("Select a from AirlinesUrl a").getResultList();

    }

    public String getJsonFromAirlinesFrom(final String from, final String date, final Integer seats) {
       
        
        
        results = new ArrayList<String>();

        List<Future<String>> list = new ArrayList<>();


        ExecutorService executor = Executors.newFixedThreadPool(airlineUrls.size());

        for (final AirlinesUrl u : airlineUrls) {

            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {

                    return readMultipleFrom(u.getAirlineUrl(), from, date, seats);
                }
            };
            list.add(executor.submit(task));
        }
        executor.shutdown();

        for (Future<String> list1 : list) {

            try {
                //This "vodoo" is a ternarry operator. If you don't know it, read:
                //http://alvinalexander.com/java/edu/pj/pj010018

                results.add(list1.get());
            } catch (InterruptedException ex) {
                Logger.getLogger(FlightSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(FlightSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        log("User conducted search: from " + from + " date " + date + " seats " + seats);
        return results.toString();

    }
    
    public String getJsonFromSpecificAirlineFromTo(final String airline, final String from, final String to, final String date, final Integer seats) {
        results = new ArrayList<String>();
        
        AirlinesUrl air = new AirlinesUrl();
        
        for (AirlinesUrl airlineUrl : airlineUrls) {
            if(airlineUrl.getName().equals(airline)){
                air = airlineUrl;
            }
        }
        log("User conducted search: airline " + airline + " from " + from + " to " + to + " date " + date + " seats " + seats);
        try {
            return readMultipleFromTo(air.getAirlineUrl(), from, to, date, seats);
        } catch (IOException ex) {
            Logger.getLogger(FlightSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getJsonFromSpecificAirlineFrom(final String airline, final String from, final String date, final Integer seats) {
        results = new ArrayList<String>();
        
        AirlinesUrl air = new AirlinesUrl();
        
        for (AirlinesUrl airlineUrl : airlineUrls) {
            if(airlineUrl.getName().equals(airline)){
                air = airlineUrl;
            }
        }
        log("User conducted search: airline " + airline + " from " + from + " date " + date + " seats " + seats);
        try {
            return readMultipleFrom(air.getAirlineUrl(), from, date, seats);
        } catch (IOException ex) {
            Logger.getLogger(FlightSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getJsonFromAirlinesFromTo(final String from, final String to, final String date, final Integer seats) {
        //First get airlines from database: simulated here:
        

        results = new ArrayList<String>();

        List<Future<String>> list = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(airlineUrls.size());

        for (final AirlinesUrl u : airlineUrls) {

            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {

                    return readMultipleFromTo(u.getAirlineUrl(), from, to, date, seats);
                }
            };
            list.add(executor.submit(task));
        }
        executor.shutdown();

        for (Future<String> list1 : list) {

            try {
                //This "vodoo" is a ternarry operator. If you don't know it, read:
                //http://alvinalexander.com/java/edu/pj/pj010018

                results.add(list1.get());
            } catch (InterruptedException ex) {
                Logger.getLogger(FlightSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(FlightSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        log("User conducted search: from " + from + " to " + to + " date " + date + " seats " + seats);
        return results.toString();

    }

    public String readMultipleFromTo(String airlineUrl, String from, String to, String date, Integer seats) throws ProtocolException, IOException {
        try {
            URL url = new URL(airlineUrl + "api/flightinfo/" + from + "/" + to + "/" + date + "/" + seats);
            System.out.println("READING FROM URL: " + url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json; charset=UTF-8");
            Scanner scan = new Scanner(con.getInputStream());
            String jsonStr = "";
            int i = 0;
            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
                //System.out.println(i+" " + jsonStr);
            }

            scan.close();
            return jsonStr;
        } catch (IOException ex) {
            throw new NotFoundException("No search results");
        }
    }

    public String readMultipleFrom(String airlineUrl, String from, String date, Integer seats) throws ProtocolException, IOException {
        try {
            URL url = new URL(airlineUrl + "api/flightinfo/" + from + "/" + date + "/" + seats + "");
            System.out.println("READING FROM URL: " + url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json; charset=UTF-8");
            Scanner scan = new Scanner(con.getInputStream());
            String jsonStr = "";
            int i = 0;
            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
                //System.out.println(i+" " + jsonStr);
            }

            scan.close();
            return jsonStr;
        } catch (IOException ex) {
            throw new NotFoundException("No search results");
        }

    }

    public String read(String from, String date, Integer seats) throws ProtocolException, IOException {
        this.url = new URL("http://angularairline-plaul.rhcloud.com/api/flightinfo/" + from + "/" + date + "/" + seats + "");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = "";
        int i = 0;
        while (scan.hasNext()) {
            jsonStr += scan.nextLine();
            //System.out.println(i+" " + jsonStr);
        }
        scan.close();

        return jsonStr;
    }

}
