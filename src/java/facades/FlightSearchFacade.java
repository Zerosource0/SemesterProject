/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Adam
 */
public class FlightSearchFacade 
{
    URL url;
    
 
    public String read (String from, String date, Integer seats ) throws ProtocolException, IOException
    {
        this.url = new URL("http://angularairline-plaul.rhcloud.com/api/flightinfo/"+from+"/"+date+"/"+seats+"");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = "";
        int i=0;
        while (scan.hasNext()) {
            jsonStr += scan.nextLine();
            //System.out.println(i+" " + jsonStr);
        }
        scan.close();

        return jsonStr;
    }
    
}
