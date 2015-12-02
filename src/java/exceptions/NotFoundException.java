/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author williambech
 */
public class NotFoundException extends WebApplicationException{
    
    public NotFoundException(String message){
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message)
                .type(MediaType.APPLICATION_XML).build());
    };
}
