/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

/**
 *
 * @author Adam
 */
public class CurrComplete {
    private String code;
    private String desc;
    private double rate;
    private String date;

    public CurrComplete(String code, String desc, double rate, String date) {
        this.code = code;
        this.desc = desc;
        this.rate = rate;
        this.date = date;
    }

    @Override
    public String toString() {
        return "CurrComplete{" + "code=" + code + ", desc=" + desc + ", rate=" + rate + ", date=" + date + '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
