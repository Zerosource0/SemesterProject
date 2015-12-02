/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Marc
 */
@Entity
public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;
    

    private String cityname;
    @Id
    private String iata;
    
    private String description;

    public Airport() {
    }

    public Airport(String iata, String cityname, String desc) {
        this.cityname = cityname;
        this.iata = iata;
        this.description = desc;
    }
    

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    
    
}
