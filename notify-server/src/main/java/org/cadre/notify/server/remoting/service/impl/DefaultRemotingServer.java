/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.firewall.BlacklistFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.cadre.notify.domain.config.NotifyConfig;
import org.cadre.notify.domain.config.server.NotifyServerConfig;
import org.cadre.notify.server.firewall.WhiteListFilter;
import org.cadre.notify.server.handler.NotifyHandler;
import org.cadre.notify.server.remoting.service.RemotingServer;
import org.cadre.notify.server.remoting.service.exception.NotifyRemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Remoting Server 的默认实现
 * @author 轩辕
 * @version $Id: DefaultRemotingServer.java, v 0.1 2011-11-1 上午11:28:07 轩辕 Exp $
 */
public class DefaultRemotingServer extends RemotingControllerAdapter implements RemotingServer {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRemotingServer.class);

    public void setNotifyServerConfig(NotifyServerConfig notifyServerConfig) {
        this.notifyConfig = notifyServerConfig;
    }

    @Override
    protected void doStart() throws NotifyRemotingException {
        NotifyServerConfig notifyServerConfig = (NotifyServerConfig) this.notifyConfig;
        try {
            this.acceptor.bind(notifyServerConfig.getLocalInetSocketAddress());
        } catch (IOException e) {
            String errorMsg = "绑定端口异常,服务启动失败";
            logger.error(errorMsg, e);
            throw new NotifyRemotingException(errorMsg, e);
        }
    }

    @Override
    protected void doStop() throws NotifyRemotingException {
        NotifyServerConfig notifyServerConfig = (NotifyServerConfig) this.notifyConfig;
        this.acceptor.unbind(notifyServerConfig.getLocalInetSocketAddress());
    }

    @Override
    protected IoAcceptor initController(NotifyConfig config) {
        NotifyServerConfig notifyServerConfig = (NotifyServerConfig) this.notifyConfig;
        IoAcceptor acceptor;
        //判断Processor池是否自定义 默认CPU个数+1
        if (notifyServerConfig.getProcessorCount() != -1) {
            acceptor = new NioSocketAcceptor(notifyServerConfig.getProcessorCount());
        } else {
            acceptor = new NioSocketAcceptor();
        }
        // 默认100个的线程池  
        Executor threadPool = Executors.newFixedThreadPool(notifyServerConfig.getThreadPoolSize());
        acceptor.getFilterChain().addLast("exector", new ExecutorFilter(threadPool));
        //白名单 
        if (notifyServerConfig.isEnableWhiteList()) {
            WhiteListFilter whiteListFilter = new WhiteListFilter();
            whiteListFilter.setWhitelist(notifyServerConfig.getWhiteList().toArray(
                new InetAddress[1]));
            acceptor.getFilterChain().addFirst("whiteListFilter", whiteListFilter);
        }
        //黑名单 mina提供了黑名单功能
        if (notifyServerConfig.isEnableBlackList()) {
            BlacklistFilter blacklistFilter = new BlacklistFilter();
            blacklistFilter.setBlacklist(notifyServerConfig.getBlackList().toArray(
                new InetAddress[1]));
            acceptor.getFilterChain().addFirst("blackListFilter", blacklistFilter);
        }
        acceptor.getFilterChain().addLast("myChin",
            new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));//FIXME 格式转换 必须实现Serialization接口
        acceptor.setHandler(new NotifyHandler());//FIXME handler处理优化
        return acceptor;
    }

    public URI getConnectURI() {
        return null;
    }

    public InetSocketAddress getInetSocketAddress() {
        return null;
    }

}
