/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.enums;

import java.io.Serializable;

/**
 * <p>Notify ����</p>
 * <ul>
 *  <li>N   -NONE(Ĭ����Ϣ����)
 *  <li>M   -MESSAGE(��ͨ��Ϣ����)
 *  <li>E   -EVENT(�¼���Ϣ����)
 * </ul>
 * 
 * @author ��ԯ
 * @version $Id: MessageType.java, v 0.1 2011-10-31 ����09:57:16 ��ԯ Exp $
 */
public enum MessageType implements Serializable {
    /** Ĭ����Ϣ����*/
    NONE("N", "Ĭ����Ϣ����"),
    /** ��ͨ��Ϣ���� */
    MESSAGE("M", "��ͨ��Ϣ����"),
    /** �¼���Ϣ���� */
    EVENT("E", "�¼���Ϣ����"), ;

    private String code;
    private String desc;

    /**
     * @param code
     * @param desc
     */
    private MessageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     * 
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     * 
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

}