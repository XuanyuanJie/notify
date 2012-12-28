/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息中心启动类
 * @author 轩辕
 * @version $Id: Main.java, v 0.1 2011-11-1 上午10:01:14 轩辕 Exp $
 */
public class Main {
    static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        start(args);

    }

    /**
     * 启动服务
     * 
     * @param args
     * @return
     */
    private static NotifyServer start(String[] args) {
        
        // 模拟请求信息
        args=new String[]{"contextPath=src/main/resources"};
        //
        final NotifyServer notifyServer = new NotifyServer();
        notifyServer.startup(args);
        return notifyServer;
    }

}
