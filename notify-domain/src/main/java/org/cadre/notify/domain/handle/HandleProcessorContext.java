/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.handle;

import org.cadre.notify.domain.ProcessorContext;
import org.cadre.notify.domain.notify.NotifyMessage;
import org.cadre.notify.domain.request.NotifyRequest;

/**
 * 
 * @author ÐùÔ¯
 * @version $Id: HandleProcessorContext.java, v 0.1 2011-11-1 ÏÂÎç03:29:54 ÐùÔ¯ Exp $
 */
public class HandleProcessorContext implements ProcessorContext {

    /**  */
    private static final long            serialVersionUID = -2697413424036516039L;

    private NotifyRequest<NotifyMessage> request;

    /**
     * Getter method for property <tt>request</tt>.
     * 
     * @return property value of request
     */
    public NotifyRequest<NotifyMessage> getRequest() {
        return request;
    }

    /**
     * Setter method for property <tt>request</tt>.
     * 
     * @param request value to be assigned to property request
     */
    public void setRequest(NotifyRequest<NotifyMessage> request) {
        this.request = request;
    }

}
