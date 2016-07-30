package com.emc.ehc.cloudkeeper.exception;
/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 30, 2016 6:15:50 PM
* 
*/
public class ConnectionException extends Exception {
    private static final long serialVersionUID = 2908618315971075004L;

    /**
     * Creates a new exception.
     */
    public ConnectionException() {
        super();
    }

    /**
     * Creates a new exception.
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new exception.
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Creates a new exception.
     */
    public ConnectionException(Throwable cause) {
        super(cause);
    }
}
