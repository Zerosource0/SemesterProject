/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;

import entity.Currdesc;
import entity.Currvalues;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adam
 */
public class tester {
   
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        BankXmlReader xrd = new BankXmlReader();
        String[][] sth=xrd.getResults();
        xrd.showResults(sth);
        persistDanishBank(em);
        
    }
    private static void persistDanishBank (EntityManager em)
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
            
            
            
            
           System.out.println(i+" "+currdesc.getCode());
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
}
