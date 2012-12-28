/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.exception;

/**
 * 
 * @author ÐùÔ¯
 * @version $Id: IllegalMessageException.java, v 0.1 2011-11-1 ÏÂÎç05:21:06 ÐùÔ¯ Exp $
 */
public class IllegalMessageException extends Exception {

    /**  */
    private static final long serialVersionUID = -7165090436050648405L;

    /**
     * 
     */
    public IllegalMessageException() {
    }

    /**
     * @param message
     * @param cause
     */
    public IllegalMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public IllegalMessageException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public IllegalMessageException(Throwable cause) {
        super(cause);
    }

}
