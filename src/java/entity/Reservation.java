/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.eclipse.persistence.jpa.config.Cascade;

/**
 *
 * @author marcj_000
 */
@Entity
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    private String airline;
    
    private String flightID;
    
    private String flightDate;
    
    private int numberOfSeats;
    
    private int travelTime;
    
    private int totalPrice;
    
    private String origin;
    
    private String destination;
    
    @OneToMany (cascade = CascadeType.ALL)
    private List<Passenger> passengers;

    public Reservation() {
    }

    public Reservation( String airline, String flightID, String flightDate, int numberOfSeats, int travelTime, int totalPrice, String origin, String destination, List<Passenger> passengers) {
        this.airline = airline;
        this.flightID = flightID;
        this.flightDate = flightDate;
        this.numberOfSeats = numberOfSeats;
        this.travelTime = travelTime;
        this.totalPrice = totalPrice;
        this.origin = origin;
        this.destination = destination;
        this.passengers = passengers;
    }
    public Reservation(User user, String airline, String flightID, String flightDate, int numberOfSeats, int travelTime, int totalPrice, String origin, String destination, List<Passenger> passengers) {
        this.user = user;
        this.airline = airline;
        this.flightID = flightID;
        this.flightDate = flightDate;
        this.numberOfSeats = numberOfSeats;
        this.travelTime = travelTime;
        this.totalPrice = totalPrice;
        this.origin = origin;
        this.destination = destination;
        this.passengers = passengers;
    }

    

    public Long getId() {
        return id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", user=" + user + ", airline=" + airline + ", flightID=" + flightID + ", flightDate=" + flightDate + ", numberOfSeats=" + numberOfSeats + ", travelTime=" + travelTime + ", totalPrice=" + totalPrice + ", origin=" + origin + ", destination=" + destination + '}';
    }
    
    
    
    
    
}
