/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import exceptions.BadParameterException;
import facades.UserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Adam
 */
@Path("newuser")
public class NewUserResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of NewUserResource
     */
    public NewUserResource() {
    }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response newUser(String jsonString) throws JOSEException 
  {
    if(jsonString == null){
        throw new BadParameterException("Make sure you enter something");
    }
    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
    String username =  json.get("username").getAsString(); 
    String password =  json.get("password").getAsString();
    String role = json.get("role").getAsString();
    String firstName = json.get("firstName").getAsString();
    String lastName = json.get("lastName").getAsString();
    String email = json.get("email").getAsString();
    
    if(username.equals("")|| password.equals("")|| role == null){
        throw new BadParameterException("Make sure you enter something");
    }
    
    JsonObject responseJson = new JsonObject();
    UserFacade uf = new UserFacade();
    boolean addUser = uf.newUser(username, password, role, firstName, lastName, email);
    
    if(addUser){
        System.out.println("good");
      return Response.ok(new Gson().toJson(responseJson)).build();
    } else{ 
         System.out.println("bad");
        throw new NotAuthorizedException("Ilegal username or select a role",Response.Status.UNAUTHORIZED);
    }
  }
}
