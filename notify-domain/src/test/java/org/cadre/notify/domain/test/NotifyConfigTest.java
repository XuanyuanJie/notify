/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.test;

import org.cadre.notify.domain.config.ConfigController;
import org.cadre.notify.domain.config.ConfigControllerImpl;

/**
 * �����ļ����ػ��Ʋ�����
 * @author ��ԯ
 * @version $Id: NotifyConfigTest.java, v 0.1 2011-10-31 ����04:49:49 ��ԯ Exp $
 */
public class NotifyConfigTest {

    public static void main(String[] args) {
        ConfigController configController = ConfigControllerImpl.getInstance();
        configController.reload();
    }
}
