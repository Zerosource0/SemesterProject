/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author williambech
 */
@XmlRootElement
public class ErrorMessage {
    
    private String message;
    private Integer status;
    private String description;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, Integer status, String description) {
        this.message = message;
        this.status = status;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
