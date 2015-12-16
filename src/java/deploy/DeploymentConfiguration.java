package deploy;

import entity.AirlinesUrl;
import entity.Airport;
import entity.Role;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.PasswordHash;

@WebListener
public class DeploymentConfiguration implements ServletContextListener {

  public static String PU_NAME = "PU-Local";

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Map<String, String> env = System.getenv();
    //If we are running in the OPENSHIFT environment change the pu-name 
    if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
      PU_NAME = "PU_OPENSHIFT";
    }
    try {
      ServletContext context = sce.getServletContext();
      EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
      EntityManager em = emf.createEntityManager();
      
      //This flag is set in Web.xml -- Make sure to disable for a REAL system
      /*boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
      if (!makeTestUsers
              || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null)) {
        return;
      }*/
      Role userRole = new Role("User");
      Role adminRole = new Role("Admin");
      

      User user = new User("user", PasswordHash.createHash("test"), "FirstName", "LastName","Email@Test.com");
      User admin = new User("admin", PasswordHash.createHash("test"), "FirstName", "LastName","Email@Test.com");
      User both = new User("user_admin", PasswordHash.createHash("test"), "FirstName", "LastName","Email@Test.com");
      user.AddRole(userRole);
      admin.AddRole(adminRole);
      both.AddRole(userRole);
      both.AddRole(adminRole);
      

      try {
        em.getTransaction().begin();
        em.persist(userRole);
        em.persist(adminRole);
        
        

        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
      } finally {
          //persistDanishBank(em);
          persistAirports(em);
          persistAirlines(em);
        em.close();
      }
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) 
    {
      Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
  
  private void persistAirlines(EntityManager em){
      ArrayList<AirlinesUrl> airlines = new ArrayList<>();
      
      airlines.add(new AirlinesUrl("http://angularairline-plaul.rhcloud.com/","Angular Airline"));
      airlines.add(new AirlinesUrl("http://wildfly-x.cloudapp.net/airline/","Cos-Group2"));
      airlines.add(new AirlinesUrl("http://thebighornairline-ebski.rhcloud.com/GiantHornAirlineServer/","ClassA-19"));
      airlines.add(new AirlinesUrl("http://justfly.azurewebsites.net/MomondoProjekt/","ClassA-42"));
      airlines.add(new AirlinesUrl("http://timetravel-tocvfan.rhcloud.com/","ClassB-TimeTravel"));
      airlines.add(new AirlinesUrl("https://frenchyairlines-zerosource.rhcloud.com/","Frenchy Airlines"));
      
        em.getTransaction().begin();
            for (AirlinesUrl airline : airlines) {
                em.persist(airline);
            }
        em.getTransaction().commit();
      
  }
  
  private void persistAirports(EntityManager em){
      ArrayList<Airport> ap = new ArrayList<>();
        
        ap.add(new Airport("BCN","Barcelona","Barcelona International"));
        ap.add(new Airport("CDG","Paris","Charles de Gaulle International"));
        ap.add(new Airport("CPH","Copenhagen","Copenhagen Kastrup"));
        ap.add(new Airport("STN","London","London Stansted"));
        ap.add(new Airport("SXF","Berlin","Berlin-Schönefeld International"));
        ap.add(new Airport("LAX","Los Angeles","Los Angeles International"));
        ap.add(new Airport("SFO","San Francisco","San Francisco International"));
        ap.add(new Airport("JFK","New York","John F Kennedy International"));
        ap.add(new Airport("HND","Tokyo","Tokyo International"));
        ap.add(new Airport("DME","Moscow","Domodedovo International"));
        ap.add(new Airport("PEK","Beijing","Beijing International"));
        ap.add(new Airport("AMS","Amsterdam","Amsterdam Schiphol Airport"));
        ap.add(new Airport("IST","Istanbul","Istanbul Atatürk Airport"));
        ap.add(new Airport("GRU","São Paulo","São Paulo-Guarulhos International Airport"));
        ap.add(new Airport("YYZ","Toronto","Toronto Pearson International Airport"));
        ap.add(new Airport("DXB","Dubai","Dubai International Airport"));
        ap.add(new Airport("ICN","Seoul","Seoul Incheon International Airport"));
        ap.add(new Airport("FCO","Rome","Leonardo da Vinci-Fiumicino Airport"));
        ap.add(new Airport("MEL","Melbourne","Tullamarine International Airport"));
        ap.add(new Airport("JNB","Johannesburg","O. R. Tambo International Airport"));
        ap.add(new Airport("CAI","Cairo","Cairo International"));
        ap.add(new Airport("MEX","Mexico City","Benito Juárez International Airport"));
        ap.add(new Airport("BBU","Bucharest","Băneasa International Airport"));
        ap.add(new Airport("HEM","Helsinki","Helsinki Malmi Airpor"));
        ap.add(new Airport("SIN","Singapore","Singapore Changi International Airport"));
  
        
        if (em.isOpen()) em.getTransaction().begin();
            for (Airport ap1 : ap) {
                em.persist(ap1);
            }
        em.getTransaction().commit();
        
        
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
  }
}