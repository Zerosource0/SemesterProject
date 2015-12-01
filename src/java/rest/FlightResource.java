/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import static com.google.common.base.Predicates.in;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.System.in;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;

/**
 * REST Web Service
 *
 * @author marcj_000
 */
@Path("flightinfo")
public class FlightResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FlightResource
     */
    public FlightResource() {
    }

    /**
     * Retrieves representation of an instance of rest.FlightResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{from}/{date}/{seats}")
    @Produces("application/json")
    @Consumes("application/json")
    public String getJson(@PathParam("from") String from, @PathParam("date") String date, @PathParam("seats") String seats) throws UnsupportedEncodingException, MalformedURLException, IOException {

        //String url = "http://angularairline-plaul.rhcloud.com/api/flightinfo/" + from + "/" + date + "/" + seats;
        //for each ariline do this;
        
            
    }

}
