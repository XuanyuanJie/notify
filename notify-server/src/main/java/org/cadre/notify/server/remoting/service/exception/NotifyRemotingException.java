/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service.exception;

/**
 * Notify remoting的check异常，强制要求捕捉
 * @author 轩辕
 * @version $Id: NotifyRemotingException.java, v 0.1 2011-11-1 上午11:15:41 轩辕 Exp $
 */
public class NotifyRemotingException extends Exception {

    static final long serialVersionUID = 8923187437857838L;

    public NotifyRemotingException() {
        super();
    }

    public NotifyRemotingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotifyRemotingException(String message) {
        super(message);
    }

    public NotifyRemotingException(Throwable cause) {
        super(cause);
    }

}
