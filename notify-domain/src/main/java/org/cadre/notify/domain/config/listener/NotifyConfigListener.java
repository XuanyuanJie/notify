/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config.listener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author ��ԯ
 * @version $Id: NotifyConfigListener.java, v 0.1 2011-10-31 ����01:42:38 ��ԯ Exp $
 */
public interface NotifyConfigListener<E extends Serializable> {

    /**
     * ģ������
     * 
     * @return module name
     */
    String getModuleName();

    /**
     * �������Listener��������ö���
     * 
     * @return
     */
    E getConfigObject();

    /**
     * �������Listener��������ö���
     * 
     * @param e
     */
    void setConfigObject(E e);

    /**
     * ������������
     * 
     * @param attributes
     */
    public void updateAttributes(Map<String, ? extends Object> attributes);

    /**
     * ����ĳһ����
     * 
     * @param name
     *            ������
     * @param newValue
     *            ��ֵ
     */
    public void updateAttribute(String name, Object newValue);

    public Object getAttributeVlaue(String attributeName);

    /**
     * ���¼�������
     * 
     * @param context
     *            TODO
     */
    public void load(ApplicationContext context);

    /**
     * ���ݵ�ǰ���ã����ر����ļ��ľ���·��
     * 
     * @return
     */
    public String backup() throws IOException;

    /**
     * ���浱ǰ���ã�������ԭ�����ļ�
     */
    public void save() throws IOException;

    /**
     * ���浽ָ���ļ�
     * 
     * @param path
     *            ����·��
     */
    public void save(String path) throws IOException;

    /**
     * ���ر�ģ����������ƣ�������ͬʱ��getter,setter������������
     * 
     * @return
     */
    public Set<String> getAttributeNames();

    /**
     * ���Ըı�������������Ըı��ʱ������֪ͨ
     * 
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public PropertyChangeSupport getPropertyChangeSupport();

    /**
     * 
     * ָ�������ļ���·��
     * 
     */
    void prepareConfigFilePath(String configFilePath);

    public void reload(ApplicationContext context);

    /**
     * ������У��
     */
    public void verify();

    /**
     * ����ģ��ı�ţ�ԽС�����ص�Խ��
     * 
     * @return
     */
    public int index();

    public String getConfigFilePath();
}
