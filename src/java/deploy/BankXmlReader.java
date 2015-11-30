package deploy;
import java.io.IOException;
import java.lang.reflect.Array;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class BankXmlReader extends DefaultHandler {
  private static  HashMap<String,String> codes =new HashMap<>();
  private static  HashMap<String,String> values =new HashMap<>();
  private static String[][] result;
  private String code="";
  private String desc="";
  private static String date="";
  

    
    

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
   // System.out.print("Element: " + localName+": " );
   for (int i = 0; i < attributes.getLength(); i++) 
   {
      switch (attributes.getLocalName(i))
      {
          
          case "code": 
          {
              code=attributes.getValue(i);
              //System.out.println(code);
          }
              break;
          case "desc":
          {
              desc=attributes.getValue(i);
                if (!code.isEmpty()) 
                {
                    codes.put(code, desc);
                  //  code="";
                   // desc="";
                }
          }
              break;
          case "rate": 
          {
              try
              {
                  
                  values.put(code,attributes.getValue(i));
                  System.out.println(code+" sth");
              }
              catch (Exception e)
              {
                  continue;
              }
              
          }
              break;
          case "id": 
          {
              date=attributes.getValue(i);
              
          }
              break;
          
    }
         
   }
  }
  public void showResults (String[][] result)
  {
      for (int j=0;j<result.length;j++) System.out.println(" "+result[j][0] +"  "+ result[j][1]+ "  "+result[j][2]+"  "+result[j][3]);
      System.out.println(result.length);
  }
  
    
    public String[][] getResults() {
        
    
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new BankXmlReader());
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
            
            result = new String[codes.size()][4];
            int j = 0;

                for (Map.Entry<String, String> entry : codes.entrySet()) {
                    
                   
                    result[j][0] = entry.getKey();
                    result[j][1] = entry.getValue();
                    result[j][2] = values.get(result[j][0]);
                    result[j][3] = date;
                    
                    j++;
                    
                }
            
            
            

    } catch (SAXException | IOException e) 
    {
      e.printStackTrace();
      result=null;
    } 
        return result;
    }
    
    public int indexOfcode() {
        return 0;
    }

    public int indexOfdesc() {
        return 1;
    }

    public int indexOfvalue() {
        return 2;
    }

    public int indexOfdate() {
        return 3;
    }
}
