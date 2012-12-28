/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config;

import org.cadre.notify.domain.config.connector.NotifyConnectorConfig;
import org.cadre.notify.domain.config.connector.listener.NotifyConnectorConfigListener;
import org.cadre.notify.domain.config.server.NotifyServerConfig;
import org.cadre.notify.domain.config.server.listener.NotifyServerConfigListener;

/**
 * <p>Notify���ù���</p>
 * @author ��ԯ
 * @version $Id: NotifyConfigFactory.java, v 0.1 2011-10-31 ����01:06:04 ��ԯ Exp $
 */
public abstract class NotifyConfigFactory {

    /**
     * ��ȡNotifyServer����
     * 
     * @return
     */
    public static NotifyServerConfig getNotifyServerConfig() {
        return NotifyServerConfigListener.getInstance().getConfigObject();
    }

    /**
     * ��ȡNotifyConnector����
     * 
     * @return
     */
    public static NotifyConnectorConfig getNotifyConnectorConfig() {
        return NotifyConnectorConfigListener.getInstance().getConfigObject();
    }
}
