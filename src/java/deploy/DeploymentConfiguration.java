package deploy;

import entity.Currdesc;
import entity.Currvalues;
import entity.Role;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
      boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
      if (!makeTestUsers
              || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null)) {
        return;
      }
      Role userRole = new Role("User");
      Role adminRole = new Role("Admin");
      
      persistDanishBank(em);

      User user = new User("user", PasswordHash.createHash("test"));
      User admin = new User("admin", PasswordHash.createHash("test"));
      User both = new User("user_admin", PasswordHash.createHash("test"));
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
          persistDanishBank(em);
        em.close();
      }
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) 
    {
      Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    

  }
  
  private void persistDanishBank (EntityManager em)
  {
    Currdesc currdesc = new Currdesc();
    Currvalues currvalue = new Currvalues();
    BankXmlReader bxr = new BankXmlReader();
    String[][] resultSet = bxr.getResults();
    try
    {
        em.getTransaction().begin();
        for (int i=0;i<resultSet.length;i++)
        {
            try
            {
            currdesc = new Currdesc(resultSet[i][bxr.indexOfcode()],resultSet[i][bxr.indexOfdesc()]);
            currvalue= new Currvalues(resultSet[i][bxr.indexOfdate()],
                    Float.parseFloat(resultSet[i][bxr.indexOfvalue()]), currdesc);
            currdesc.addCurrValue(currvalue);
            System.out.println(currdesc.toString());
            System.out.println(currvalue.toString());
            em.persist(currdesc);
            em.persist(currvalue);
            }
            catch (Exception e)
            {
                System.out.println("hey");
                continue;
            }
            
            
            
            
           // System.out.println(i+" "+currdesc.getCode());
        }
        em.getTransaction().commit();
    }
    
    catch (Exception e)
    {
        //      Currdesc currdesc = new Currdesc("PLN", "Polska waluta");
//      Currvalues currvalue = new Currvalues("24-05-1995", (56.34), currdesc);
        e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
  }
}