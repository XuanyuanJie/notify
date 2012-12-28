/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response.enums;

/**
 *  命令类型
 * @author 轩辕
 * @version $Id: CommandType.java, v 0.1 2011-11-2 下午03:38:01 轩辕 Exp $
 */
public enum CommandType {

    /** DEFAULT*/
    NONE,

    /** 心跳*/
    HEARTBEAT,

    //
    ;

    public short getValue() {
        switch (this) {
            case NONE:
                return 0x0000;
            case HEARTBEAT:
                return 0x0001;

        }
        throw new IllegalArgumentException("Unknown CommandType," + this);
    }

    public static CommandType valueOf(short value) {
        switch (value) {
            case 0x0000:
                return NONE;
            case 0x0001:
                return HEARTBEAT;
        }
        throw new IllegalArgumentException("Unknown CommandType value ," + value);
    }
}
