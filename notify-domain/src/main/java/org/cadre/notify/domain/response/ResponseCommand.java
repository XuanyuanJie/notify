/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.response;

import java.io.Serializable;

import org.cadre.notify.domain.response.enums.CommandType;

/**
 * 
 * @author ��ԯ
 * @version $Id: ResponseCommand.java, v 0.1 2011-11-1 ����05:25:54 ��ԯ Exp $
 */
public interface ResponseCommand extends Serializable {

    /**
     * ��ȡ��������
     * 
     * @return
     */
    public CommandType getCommandType();
}
