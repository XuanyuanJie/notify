/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.request.callback;

import java.io.Serializable;

import org.cadre.notify.domain.response.ResponseCommand;

/**
 * ����Ļص�����
 * @author ��ԯ
 * @version $Id: RequestCallBack.java, v 0.1 2011-11-2 ����03:30:09 ��ԯ Exp $
 */
public interface RequestCallBack extends Serializable {

    /**
     * 
     * ִ������
     * @param responseCommand
     */
    public void onResponse(ResponseCommand responseCommand);
}
