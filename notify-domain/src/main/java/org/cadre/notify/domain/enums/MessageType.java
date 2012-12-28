/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.enums;

import java.io.Serializable;

/**
 * <p>Notify 类型</p>
 * <ul>
 *  <li>N   -NONE(默认消息类型)
 *  <li>M   -MESSAGE(普通消息类型)
 *  <li>E   -EVENT(事件消息类型)
 * </ul>
 * 
 * @author 轩辕
 * @version $Id: MessageType.java, v 0.1 2011-10-31 上午09:57:16 轩辕 Exp $
 */
public enum MessageType implements Serializable {
    /** 默认消息类型*/
    NONE("N", "默认消息类型"),
    /** 普通消息类型 */
    MESSAGE("M", "普通消息类型"),
    /** 事件消息类型 */
    EVENT("E", "事件消息类型"), ;

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
