package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import facades.UserFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;

@Path("demoadmin")
@RolesAllowed("Admin")
public class Admin {
  
  UserFacade facade;
  Gson gson = new Gson();

    public Admin(UserFacade facade) {
        this.facade = facade;
    }

    public Admin() {
    }
  
    
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSomething(){
    String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
    return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated ADMINS\",\n"
            +"\"serverTime\": \""+now +"\"}"; 
  }
 
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/users")
  public String getAllUsers(){
      JsonArray jArray = new JsonArray();
      List<User> users = new UserFacade().getUsers();
      for (User user : users) {
          JsonObject json = new JsonObject();
          json.addProperty("userName", user.getUserName());
          jArray.add(json);
      }
      String jsonString = gson.toJson(jArray);
      return jsonString;
  }
  
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/users/{id}")
  public String deleteUser(@PathParam("id") String id){
      facade = UserFacade.getInstance();
      User user = facade.getUserByUserId(id);
      JsonObject json = new JsonObject();
      json.addProperty("userName", user.getUserName());
      facade.deleteUser(id);
      return gson.toJson(json);
  }
}
