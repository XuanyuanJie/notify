/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.holder;

import org.cadre.notify.domain.ProcessorContext;

/**
 * ��ǰҵ�����������ĵĴ洢��
 * @author ��ԯ
 * @version $Id: ProcessContextHolder.java, v 0.1 2011-11-1 ����09:33:04 ��ԯ Exp $
 */
public class ProcessContextHolder {

    /**
     * ThreadLocal
     */
    private static ThreadLocal<ProcessorContext> holder = new ThreadLocal<ProcessorContext>();

    /**
     * ����ҵ����processor������
     * 
     * @param exterface
     */
    public static void setContext(ProcessorContext processContext) {
        holder.set(processContext);
    }

    /**
     * ��õ�ǰҵ����processor������
     * 
     * @return
     */
    public static ProcessorContext getContext() {
        return (ProcessorContext) holder.get();
    }

    /**
     * ���
     */
    public static void clean() {
        holder.set(null);
    }

}
