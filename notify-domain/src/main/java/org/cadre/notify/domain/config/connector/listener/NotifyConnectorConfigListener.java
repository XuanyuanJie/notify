/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config.connector.listener;

import org.cadre.notify.domain.config.connector.NotifyConnectorConfig;
import org.cadre.notify.domain.config.listener.adapter.NotifyConfigListenerAdapter;

/**
 * 
 * @author ÐùÔ¯
 * @version $Id: NotifyConnectorConfigListener.java, v 0.1 2011-11-4 ÏÂÎç12:00:15 ÐùÔ¯ Exp $
 */
public class NotifyConnectorConfigListener extends
                                          NotifyConfigListenerAdapter<NotifyConnectorConfig> {

    private static final String                       MODULE_NAME = "notifyConnectorConfig";

    public static final NotifyConnectorConfigListener instance    = new NotifyConnectorConfigListener();

    public static NotifyConnectorConfigListener getInstance() {
        return instance;
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public void verify() {

    }
}
