/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 轩辕
 * @version $Id: NotifyUtils.java, v 0.1 2011-10-31 下午03:45:23 轩辕 Exp $
 */
public abstract class NotifyUtils {
    static final Logger         logger                = LoggerFactory.getLogger(NotifyUtils.class);

    private static final String DEFAULT_INSTANCE_NAME = "notify";

    public static String getNotifyInstanceName() {
        String instanceName = System.getProperty("notify.instance.name");
        if (instanceName == null) {
            instanceName = DEFAULT_INSTANCE_NAME;
        }
        return instanceName;
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.error("获取本机Host失败", e);
            return "";
        }
    }
}
