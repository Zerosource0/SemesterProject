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
    private String description;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
