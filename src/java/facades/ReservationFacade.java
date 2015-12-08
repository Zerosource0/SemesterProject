/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Reservation;
import entity.User;
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
    public ReservationFacade ()
    {
        
    }
    
    public ReservationFacade (User currentUser)
    {
        this.currentUser=currentUser;
    }
    
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
}
