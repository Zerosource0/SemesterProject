/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import deploy.DeploymentConfiguration;
import entity.Airport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class smallTester {

    public static ArrayList<String> airlineUrls;
    public static ArrayList<String> results;
    public static FlightSearchFacade fsFacade;

    public static void main(String[] args) {
        

//        CurrencyFacade cf = new CurrencyFacade();
//        ArrayList al=cf.getAllValues();
//        cf.getComplete();
//        
//        Gson gson = new Gson();
//        //CurrencyFacade cf = new CurrencyFacade();
//        //System.out.println(gson.toJson(cf.getComplete()));
//        UserFacade uf = new UserFacade();
//        uf.newUser("gypsyyyyy", "test", "admin");
       /*
        
        
        fsFacade = new FlightSearchFacade();
        airlineUrls = new ArrayList<String>();
        airlineUrls.add("http://angularairline-plaul.rhcloud.com/");
        airlineUrls.add("http://wildfly-x.cloudapp.net/airline/");
        results = new ArrayList<String>();

        List<Future<String>> list = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(8);

        for (final String url : airlineUrls) {

            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return fsFacade.readMultipleFromTo(url,"CPH","LAX","2015-11-24T11:30:00.000Z",10);
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
                Logger.getLogger(smallTester.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(smallTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println(results.toString());
               */
    }
               
}
