/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response.enums;

/**
 *  ��������
 * @author ��ԯ
 * @version $Id: CommandType.java, v 0.1 2011-11-2 ����03:38:01 ��ԯ Exp $
 */
public enum CommandType {

    /** DEFAULT*/
    NONE,

    /** ����*/
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
