/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.request.callback;

import java.io.Serializable;

import org.cadre.notify.domain.response.ResponseCommand;

/**
 * 请求的回调方法
 * @author 轩辕
 * @version $Id: RequestCallBack.java, v 0.1 2011-11-2 下午03:30:09 轩辕 Exp $
 */
public interface RequestCallBack extends Serializable {

    /**
     * 
     * 执行命令
     * @param responseCommand
     */
    public void onResponse(ResponseCommand responseCommand);
}
