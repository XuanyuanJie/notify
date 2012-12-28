/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain;

import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.mina.core.session.IoSession;

/**
 * <p>��Ϣ�������ӿ�</p>
 * @author ��ԯ
 * @version $Id: NotifyProcessor.java, v 0.1 2011-10-31 ����10:18:26 ��ԯ Exp $
 */
public interface NotifyProcessor<C extends ProcessorContext> extends Serializable {

    /**
     * �û��Զ�����̳߳أ�����ṩ����ô����Ĵ������ڸ��̳߳���ִ��
     * 
     * @return
     */
    public ThreadPoolExecutor getExecutor();

    /**
     * ��Ϣǰ�ô���
     * 
     * @param context
     */
    public void beforeProcess(C context, IoSession session);

    /**
     * ִ�д���
     * @param context
     */
    public void process(C context, IoSession session);

    /**
     * ��Ϣ���ô���
     * @param context
     */
    public void afterProcess(C context, IoSession session);
}
