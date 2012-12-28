/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.client.service.achieve;

import java.net.InetSocketAddress;

import org.cadre.notify.client.service.impl.NotifyConnectorProvider;
import org.cadre.notify.domain.Message;
import org.cadre.notify.domain.request.NotifyRequest;

/**
 * NotifyConnector的默认实现
 * @author 轩辕
 * @version $Id: DefaultNotifyConnector.java, v 0.1 2011-11-4 下午12:10:01 轩辕 Exp $
 */
public class DefaultNotifyConnector extends NotifyConnectorProvider {

    public void connect(InetSocketAddress inetSocketAddress) {

    }

    public void sendMessage(NotifyRequest<Message> request) {

    }

}
