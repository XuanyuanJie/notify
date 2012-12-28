/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service;

import org.cadre.notify.server.remoting.service.exception.NotifyRemotingException;

/**
 * 
 * @author 轩辕
 * @version $Id: RemotingController.java, v 0.1 2011-11-1 上午11:14:27 轩辕 Exp $
 */
public interface RemotingController {
    /**
     * 启动Remoting控制器
     * 
     * @throws NotifyRemotingException
     * 
     */
    public void start() throws NotifyRemotingException;

    /**
     * 关闭Remoting控制器
     * 
     * @throws NotifyRemotingException
     * 
     */
    public void stop() throws NotifyRemotingException;

    /**
     * 判断通讯组件是否启动
     * 
     * @return
     */
    public boolean isStarted();
}
