/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.request;

import java.io.Serializable;

import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.enums.MessageType;
import org.cadre.notify.domain.request.enums.ReqeustType;

/**
 * <p>Notify 请求对象</p>
 * @author 轩辕
 * @version $Id: NotifyRequest.java, v 0.1 2011-10-31 上午09:53:59 轩辕 Exp $
 */
public interface NotifyRequest<M extends Message> extends Serializable {

    /*========================== 核心对象信息处理 =========== begin ============ */
    /**  */
    public String getRequestId();

    /** */
    public void setRequestId(String requestId);

    /**  */
    public MessageType getMessageType();

    /** */
    public void setMessageType(MessageType messageType);

    /** */
    public ReqeustType getReqeustType();

    /** */
    public void setReqeustType(ReqeustType reqeustType);

    /**  */
    public String getGruop();

    /**  */
    public void setGruop(String gruop);

    /**  */
    public String getTopic();

    /** */
    public void setTopic(String topic);

    /** */
    public M getMessage();

    /**  */
    public void setMessage(M message);
    /*========================== 核心对象信息处理 =========== end ============ */
}
