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
 * <p>Notify配置工厂</p>
 * @author 轩辕
 * @version $Id: NotifyConfigFactory.java, v 0.1 2011-10-31 下午01:06:04 轩辕 Exp $
 */
public abstract class NotifyConfigFactory {

    /**
     * 获取NotifyServer配置
     * 
     * @return
     */
    public static NotifyServerConfig getNotifyServerConfig() {
        return NotifyServerConfigListener.getInstance().getConfigObject();
    }

    /**
     * 获取NotifyConnector配置
     * 
     * @return
     */
    public static NotifyConnectorConfig getNotifyConnectorConfig() {
        return NotifyConnectorConfigListener.getInstance().getConfigObject();
    }
}
