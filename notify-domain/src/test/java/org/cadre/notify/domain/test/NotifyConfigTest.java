/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.test;

import org.cadre.notify.domain.config.ConfigController;
import org.cadre.notify.domain.config.ConfigControllerImpl;

/**
 * 配置文件加载机制测试类
 * @author 轩辕
 * @version $Id: NotifyConfigTest.java, v 0.1 2011-10-31 下午04:49:49 轩辕 Exp $
 */
public class NotifyConfigTest {

    public static void main(String[] args) {
        ConfigController configController = ConfigControllerImpl.getInstance();
        configController.reload();
    }
}
