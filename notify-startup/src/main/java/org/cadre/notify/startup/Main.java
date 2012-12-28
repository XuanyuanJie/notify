/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ��Ϣ����������
 * @author ��ԯ
 * @version $Id: Main.java, v 0.1 2011-11-1 ����10:01:14 ��ԯ Exp $
 */
public class Main {
    static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        start(args);

    }

    /**
     * ��������
     * 
     * @param args
     * @return
     */
    private static NotifyServer start(String[] args) {
        
        // ģ��������Ϣ
        args=new String[]{"contextPath=src/main/resources"};
        //
        final NotifyServer notifyServer = new NotifyServer();
        notifyServer.startup(args);
        return notifyServer;
    }

}
