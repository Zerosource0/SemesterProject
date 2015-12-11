/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;


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
        
    }
    
}
