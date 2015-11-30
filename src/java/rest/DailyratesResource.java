/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Currvalues;
import facades.CurrencyFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author marcj_000
 */
@Path("currency")
@RolesAllowed("User")
public class DailyratesResource {

    @Context
    private UriInfo context;
    
    CurrencyFacade currencyFacade = new CurrencyFacade();
    Gson gson = new Gson();
    /**
     * Creates a new instance of DailyratesResource
     */
    public DailyratesResource() {
    }

    /**
     * Retrieves representation of an instance of rest.DailyratesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("/dailyrates")
    public String getJson() {
       return gson.toJson(currencyFacade.getComplete());
    }
    
    
    @GET
    @Produces("application/json")
    @Path("calculator/{amount}/{from}/{to}")
    public String convert(@PathParam("amount") BigDecimal amount, @PathParam("from") String from, @PathParam("to") String to){
        
        ArrayList<Currvalues> rates = currencyFacade.getAllValues();
        
        Currvalues fromCurrency = null;
        Currvalues toCurrency = null;
        
        for (Currvalues rate : rates) {
         
            if(rate.getCode().getCode().equals(from)) fromCurrency = rate;        
            if(rate.getCode().getCode().equals(to)) toCurrency = rate;
           
        }
        
        
        Double amountDkk = amount.doubleValue() * fromCurrency.getCurr();
        Double amountCurrency = amountDkk / toCurrency.getCurr();
        
        JsonObject json = new JsonObject();
        json.addProperty("convertedCurrency", amountCurrency);
        
        return gson.toJson(json);
    }
}