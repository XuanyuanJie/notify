/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.holder;

import org.cadre.notify.domain.ProcessorContext;

/**
 * 当前业务处理器上下文的存储器
 * @author 轩辕
 * @version $Id: ProcessContextHolder.java, v 0.1 2011-11-1 上午09:33:04 轩辕 Exp $
 */
public class ProcessContextHolder {

    /**
     * ThreadLocal
     */
    private static ThreadLocal<ProcessorContext> holder = new ThreadLocal<ProcessorContext>();

    /**
     * 设置业务处理processor上下文
     * 
     * @param exterface
     */
    public static void setContext(ProcessorContext processContext) {
        holder.set(processContext);
    }

    /**
     * 获得当前业务处理processor上下文
     * 
     * @return
     */
    public static ProcessorContext getContext() {
        return (ProcessorContext) holder.get();
    }

    /**
     * 清空
     */
    public static void clean() {
        holder.set(null);
    }

}
