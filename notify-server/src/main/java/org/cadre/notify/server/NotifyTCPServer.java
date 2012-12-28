/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server;

import org.cadre.notify.domain.config.NotifyConfigFactory;
import org.cadre.notify.domain.config.server.NotifyServerConfig;
import org.cadre.notify.server.manager.ManagerLifeCycle;
import org.cadre.notify.server.remoting.service.RemotingServer;
import org.cadre.notify.server.remoting.service.exception.NotifyRemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息TCP服务端
 * @author 轩辕
 * @version $Id: NotifyTCPServer.java, v 0.1 2011-11-1 上午11:05:55 轩辕 Exp $
 */
public class NotifyTCPServer implements ManagerLifeCycle {
    private static final Logger logger = LoggerFactory.getLogger(NotifyTCPServer.class);

    /**
     * 消息服务端配置信息
     */
    private NotifyServerConfig  notifyServerConfig;
    /**
     * 服务器
     */
    private RemotingServer      remotingServer;

    public void start() {
        this.initNotifyServerConfig();
        this.initRemotingServer();
        this.registerProcessorAndListener();
        this.startRemotingServer();
    }

    private void initNotifyServerConfig() {
        // 从配置中心获取ServerConfig
        this.notifyServerConfig = NotifyConfigFactory.getNotifyServerConfig();

    }

    private void startRemotingServer() {
        try {
            this.remotingServer.start();
        } catch (NotifyRemotingException e) {
            logger.error("启动Notify tcp server失败", e);
        }
    }

    private void initRemotingServer() {
        this.remotingServer.setNotifyServerConfig(notifyServerConfig);
    }

    private void registerProcessorAndListener() {
        //FIXME 注册
    }

    public void stop() {
        try {
            this.remotingServer.stop();
        } catch (NotifyRemotingException e) {
            logger.error("停止Notify tcp server失败", e);
        }
    }

    public int getIndex() {
        return 10;
    }

    /**
     * Getter method for property <tt>notifyServerConfig</tt>.
     * 
     * @return property value of notifyServerConfig
     */
    public NotifyServerConfig getNotifyServerConfig() {
        return notifyServerConfig;
    }

    /**
     * Setter method for property <tt>notifyServerConfig</tt>.
     * 
     * @param notifyServerConfig value to be assigned to property notifyServerConfig
     */
    public void setNotifyServerConfig(NotifyServerConfig notifyServerConfig) {
        this.notifyServerConfig = notifyServerConfig;
    }

    /**
     * Getter method for property <tt>remotingServer</tt>.
     * 
     * @return property value of remotingServer
     */
    public RemotingServer getRemotingServer() {
        return remotingServer;
    }

    /**
     * Setter method for property <tt>remotingServer</tt>.
     * 
     * @param remotingServer value to be assigned to property remotingServer
     */
    public void setRemotingServer(RemotingServer remotingServer) {
        this.remotingServer = remotingServer;
    }

}
