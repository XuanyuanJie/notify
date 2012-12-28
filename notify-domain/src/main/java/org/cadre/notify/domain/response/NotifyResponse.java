/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response;

import java.io.Serializable;

import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.response.enums.ResponseStatus;

/**
 * <p>Notify ��Ӧ����</p>
 * @author ��ԯ
 * @version $Id: NotifyResponse.java, v 0.1 2011-10-31 ����10:12:12 ��ԯ Exp $
 */
public interface NotifyResponse<M extends Message> extends Serializable {
    /*========================== ���Ķ�����Ϣ���� =========== begin ============ */
    /**  */
    public String getRequestId();

    /** */
    public void setRequestId(String requestId);

    /** */
    public M getMessage();

    /**  */
    public void setMessage(M message);

    /*========================== ���Ķ�����Ϣ���� =========== end ============ */
    public ResponseStatus getResponseStatus();

    public void setResponseStatus(ResponseStatus responseStatus);

}
