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
 * @author 轩辕
 * @version $Id: NotifyConfigListener.java, v 0.1 2011-10-31 下午01:42:38 轩辕 Exp $
 */
public interface NotifyConfigListener<E extends Serializable> {

    /**
     * 模块名称
     * 
     * @return module name
     */
    String getModuleName();

    /**
     * 返回这个Listener管理的配置对象
     * 
     * @return
     */
    E getConfigObject();

    /**
     * 设置这个Listener管理的配置对象
     * 
     * @param e
     */
    void setConfigObject(E e);

    /**
     * 批量更新属性
     * 
     * @param attributes
     */
    public void updateAttributes(Map<String, ? extends Object> attributes);

    /**
     * 更新某一属性
     * 
     * @param name
     *            属性名
     * @param newValue
     *            新值
     */
    public void updateAttribute(String name, Object newValue);

    public Object getAttributeVlaue(String attributeName);

    /**
     * 重新加载配置
     * 
     * @param context
     *            TODO
     */
    public void load(ApplicationContext context);

    /**
     * 备份当前配置，返回备份文件的绝对路径
     * 
     * @return
     */
    public String backup() throws IOException;

    /**
     * 保存当前配置，将覆盖原配置文件
     */
    public void save() throws IOException;

    /**
     * 保存到指定文件
     * 
     * @param path
     *            绝对路径
     */
    public void save(String path) throws IOException;

    /**
     * 返回本模块的属性名称，仅包括同时有getter,setter方法的属性名
     * 
     * @return
     */
    public Set<String> getAttributeNames();

    /**
     * 属性改变监听器，当属性改变的时候主动通知
     * 
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public PropertyChangeSupport getPropertyChangeSupport();

    /**
     * 
     * 指定配置文件的路径
     * 
     */
    void prepareConfigFilePath(String configFilePath);

    public void reload(ApplicationContext context);

    /**
     * 做语义校验
     */
    public void verify();

    /**
     * 配置模块的编号，越小，加载的越早
     * 
     * @return
     */
    public int index();

    public String getConfigFilePath();
}
