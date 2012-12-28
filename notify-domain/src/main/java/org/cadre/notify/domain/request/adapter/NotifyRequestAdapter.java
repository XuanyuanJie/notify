/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.request.adapter;

import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.enums.MessageType;
import org.cadre.notify.domain.request.NotifyRequest;
import org.cadre.notify.domain.request.enums.ReqeustType;

/**
 * <p>Notify �������������</p>
 * <p>
 *   �κ�����������̳��ڴ�������
 * </p>
 * @author ��ԯ
 * @version $Id: NotifyRequestAdapter.java, v 0.1 2011-10-31 ����09:55:31 ��ԯ Exp $
 */
public abstract class NotifyRequestAdapter<M extends Message> implements NotifyRequest<M> {

    /**  */
    private static final long serialVersionUID = -752817562478436307L;
    /**  ����KEY */
    private String            requestId;
    /**  ��Ϣ���� */
    private MessageType       messageType;
    /**  ��������*/
    private ReqeustType       reqeustType;
    /**  ��Ϣ��������Ϣ*/
    private String            gruop;
    /**  ��Ϣ������Ϣ*/
    private String            topic;
    /**  ��Ϣ����*/
    private M                 message;

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
     * Getter method for property <tt>messageType</tt>.
     * 
     * @return property value of messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Setter method for property <tt>messageType</tt>.
     * 
     * @param messageType value to be assigned to property messageType
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * Getter method for property <tt>reqeustType</tt>.
     * 
     * @return property value of reqeustType
     */
    public ReqeustType getReqeustType() {
        return reqeustType;
    }

    /**
     * Setter method for property <tt>reqeustType</tt>.
     * 
     * @param reqeustType value to be assigned to property reqeustType
     */
    public void setReqeustType(ReqeustType reqeustType) {
        this.reqeustType = reqeustType;
    }

    /**
     * Getter method for property <tt>gruop</tt>.
     * 
     * @return property value of gruop
     */
    public String getGruop() {
        return gruop;
    }

    /**
     * Setter method for property <tt>gruop</tt>.
     * 
     * @param gruop value to be assigned to property gruop
     */
    public void setGruop(String gruop) {
        this.gruop = gruop;
    }

    /**
     * Getter method for property <tt>topic</tt>.
     * 
     * @return property value of topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Setter method for property <tt>topic</tt>.
     * 
     * @param topic value to be assigned to property topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
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

}
