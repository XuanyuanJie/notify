/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.mina.core.session.IoSession;
import org.cadre.notify.domain.notify.NotifyMessage;
import org.cadre.notify.domain.response.NotifyResponse;

/**
 * ��������ĵ������ӵ�Ӧ��ص�������
 * @author ��ԯ
 * @version $Id: SingleRequestCallBackListener.java, v 0.1 2011-11-1 ����03:42:53 ��ԯ Exp $
 */
public interface SingleRequestCallBackListener {
    /**
     * ����Ӧ��
     * 
     * @param responseCommand
     *            Ӧ������
     * @param session
     *            Ӧ������
     */
    public void onResponse(NotifyResponse<NotifyMessage> response, IoSession session);

    /**
     * �쳣������ʱ��ص�
     * 
     * @param e
     */
    public void onException(Exception e);

    /**
     * onResponse�ص�ִ�е��̳߳�
     * 
     * @return
     */
    public ThreadPoolExecutor getExecutor();
}
