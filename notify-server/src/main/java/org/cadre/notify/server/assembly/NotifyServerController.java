/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.assembly;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.cadre.notify.domain.config.NotifyConfigFactory;
import org.cadre.notify.domain.utils.GenericUtils;
import org.cadre.notify.server.NotifyTCPServer;
import org.cadre.notify.server.manager.ManagerLifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  ����Manager�Ŀ�����
 * @author ��ԯ
 * @version $Id: NotifyServerController.java, v 0.1 2011-11-1 ����10:18:20 ��ԯ Exp $
 */
public class NotifyServerController implements ManagerLifeCycle {
    static final Logger      logger = LoggerFactory.getLogger(NotifyServerController.class);
    /** ������ʶ*/
    private volatile boolean started;

    private NotifyTCPServer  notifyTCPServer;

    /** 
     * @see org.cadre.notify.server.manager.ManagerLifeCycle#start()
     */
    public synchronized void start() {
        logger.warn("����ʵʱͳ��");
        List<ManagerLifeCycle> managerList = this.getManagerList();
        // ��������˳������
        Collections.sort(managerList, new ManagerComparator(true));
        this.startManager(managerList);
        this.started = true;
    }

    /** 
     * @see org.cadre.notify.server.manager.ManagerLifeCycle#stop()
     */
    public void stop() {
        if (!this.started) {
            return;
        }
        this.started = false;
        logger.warn("�ر�ʵʱͳ��");
        List<ManagerLifeCycle> managerList = this.getManagerList();
        // �������򣬲��ر�
        Collections.sort(managerList, new ManagerComparator(false));
        this.stopManager(managerList);
        //        ProcessRegister.getInstance().clear(); 
        //FIXME ��մ���Ĵ����е���Ϣ
    }

    private void stopManager(List<ManagerLifeCycle> managerList) {
        for (ManagerLifeCycle manager : managerList) {
            try {
                manager.stop();
                logger.warn("�ر�" + manager.getClass().getSimpleName() + "�ɹ�");
            } catch (Exception e) {
                if (manager != null) {
                    logger.error("�ر�" + manager.getClass().getSimpleName() + "ʧ��", e);
                }
            }
        }
    }

    /** 
     * @see org.cadre.notify.server.manager.ManagerLifeCycle#getIndex()
     */
    public int getIndex() {
        return Integer.MAX_VALUE;
    }

    private void startManager(List<ManagerLifeCycle> managerList) {
        for (ManagerLifeCycle manager : managerList) {
            if (!NotifyConfigFactory.getNotifyServerConfig().isEnableRecover()) {
                logger.warn("��������" + manager.getClass().getSimpleName() + "...");
                continue;
            }
            try {
                logger.warn("��������" + manager.getClass().getSimpleName() + "...");
                manager.start();
                logger.warn("����" + manager.getClass().getSimpleName() + "�ɹ�");
            } catch (Exception e) {
                if (manager != null) {
                    logger.error("����" + manager.getClass().getSimpleName() + "ʧ��", e);
                }
            }
        }
    }

    private List<ManagerLifeCycle> getManagerList() {
        BeanInfo beanInfo = GenericUtils.getBeanInfo(this);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        List<ManagerLifeCycle> managerList = new ArrayList<ManagerLifeCycle>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Object manager = null;
            try {
                manager = propertyDescriptor.getReadMethod().invoke(this);
                if (manager != null && manager instanceof ManagerLifeCycle) {
                    managerList.add((ManagerLifeCycle) manager);
                }
            } catch (Exception e) {
                if (manager != null) {
                    logger.error("��ȡ" + propertyDescriptor.getName() + "ʧ��", e);
                }
            }
        }
        return managerList;
    }

    static private class ManagerComparator implements Comparator<ManagerLifeCycle>, Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1405198920349829323L;
        /**
         * �Ƿ���������
         */
        private boolean           asc              = true;

        public ManagerComparator(boolean asc) {
            super();
            this.asc = asc;
        }

        public int compare(ManagerLifeCycle o1, ManagerLifeCycle o2) {
            return this.asc ? o1.getIndex() - o2.getIndex() : o2.getIndex() - o1.getIndex();
        }

    }

    /**
     * Getter method for property <tt>started</tt>.
     * 
     * @return property value of started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Getter method for property <tt>notifyTCPServer</tt>.
     * 
     * @return property value of notifyTCPServer
     */
    public NotifyTCPServer getNotifyTCPServer() {
        return notifyTCPServer;
    }

    /**
     * Setter method for property <tt>notifyTCPServer</tt>.
     * 
     * @param notifyTCPServer value to be assigned to property notifyTCPServer
     */
    public void setNotifyTCPServer(NotifyTCPServer notifyTCPServer) {
        this.notifyTCPServer = notifyTCPServer;
    }

}
