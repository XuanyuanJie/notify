/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service;

import java.net.InetSocketAddress;
import java.net.URI;

import org.cadre.notify.domain.config.server.NotifyServerConfig;

/**
 * Remoting������
 * @author ��ԯ
 * @version $Id: RemotingServer.java, v 0.1 2011-11-1 ����11:16:51 ��ԯ Exp $
 */
public interface RemotingServer extends RemotingController {
    /**
     * ���÷��������ã������˿ڡ�TCPѡ���
     * 
     * @param serverConfig
     */
    public void setNotifyServerConfig(NotifyServerConfig notifyServerConfig);

    /**
     * ���ؿɹ����ӵ�URI
     * 
     * @return
     */
    public URI getConnectURI();

    /**
     * ���ذ󶨵�ַ
     * 
     * @return
     */
    public InetSocketAddress getInetSocketAddress();

}
