/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service;

import org.cadre.notify.server.remoting.service.exception.NotifyRemotingException;

/**
 * 
 * @author ��ԯ
 * @version $Id: RemotingController.java, v 0.1 2011-11-1 ����11:14:27 ��ԯ Exp $
 */
public interface RemotingController {
    /**
     * ����Remoting������
     * 
     * @throws NotifyRemotingException
     * 
     */
    public void start() throws NotifyRemotingException;

    /**
     * �ر�Remoting������
     * 
     * @throws NotifyRemotingException
     * 
     */
    public void stop() throws NotifyRemotingException;

    /**
     * �ж�ͨѶ����Ƿ�����
     * 
     * @return
     */
    public boolean isStarted();
}
