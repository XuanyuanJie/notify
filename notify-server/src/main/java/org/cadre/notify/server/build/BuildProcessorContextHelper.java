/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.build;

import org.cadre.notify.domain.ProcessorContext;
import org.cadre.notify.domain.handle.HandleProcessorContext;
import org.cadre.notify.domain.notify.NotifyMessage;
import org.cadre.notify.domain.request.NotifyRequest;

/**
 * 
 * @author 轩辕
 * @version $Id: BuildProcessorContextHelper.java, v 0.1 2011-11-2 下午02:27:30 轩辕 Exp $
 */
public abstract class BuildProcessorContextHelper {

    /**
     * 
     * 组装HandleProcessor的上下文对象,根据请求对象
     * @param <T> extends NotifyRequest<NotifyMessage>
     * @param request 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends NotifyRequest<NotifyMessage>, P extends ProcessorContext> P buildProcessContextForNotify(T request) {
        HandleProcessorContext context = new HandleProcessorContext();
        context.setRequest(request);
        return (P) context;
    }
}
