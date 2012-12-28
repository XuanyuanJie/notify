/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.cadre.notify.domain.config.NotifyConfig;

/**
 * <p>������Ϣ���������</p>
 * @author ��ԯ
 * @version $Id: NotifyServerConfig.java, v 0.1 2011-10-31 ����01:35:53 ��ԯ Exp $
 */
public final class NotifyServerConfig extends NotifyConfig {

    /**  */
    private static final long          serialVersionUID = -5495689534283112970L;
    /** Master*/
    private boolean                    master;

    private InetSocketAddress          localInetSocketAddress;
    /** �Ƿ�������Ϣ����*/
    private volatile boolean           enablePublish    = true;
    /** �Ƿ�������Ϣ����*/
    private volatile boolean           enableRecover    = true;

    /*============================================== �������������� ====================================================*/
    /**  ������Processor���Զ����processor����,-1���ʾĬ��,Ĭ��Ϊ ����CPU����+1*/
    private volatile int               processorCount   = -1;
    /**  �̳߳ش�С,Ĭ��100*/
    private volatile int               threadPoolSize   = 100;
    /**  �Ƿ����ð�����*/
    private volatile boolean           enableWhiteList;
    /**  �Ƿ����ú�����*/
    private volatile boolean           enableBlackList;

    /*============================================== ������ ��չ���� ====================================================*/
    /**  ������*/
    private volatile List<InetAddress> whiteList        = new ArrayList<InetAddress>();
    /**  ������*/
    private volatile List<InetAddress> blackList        = new ArrayList<InetAddress>();

    /**
     * Getter method for property <tt>localInetSocketAddress</tt>.
     * 
     * @return property value of localInetSocketAddress
     */
    public InetSocketAddress getLocalInetSocketAddress() {
        return localInetSocketAddress;
    }

    /**
     * Setter method for property <tt>localInetSocketAddress</tt>.
     * 
     * @param localInetSocketAddress value to be assigned to property localInetSocketAddress
     */
    public void setLocalInetSocketAddress(InetSocketAddress localInetSocketAddress) {
        this.localInetSocketAddress = localInetSocketAddress;
    }

    /**
     * Getter method for property <tt>master</tt>.
     * 
     * @return property value of master
     */
    public boolean isMaster() {
        return master;
    }

    /**
     * Setter method for property <tt>master</tt>.
     * 
     * @param master value to be assigned to property master
     */
    public void setMaster(boolean master) {
        this.master = master;
    }

    /**
     * Getter method for property <tt>enablePublish</tt>.
     * 
     * @return property value of enablePublish
     */
    public boolean isEnablePublish() {
        return enablePublish;
    }

    /**
     * Setter method for property <tt>enablePublish</tt>.
     * 
     * @param enablePublish value to be assigned to property enablePublish
     */
    public void setEnablePublish(boolean enablePublish) {
        this.enablePublish = enablePublish;
    }

    /**
     * Getter method for property <tt>enableRecover</tt>.
     * 
     * @return property value of enableRecover
     */
    public boolean isEnableRecover() {
        return enableRecover;
    }

    /**
     * Setter method for property <tt>enableRecover</tt>.
     * 
     * @param enableRecover value to be assigned to property enableRecover
     */
    public void setEnableRecover(boolean enableRecover) {
        this.enableRecover = enableRecover;
    }

    /**
     * Getter method for property <tt>processorCount</tt>.
     * 
     * @return property value of processorCount
     */
    public int getProcessorCount() {
        return processorCount;
    }

    /**
     * Setter method for property <tt>processorCount</tt>.
     * 
     * @param processorCount value to be assigned to property processorCount
     */
    public void setProcessorCount(int processorCount) {
        this.processorCount = processorCount;
    }

    /**
     * Getter method for property <tt>threadPoolSize</tt>.
     * 
     * @return property value of threadPoolSize
     */
    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    /**
     * Setter method for property <tt>threadPoolSize</tt>.
     * 
     * @param threadPoolSize value to be assigned to property threadPoolSize
     */
    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    /**
     * Getter method for property <tt>enableWhiteList</tt>.
     * 
     * @return property value of enableWhiteList
     */
    public boolean isEnableWhiteList() {
        return enableWhiteList;
    }

    /**
     * Setter method for property <tt>enableWhiteList</tt>.
     * 
     * @param enableWhiteList value to be assigned to property enableWhiteList
     */
    public void setEnableWhiteList(boolean enableWhiteList) {
        this.enableWhiteList = enableWhiteList;
    }

    /**
     * Getter method for property <tt>enableBlackList</tt>.
     * 
     * @return property value of enableBlackList
     */
    public boolean isEnableBlackList() {
        return enableBlackList;
    }

    /**
     * Setter method for property <tt>enableBlackList</tt>.
     * 
     * @param enableBlackList value to be assigned to property enableBlackList
     */
    public void setEnableBlackList(boolean enableBlackList) {
        this.enableBlackList = enableBlackList;
    }

    /**
     * Getter method for property <tt>whiteList</tt>.
     * 
     * @return property value of whiteList
     */
    public List<InetAddress> getWhiteList() {
        return whiteList;
    }

    /**
     * Setter method for property <tt>whiteList</tt>.
     * 
     * @param whiteList value to be assigned to property whiteList
     */
    public void setWhiteList(List<InetAddress> whiteList) {
        this.whiteList = whiteList;
    }

    /**
     * Getter method for property <tt>blackList</tt>.
     * 
     * @return property value of blackList
     */
    public List<InetAddress> getBlackList() {
        return blackList;
    }

    /**
     * Setter method for property <tt>blackList</tt>.
     * 
     * @param blackList value to be assigned to property blackList
     */
    public void setBlackList(List<InetAddress> blackList) {
        this.blackList = blackList;
    }

    public void addBlackAddress(InetAddress inetAddress) {
        this.blackList.add(inetAddress);
    }

    public void addWhiteAddress(InetAddress inetAddress) {
        this.whiteList.add(inetAddress);
    }
}
