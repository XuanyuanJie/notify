/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.client.service;

import java.net.InetSocketAddress;

import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.request.NotifyRequest;

/**
 *  ÏûÏ¢Connector
 * @author ÐùÔ¯
 * @version $Id: NotifyConnector.java, v 0.1 2011-11-4 ÉÏÎç11:26:42 ÐùÔ¯ Exp $
 */
public interface NotifyConnector {

    public void initConnector();

    public void connect(InetSocketAddress inetSocketAddress);

    public void sendMessage(NotifyRequest<Message> request);

    public void dispose();
}
