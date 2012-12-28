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
 * @author ��ԯ
 * @version $Id: NotifyClientTest.java, v 0.1 2011-11-3 ����02:35:48 ��ԯ Exp $
 */
public class NotifyClientTest {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        //Create TCP/IP connection   
        NioSocketConnector connector = new NioSocketConnector();

        //�����������ݵĹ�����   
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();

        //�趨�����������һ��һ��(/r/n)�Ķ�ȡ����   
        //        chain.addLast("myChin", new ProtocolCodecFilter(new TextLineCodecFactory()));
        chain.addLast("myChin", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        //����������Ϣ��������һ��SamplMinaServerHander����   
        connector.setHandler(new SamplMinaClientHander());

        //set connect timeout   
        connector.setConnectTimeout(30);

        //���ӵ���������   
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
