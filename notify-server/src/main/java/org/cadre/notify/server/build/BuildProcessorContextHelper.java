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
 * @author ��ԯ
 * @version $Id: BuildProcessorContextHelper.java, v 0.1 2011-11-2 ����02:27:30 ��ԯ Exp $
 */
public abstract class BuildProcessorContextHelper {

    /**
     * 
     * ��װHandleProcessor�������Ķ���,�����������
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
