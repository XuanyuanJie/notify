/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response.adapter;

import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.response.NotifyResponse;
import org.cadre.notify.domain.response.enums.ResponseStatus;

/**
 * 
 * @author 轩辕
 * @version $Id: NotifyResponseAdapter.java, v 0.1 2011-10-31 上午10:15:38 轩辕 Exp $
 */
public abstract class NotifyResponseAdapter<M extends Message> implements NotifyResponse<M> {

    /**  */
    private static final long serialVersionUID = -1470034674355483837L;

    /**  请求KEY */
    private String            requestId;

    /**  消息主体*/
    private M                 message;
    /** 应答状态*/
    private ResponseStatus    responseStatus;

    /**
     * Getter method for property <tt>requestId</tt>.
     * 
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     * 
     * @param requestId value to be assigned to property requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Getter method for property <tt>message</tt>.
     * 
     * @return property value of message
     */
    public M getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(M message) {
        this.message = message;
    }

    /**
     * Getter method for property <tt>responseStatus</tt>.
     * 
     * @return property value of responseStatus
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Setter method for property <tt>responseStatus</tt>.
     * 
     * @param responseStatus value to be assigned to property responseStatus
     */
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}
