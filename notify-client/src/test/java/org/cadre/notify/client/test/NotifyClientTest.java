/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.client.test;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.cadre.notify.domain.notify.NotifyMessage;
import org.cadre.notify.domain.request.NotifyRequest;

/**
 * 
 * @author 轩辕
 * @version $Id: NotifyClientTest.java, v 0.1 2011-11-3 下午02:35:48 轩辕 Exp $
 */
public class NotifyClientTest {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        //Create TCP/IP connection   
        NioSocketConnector connector = new NioSocketConnector();

        //创建接受数据的过滤器   
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();

        //设定这个过滤器将一行一行(/r/n)的读取数据   
        //        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));
        chain.addLast("myChin", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        //服务器的消息处理器：一个SamplMinaServerHander对象   
        connector.setHandler(new SamplMinaClientHander());

        //set connect timeout   
        connector.setConnectTimeout(30);

        //连接到服务器：   
        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", 9988));

        //Wait for the connection attempt to be finished.   
        cf.awaitUninterruptibly();
        NotifyTestRequest request = new NotifyTestRequest();
        request.setGruop("TEST_GROUP");
        request.setMessage(new NotifyMessage());

        cf.getSession().write(request);

        connector.dispose();

    }

}
