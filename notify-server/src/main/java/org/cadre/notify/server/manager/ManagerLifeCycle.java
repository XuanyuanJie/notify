/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.manager;

/**
 * manager�������ڽӿڣ�����Manager����ʵ�ִ˽ӿ�
 * @author ��ԯ
 * @version $Id: ManagerLifeCycle.java, v 0.1 2011-11-1 ����10:13:32 ��ԯ Exp $
 */
public interface ManagerLifeCycle {
    /** ����*/
    public void start();

    /** ֹͣ*/
    public void stop();

    /** �õ�����*/
    public int getIndex();
}
