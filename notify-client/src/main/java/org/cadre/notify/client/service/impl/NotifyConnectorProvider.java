/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.client.service.impl;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.cadre.notify.client.service.NotifyConnector;
import org.cadre.notify.client.test.SamplMinaClientHander;
import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.config.NotifyConfigFactory;
import org.cadre.notify.domain.config.connector.NotifyConnectorConfig;
import org.cadre.notify.domain.request.NotifyRequest;

/**
 * @author 轩辕
 * @version $Id: NotifyConnectorProvider.java, v 0.1 2011-11-4 上午11:27:58 轩辕 Exp $
 */
public abstract class NotifyConnectorProvider implements NotifyConnector {

    private SocketConnector       connector;

    private NotifyConnectorConfig connectorConfig;

    private ConnectFuture         connectFuture;

    /** 
     * @see org.cadre.notify.client.service.NotifyConnector#initConnector()
     */
    public void initConnector() {
        initConnectorConfig();

        //初始化 设置 ProcessorCount
        if (connectorConfig.getProcessorCount() != -1 && connectorConfig.getProcessorCount() > 0) {
            connector = new NioSocketConnector(connectorConfig.getProcessorCount());
        } else {
            connector = new NioSocketConnector();
        }
        //设置基本参数
        //创建接受数据的过滤器   
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        //设置对象序列化chain
        chain.addLast("SerializationChain", new ProtocolCodecFilter(
            new ObjectSerializationCodecFactory()));

        //服务器的消息处理器：一个SamplMinaServerHander对象   
        connector.setHandler(new SamplMinaClientHander());

    }

    private void initConnectorConfig() {
        this.connectorConfig = NotifyConfigFactory.getNotifyConnectorConfig();
    }

    /** 
     * @see org.cadre.notify.client.service.NotifyConnector#connect(java.net.InetSocketAddress)
     */
    public void connect(InetSocketAddress inetSocketAddress) {
        connectFuture = connector.connect(inetSocketAddress);
    }

    /** 
     * @see org.cadre.notify.client.service.NotifyConnector#sendMessage(org.cadre.notify.domain.request.NotifyRequest)
     */
    public void sendMessage(NotifyRequest<Message> request) {
        connectFuture.getSession().write(request);
    }

    /** 
     * @see org.cadre.notify.client.service.NotifyConnector#dispose()
     */
    public void dispose() {
        connector.dispose();
    }

    /**
     * Getter method for property <tt>connector</tt>.
     * 
     * @return property value of connector
     */
    public SocketConnector getConnector() {
        return connector;
    }

    /**
     * Setter method for property <tt>connector</tt>.
     * 
     * @param connector value to be assigned to property connector
     */
    public void setConnector(SocketConnector connector) {
        this.connector = connector;
    }

    /**
     * Getter method for property <tt>connectorConfig</tt>.
     * 
     * @return property value of connectorConfig
     */
    public NotifyConnectorConfig getConnectorConfig() {
        return connectorConfig;
    }

    /**
     * Setter method for property <tt>connectorConfig</tt>.
     * 
     * @param connectorConfig value to be assigned to property connectorConfig
     */
    public void setConnectorConfig(NotifyConnectorConfig connectorConfig) {
        this.connectorConfig = connectorConfig;
    }

}
