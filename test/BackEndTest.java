/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entity.User;
import facades.CurrencyFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author williambech
 */
public class BackEndTest {
    
    UserFacade uf = new UserFacade();
    CurrencyFacade cf = new CurrencyFacade();
    
    public BackEndTest() {
        
        
    }
    
    @Test
    public void newUser(){
       uf.newUser("newBackEndUSer", "test", "admin").equals(true);
    }
    
     @Test
    public void authenticateUser(){
       List<String> list = uf.authenticateUser("newBackEndUSer", "test");
       assertFalse(list.isEmpty());
    }
    
     @Test
    public void getUsers(){
       List<User> list = uf.getUsers();
       assertFalse(list.isEmpty());
    }

    @Test
    public void deleteUser(){
       User user =  uf.deleteUser("newBackEndUSer");
       user.getUserName().equals("newBackEndUSer");
    }
    
      @Test
    public void getCurrencies(){
       ArrayList list = cf.getComplete();
       assertFalse(list.isEmpty());
    }
    
       @Test
    public void getValues(){
       ArrayList list = cf.getAllValues();
       assertFalse(list.isEmpty());
    }
    
       @Test
    public void getDescriptions(){
       ArrayList list = cf.getAllDesc();
       assertFalse(list.isEmpty());
    }
              
    
   
    
}
