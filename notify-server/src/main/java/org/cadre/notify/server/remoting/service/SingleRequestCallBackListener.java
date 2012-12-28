/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.mina.core.session.IoSession;
import org.cadre.notify.domain.notify.NotifyMessage;
import org.cadre.notify.domain.response.NotifyResponse;

/**
 * 单个分组的单个连接的应答回调监听器
 * @author 轩辕
 * @version $Id: SingleRequestCallBackListener.java, v 0.1 2011-11-1 下午03:42:53 轩辕 Exp $
 */
public interface SingleRequestCallBackListener {
    /**
     * 处理应答
     * 
     * @param responseCommand
     *            应答命令
     * @param session
     *            应答连接
     */
    public void onResponse(NotifyResponse<NotifyMessage> response, IoSession session);

    /**
     * 异常发生的时候回调
     * 
     * @param e
     */
    public void onException(Exception e);

    /**
     * onResponse回调执行的线程池
     * 
     * @return
     */
    public ThreadPoolExecutor getExecutor();
}
