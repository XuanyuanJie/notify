/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.client.test;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 
 * @author ÐùÔ¯
 * @version $Id: SamplMinaClientHander.java, v 0.1 2011-11-3 ÏÂÎç02:40:19 ÐùÔ¯ Exp $
 */
public class SamplMinaClientHander implements IoHandler {

    public void sessionCreated(IoSession session) throws Exception {
    }

    public void sessionOpened(IoSession session) throws Exception {
    }

    public void sessionClosed(IoSession session) throws Exception {
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println(message);
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println(message);
    }

}
