/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config.connector;

import org.cadre.notify.domain.config.NotifyConfig;

/**
 * 
 * @author ��ԯ
 * @version $Id: NotifyConnectorConfig.java, v 0.1 2011-11-4 ����11:58:07 ��ԯ Exp $
 */
public class NotifyConnectorConfig extends NotifyConfig {

    /**  */
    private static final long serialVersionUID = 2844918926848970730L;

    /*============================================== ������ �������� ====================================================*/
    /**  ������Processor���Զ����processor����,-1���ʾĬ��,Ĭ��Ϊ ����CPU����+1*/
    private volatile int      processorCount   = -1;

    /*============================================== ������ ��չ���� ====================================================*/

    /**
     * Getter method for property <tt>processorCount</tt>.
     * 
     * @return property value of processorCount
     */
    public int getProcessorCount() {
        return processorCount;
    }

    /**
     * Setter method for property <tt>processorCount</tt>.
     * 
     * @param processorCount value to be assigned to property processorCount
     */
    public void setProcessorCount(int processorCount) {
        this.processorCount = processorCount;
    }

}
