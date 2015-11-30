/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author Adam
 */
public class smallTester {
    
    
    public static void main(String[] args) {
//        CurrencyFacade cf = new CurrencyFacade();
//        ArrayList al=cf.getAllValues();
//        cf.getComplete();
//        
        Gson gson = new Gson();
        //CurrencyFacade cf = new CurrencyFacade();
        //System.out.println(gson.toJson(cf.getComplete()));
        UserFacade uf = new UserFacade();
        uf.newUser("gypsyyyyy", "test", "admin");
    }
}
