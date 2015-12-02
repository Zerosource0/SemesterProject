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
public class BadParameterException extends WebApplicationException{
    public BadParameterException(String message) {
    super(Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorMessage(message, Response.Status.BAD_REQUEST.getStatusCode(), "non-existent or bad formatting" ))
            .type(MediaType.APPLICATION_XML).build());
}
    
}
