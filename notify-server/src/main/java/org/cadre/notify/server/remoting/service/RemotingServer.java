/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service;

import java.net.InetSocketAddress;
import java.net.URI;

import org.cadre.notify.domain.config.server.NotifyServerConfig;

/**
 * Remoting服务器
 * @author 轩辕
 * @version $Id: RemotingServer.java, v 0.1 2011-11-1 上午11:16:51 轩辕 Exp $
 */
public interface RemotingServer extends RemotingController {
    /**
     * 设置服务器配置，包括端口、TCP选项等
     * 
     * @param serverConfig
     */
    public void setNotifyServerConfig(NotifyServerConfig notifyServerConfig);

    /**
     * 返回可供连接的URI
     * 
     * @return
     */
    public URI getConnectURI();

    /**
     * 返回绑定地址
     * 
     * @return
     */
    public InetSocketAddress getInetSocketAddress();

}
