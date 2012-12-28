/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response.enums;

/**
 * Ӧ��״̬
 * @author ��ԯ
 * @version $Id: ResponseStatus.java, v 0.1 2011-11-1 ����03:51:27 ��ԯ Exp $
 */
public enum ResponseStatus {
    NO_ERROR, // �����ɹ�
    ERROR, // ������Ӧ����������
    EXCEPTION, // �쳣
    UNKNOWN, // û��ע��Listener������CheckMessageListener��MessageListener
    THREADPOOL_BUSY, // ��Ӧ���̷߳�æ
    ERROR_COMM, // ͨѶ������������
    NO_PROCESSOR, // û�и���������Ĵ�����
    TIMEOUT; // ��Ӧ��ʱ

    public short getValue() {
        switch (this) {
            case NO_ERROR:
                return 0x0000;
            case ERROR:
                return 0x0001;
            case EXCEPTION:
                return 0x0002;
            case UNKNOWN:
                return 0x0003;
            case THREADPOOL_BUSY:
                return 0x0004;
            case ERROR_COMM:
                return 0x0005;
            case NO_PROCESSOR:
                return 0x0006;
            case TIMEOUT:
                return 0x0007;

        }
        throw new IllegalArgumentException("Unknown status," + this);
    }

    public static ResponseStatus valueOf(short value) {
        switch (value) {
            case 0x0000:
                return NO_ERROR;
            case 0x0001:
                return ERROR;
            case 0x0002:
                return EXCEPTION;
            case 0x0003:
                return UNKNOWN;
            case 0x0004:
                return THREADPOOL_BUSY;
            case 0x0005:
                return ERROR_COMM;
            case 0x0006:
                return NO_PROCESSOR;
            case 0x0007:
                return TIMEOUT;
        }
        throw new IllegalArgumentException("Unknown status value ," + value);
    }
}
