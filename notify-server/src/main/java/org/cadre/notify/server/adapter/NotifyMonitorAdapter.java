/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.adapter;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.cadre.notify.server.NotifyMonitor;

/**
 * 
 * @author ��ԯ
 * @version $Id: NotifyMonitorAdapter.java, v 0.1 2011-10-31 ����01:03:56 ��ԯ Exp $
 */
public abstract class NotifyMonitorAdapter implements NotifyMonitor {

    /**
     * ������Ϣ����
     */
    private IoAcceptor acceptor;
    
    private void init(){
        acceptor=new NioSocketAcceptor();
        
    }

    /** 
     * @see org.cadre.notify.server.NotifyMonitor#start()
     */
    public void start() {
    }

}
