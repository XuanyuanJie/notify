/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config.server.listener;

import org.cadre.notify.domain.config.listener.adapter.NotifyConfigListenerAdapter;
import org.cadre.notify.domain.config.server.NotifyServerConfig;

/**
 * 
 * @author ÐùÔ¯
 * @version $Id: NotifyServerConfigListener.java, v 0.1 2011-10-31 ÏÂÎç01:49:18 ÐùÔ¯ Exp $
 */
public class NotifyServerConfigListener extends NotifyConfigListenerAdapter<NotifyServerConfig> {

    private static final String                    MODULE_NAME = "notifyServerConfig";

    public static final NotifyServerConfigListener instance    = new NotifyServerConfigListener();

    public static NotifyServerConfigListener getInstance() {
        return instance;
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public void verify() {

    }

}
