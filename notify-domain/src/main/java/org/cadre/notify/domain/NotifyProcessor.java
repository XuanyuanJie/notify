/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain;

import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.mina.core.session.IoSession;

/**
 * <p>消息处理器接口</p>
 * @author 轩辕
 * @version $Id: NotifyProcessor.java, v 0.1 2011-10-31 上午10:18:26 轩辕 Exp $
 */
public interface NotifyProcessor<C extends ProcessorContext> extends Serializable {

    /**
     * 用户自定义的线程池，如果提供，那么请求的处理都将在该线程池内执行
     * 
     * @return
     */
    public ThreadPoolExecutor getExecutor();

    /**
     * 消息前置处理
     * 
     * @param context
     */
    public void beforeProcess(C context, IoSession session);

    /**
     * 执行处理
     * @param context
     */
    public void process(C context, IoSession session);

    /**
     * 消息后置处理
     * @param context
     */
    public void afterProcess(C context, IoSession session);
}
