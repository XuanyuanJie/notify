/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain;

import org.cadre.notify.domain.utils.NotifyUtils;

/**
 *  Notify ��������ǩ
 * @author ��ԯ
 * @version $Id: NotifyServerTag.java, v 0.1 2011-10-31 ����04:11:43 ��ԯ Exp $
 */
public class NotifyServerTag {

    private static String serverTag = NotifyUtils.getHostName();

    public static String getServerTag() {
        return serverTag;
    }

    public static void setServerTag(String serverTag) {
        if (serverTag.length() > 32) {
            NotifyServerTag.serverTag = serverTag.substring(0, 32);// ��ֹ���ȳ���32λ
        } else {
            NotifyServerTag.serverTag = serverTag;
        }
    }
}
