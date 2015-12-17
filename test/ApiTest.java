
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.authentication.FormAuthConfig;
import static com.jayway.restassured.authentication.FormAuthConfig.formAuthConfig;
import com.jayway.restassured.authentication.OAuthSignature;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;
import javax.ws.rs.core.MediaType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import rest.ApplicationConfig;

/**
 *
 * @author williambech
 */
public class ApiTest {

    static Server server;
      
    @BeforeClass
    public static void setUpBeforeClass() {
        baseURI = "http://localhost:8080/semesterSeedSP";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }
    
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8082);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }

    @Test
    public void getUsersTest_401() {
        final String uri = "demoadmin/users";
        given().
                contentType(MediaType.APPLICATION_JSON).
                when().
                get("/demoadmin/users").
                then().
                statusCode(401);
    }

    @Test
    public void logInTest_200() {
            given().
                contentType("application/json").
                body("{\"username\":\"admin\",\"password\":\"test\"}").
                when().
                post("/login").
                then().
                statusCode(200);
    }
    @Test
    public void wrongLogInInfo_401(){
            given().
                contentType("application/json").
                body("{\"username\":\"wrongUser\",\"password\":\"wrongPassword\"}").
                when().
                post("/login").
                then().
                statusCode(401);
    }
//    @Test
//    public void getUsersLogin_200(){
//                
//        String token = given().
//                contentType("application/json").
//                body("{\"username\":\"admin\",\"password\":\"test\"}").
//                when().
//                post("/login").
//                then().
//                statusCode(200).
//                extract().
//                path("token");
//        
//           given().
//                log().all().
//                headers("Authorization", "Bearer "+token).
//                contentType(MediaType.APPLICATION_JSON).
//                when().
//                get("/demoadmin/users").
//                then().
//                statusCode(200);
//    }
    @Test
    public void addNewUser_200(){
        
        given().
                contentType("application/json").
                body("{\"username\":\"newUserTestttttttt\",\"password\":\"test\",\"role\":\"admin\","
                        + " \"firstName\":\"fname\", \"lastName\":\"lname\",  \"email\":\"mail@mail\","
                        + " \"phone\":\"1234\"}").
                when().post("/newuser").
                then().statusCode(200);
                
    }
    
     @Test
    public void addNewUserWithWrongInput_401(){
        
        given().
                contentType("application/json").
                body("{\"username\":\"newUserTesttt\",\"password\":\"test\", \"role\":\"WrongRole\","
                        + " \"firstName\":\"fname\", \"lastName\":\"lname\",  \"email\":\"mail@mail\","
                        + " \"phone\":\"1234\"}").
                when().post("/newuser").
                then().statusCode(401);
                
    }
    /*
    @Test
    public void searchToTest_200(){
        
        given().
                contentType("application/json").
                when().get("flightinfo/CPH/2016-01-01T00:00:00.000Z/1").
                then().statusCode(200);
                
    }
    
      @Test
    public void searchToFromTest_200(){
        
        given().
                contentType("application/json").
                when().get("flightinfo/CPH/SXF/2016-01-01T00:00:00.000Z/1").
                then().statusCode(200);
                
    }
    */
    @Test
    public void searchSpecificFromTest_200(){
        
        given().
                contentType("application/json").
                when().get("airline/Angular Airline/CPH/2016-01-01T00:00:00.000Z/1").
                then().statusCode(200);
                
    }
    
      @Test
    public void searchSpecificFromToTest_200(){
        
        given().
                contentType("application/json").
                when().get("airline/Angular Airline/CPH/SXF/2016-01-01T00:00:00.000Z/1").
                then().statusCode(200);
                
    }
    
      @Test
    public void searchToWrongSeatTest_400(){
        
        given().
                contentType("application/json").
                when().get("flightinfo/CPH/2016-01-01T00:00:00.000Z/0").
                then().statusCode(400);
                
    }
    
      @Test
    public void searchToFromWrongSeatTest_400(){
        
        given().
                contentType("application/json").
                when().get("flightinfo/CPH/SXF/2016-01-01T00:00:00.000Z/0").
                then().statusCode(400);        
    }
    
}
