/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Currdesc;
import entity.Currvalues;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Adam
 */
public class CurrencyFacade {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    public ArrayList getAllValues ()
    {
        EntityManager em = emf.createEntityManager();
        List l=em.createNamedQuery("Currvalues.findAll").getResultList();
        ArrayList<Currvalues> cvl = new ArrayList<>();
        em.close();
        for (Object l1 : l) {
            cvl.add((Currvalues)l1);
            //System.out.println(((Currvalues)l1).toString());
        }
        
        return cvl;
    }
    
    public ArrayList getAllDesc ()
    {
        EntityManager em = emf.createEntityManager();
        List l=em.createNamedQuery("Currdesc.findAll").getResultList();
        ArrayList<Currdesc> cdl = new ArrayList<>();
        for (Object l1 : l) {
            cdl.add((Currdesc)l1);
          //  System.out.println(((Currdesc)l1).toString());
        }
        em.close();
        return cdl;
        
    }
    
    public ArrayList getComplete ()
    {
        EntityManager em = emf.createEntityManager();
        
        ArrayList<Currdesc> cdlist=getAllDesc();
        ArrayList<Currvalues> cvlist=getAllValues();
        ArrayList<CurrComplete> cclist = new ArrayList<>();
        
        for (Currvalues cv : cvlist) 
        {
//            String desc =(String)em.createNativeQuery("Currvalues.findDesc").setParameter("code",cv.getCode()).getSingleResult();
//            System.out.println(cv.getCode()+" "+desc);
            CurrComplete cc = new CurrComplete(cv.getCode().getCode(), cv.getCode().getDescription(), cv.getCurr(), cv.getDate());
            //System.out.println(cc.toString());
            cclist.add(cc);
            
        }
        return cclist;
    }
    
}
