/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.manager;

/**
 * manager生命周期接口，所有Manager都将实现此接口
 * @author 轩辕
 * @version $Id: ManagerLifeCycle.java, v 0.1 2011-11-1 上午10:13:32 轩辕 Exp $
 */
public interface ManagerLifeCycle {
    /** 启动*/
    public void start();

    /** 停止*/
    public void stop();

    /** 得到索引*/
    public int getIndex();
}
