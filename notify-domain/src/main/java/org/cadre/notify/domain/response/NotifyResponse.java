/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response;

import java.io.Serializable;

import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.response.enums.ResponseStatus;

/**
 * <p>Notify 相应对象</p>
 * @author 轩辕
 * @version $Id: NotifyResponse.java, v 0.1 2011-10-31 上午10:12:12 轩辕 Exp $
 */
public interface NotifyResponse<M extends Message> extends Serializable {
    /*========================== 核心对象信息处理 =========== begin ============ */
    /**  */
    public String getRequestId();

    /** */
    public void setRequestId(String requestId);

    /** */
    public M getMessage();

    /**  */
    public void setMessage(M message);

    /*========================== 核心对象信息处理 =========== end ============ */
    public ResponseStatus getResponseStatus();

    public void setResponseStatus(ResponseStatus responseStatus);

}
