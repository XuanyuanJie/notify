/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config.listener.adapter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.cadre.notify.domain.config.listener.NotifyConfigListener;
import org.cadre.notify.domain.utils.GenericUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @author 轩辕
 * @version $Id: NotifyConfigListenerAdapter.java, v 0.1 2011-10-31 下午01:50:16 轩辕 Exp $
 */
public abstract class NotifyConfigListenerAdapter<E extends Serializable> implements
                                                                          NotifyConfigListener<E> {
    private static final Logger           logger                = LoggerFactory
                                                                    .getLogger(NotifyConfigListenerAdapter.class);

    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /** 模块名称*/
    private String                        moduleName;
    /** 模块编号*/
    private int                           index                 = 100;                                            //默认100,越小越先加载

    /** 配置对象*/
    private E                             configObject;
    /** 配置文件存贮地址*/
    private String                        configFilePath;

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected NotifyConfigListenerAdapter() {
        try {
            Type type = GenericUtils.getSuperclassTypeParameter(getClass());
            Class<E> clazz = (Class<E>) GenericUtils.getRawType(type);
            this.configObject = clazz.newInstance();
        } catch (Exception e) {
            logger.warn("初始化配置对象失败");
        }
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#load(org.springframework.context.ApplicationContext)
     */
    @SuppressWarnings("unchecked")
    public synchronized void load(ApplicationContext context) {
        this.configObject = (E) context.getBean(this.getModuleName());
        this.verify();
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#reload(org.springframework.context.ApplicationContext)
     */
    @SuppressWarnings("unchecked")
    public void reload(ApplicationContext context) {
        E newConfigObject = (E) context.getBean(this.getModuleName());
        this.verify();
        Set<String> attributeSet = this.getAttributeNames();
        if (newConfigObject != null && !newConfigObject.equals(this.getConfigObject())) {
            logger.warn("模块 " + this.getModuleName() + " 属性有改变，更新模块属性……");
            for (String attributeName : attributeSet) {
                this.updateAttribute(attributeName,
                    this.getAttributeVlaue(newConfigObject, attributeName));
            }
        }
    }

    @SuppressWarnings("finally")
    private BeanInfo getBeanInfo(Object configBean) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(configBean.getClass());
        } catch (IntrospectionException e1) {
            logger.error("getBeanInfo Error", e1);
        } finally {
            return beanInfo;
        }
    }

    private Object getAttributeVlaue(Object configObject, String attributeName) {
        PropertyDescriptor[] propertyDescriptors = getBeanInfo(configObject)
            .getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getName().equals(attributeName)) {
                try {
                    if (propertyDescriptor.getReadMethod() != null) {
                        return propertyDescriptor.getReadMethod().invoke(configObject);
                    }
                } catch (InvocationTargetException e) {
                    this.handleException("get", attributeName, e);
                } catch (IllegalAccessException e) {
                    this.handleException("get", attributeName, e);
                }

            }
        }
        return null;
    }

    private void handleException(String opName, String name, Exception e) {
        StringBuilder buf = new StringBuilder();
        buf.append(opName).append("属性失败，moduleName:").append(this.getModuleName());
        buf.append(", propertyName:").append(name);
        logger.error(buf.toString(), e);
        throw new RuntimeException(buf.toString(), e);
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#getModuleName()
     */
    public String getModuleName() {
        return this.moduleName;
    }

    /** 
     * 批量更新配置类的属性,当只有一个配置类的时候可用，其他情况下请覆写此方法
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#updateAttributes(java.util.Map)
     */
    public void updateAttributes(Map<String, ? extends Object> attributes) {
        for (Map.Entry<String, ? extends Object> entry : attributes.entrySet()) {
            this.updateAttribute(entry.getKey(), entry.getValue());
        }
    }

    /** 
     * 更新配置类的某个属性，当只有一个配置类的时候可用，其他情况下请覆写此方法
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#updateAttribute(java.lang.String, java.lang.Object)
     */
    public void updateAttribute(String name, Object newValue) {
        Object oldValue = this.getAttributeVlaue(name);
        PropertyDescriptor[] propertyDescriptors = getBeanInfo(this.getConfigObject())
            .getPropertyDescriptors();
        boolean contains = false;
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getName().equals(name)) {
                contains = true;
                try {
                    if (propertyDescriptor.getWriteMethod() != null) {
                        propertyDescriptor.getWriteMethod()
                            .invoke(this.getConfigObject(), newValue);
                        this.firePropertyChange(name, oldValue, newValue);
                    } else {
                        throw new RuntimeException(name + " does not have setter method");
                    }

                } catch (InvocationTargetException e) {
                    this.handleException("set", name, e);
                } catch (IllegalAccessException e) {
                    this.handleException("set", name, e);
                }
                break;
            }
        }
        if (!contains) {
            throw new RuntimeException("Unknow attribute " + name);
        }

    }

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(this.getModuleName(), propertyName,
            oldValue, newValue);
        this.propertyChangeSupport.firePropertyChange(event);
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#getAttributeVlaue(java.lang.String)
     */
    public Object getAttributeVlaue(String attributeName) {
        return this.getAttributeVlaue(this.getConfigObject(), attributeName);
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#backup()
     */
    public String backup() throws IOException {
        return this.saveConfigFile(this.getBackupPath());
    }

    protected String getBackupPath() {
        DateFormat dateFormat = new SimpleDateFormat("_yyyy-MM-dd-HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String oldPath = this.configFilePath;
        String newPath = oldPath.substring(0, oldPath.length() - 4) + timestamp + ".xml";
        return newPath;
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#save()
     */
    public void save() throws IOException {
        saveConfigFile(this.configFilePath);
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#save(java.lang.String)
     */
    public void save(String path) throws IOException {
        saveConfigFile(path);
    }

    /**
     * 保存配置至文件
     * 
     * @param path
     */
    private String saveConfigFile(String path) throws IOException {
        this.verify();
        String newConfigString = "";
        File actualFile = new File(configFilePath);
        File tempFile = File.createTempFile(this.getModuleName(), null, actualFile.getParentFile());

        // 配置内容写入临时文件
        FileUtils.writeStringToFile(tempFile, newConfigString);

        // rename to actualFile
        actualFile.delete();
        if (tempFile.renameTo(actualFile)) {
            tempFile.delete();
        } else {
            throw new IOException("写入配置文件失败：临时文件重命名失败");
        }

        return actualFile.getAbsolutePath();
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#getAttributeNames()
     */
    public Set<String> getAttributeNames() {
        PropertyDescriptor[] propertyDescriptors = getBeanInfo(this.getConfigObject())
            .getPropertyDescriptors();
        Set<String> attributeNames = new HashSet<String>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (!propertyDescriptor.getName().equals("class")) {
                if (propertyDescriptor.getReadMethod() != null
                    && propertyDescriptor.getWriteMethod() != null) {
                    attributeNames.add(propertyDescriptor.getName());
                }
            }
        }
        return attributeNames;
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#getPropertyChangeSupport()
     */
    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.propertyChangeSupport;
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#prepareConfigFilePath(java.lang.String)
     */
    public void prepareConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#index()
     */
    public int index() {
        return this.index;
    }

    /** 
     * @see org.cadre.notify.domain.config.listener.NotifyConfigListener#getConfigFilePath()
     */
    public String getConfigFilePath() {
        return this.configFilePath;
    }

    /**
     * Getter method for property <tt>configObject</tt>.
     * 
     * @return property value of configObject
     */
    public E getConfigObject() {
        return configObject;
    }

    /**
     * Setter method for property <tt>configObject</tt>.
     * 
     * @param configObject value to be assigned to property configObject
     */
    public void setConfigObject(E configObject) {
        this.configObject = configObject;
    }

    public void verify() {
        // 默认不做任何校验
    }
}
