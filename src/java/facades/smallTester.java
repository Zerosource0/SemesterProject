/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Reservation;
import entity.User;
import java.util.ArrayList;
import java.util.List;
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
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU-Local");
        EntityManager em = emf.createEntityManager();
        String append="";
        List<User> users = em.createQuery("select u from User u, Reservation r where u.userName=r.user.userName").getResultList();
       // List<Reservation> reservations = em.createQuery("select r from User u, Reservation r where u.userName=r.user.userName").getResultList();
        for (User user : users) {
           // System.out.println(reservations.get(0).toString());
//            for (Reservation reservation : reservations) {
//                if (user.getUserName().equals(reservation.getUser().getUserName())) user.addReservation(reservation);
//            }
           // System.out.println(user.toString());
            for (Reservation reservation : user.getReservations()) {
                System.out.println(reservation.toString());
                
            }
           // user.getReservations().get(0).toString();
        }
        
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
