/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config.connector;

import org.cadre.notify.domain.config.NotifyConfig;

/**
 * 
 * @author 轩辕
 * @version $Id: NotifyConnectorConfig.java, v 0.1 2011-11-4 上午11:58:07 轩辕 Exp $
 */
public class NotifyConnectorConfig extends NotifyConfig {

    /**  */
    private static final long serialVersionUID = 2844918926848970730L;

    /*============================================== 连接器 基本配置 ====================================================*/
    /**  服务器Processor池自定义的processor个数,-1则表示默认,默认为 机器CPU个数+1*/
    private volatile int      processorCount   = -1;

    /*============================================== 连接器 扩展配置 ====================================================*/

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
