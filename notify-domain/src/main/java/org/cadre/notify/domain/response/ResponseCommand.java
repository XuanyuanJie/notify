/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response;

import java.io.Serializable;

import org.cadre.notify.domain.response.enums.CommandType;

/**
 * 
 * @author 轩辕
 * @version $Id: ResponseCommand.java, v 0.1 2011-11-1 下午05:25:54 轩辕 Exp $
 */
public interface ResponseCommand extends Serializable {

    /**
     * 获取命令类型
     * 
     * @return
     */
    public CommandType getCommandType();
}
