/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.startup.listener;

import org.cadre.notify.startup.NotifyServer;

/**
 *  �رշ��������
 * @author ��ԯ
 * @version $Id: ShutdownListener.java, v 0.1 2011-11-2 ����04:01:09 ��ԯ Exp $
 */
public class ShutdownListener extends Thread {

    private NotifyServer notifyServer;

    public ShutdownListener(NotifyServer notifyServer) {
        this.notifyServer = notifyServer;
    }

    public void start() {
    }
}
