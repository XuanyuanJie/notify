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
 * <p>Notify �������</p>
 * @author ��ԯ
 * @version $Id: NotifyRequest.java, v 0.1 2011-10-31 ����09:53:59 ��ԯ Exp $
 */
public interface NotifyRequest<M extends Message> extends Serializable {

    /*========================== ���Ķ�����Ϣ���� =========== begin ============ */
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
    /*========================== ���Ķ�����Ϣ���� =========== end ============ */
}
