/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FilenameUtils;
import org.cadre.notify.domain.NotifyServerTag;
import org.cadre.notify.domain.config.listener.NotifyConfigListener;
import org.cadre.notify.domain.utils.NotifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ResourceUtils;

/**
 * ����ģ��ȫ�ֹ�����ʵ��
 * @author ��ԯ
 * @version $Id: ConfigControllerImpl.java, v 0.1 2011-10-31 ����03:35:17 ��ԯ Exp $
 */
public class ConfigControllerImpl implements ConfigController {

    static final Logger                                                                   log                   = LoggerFactory
                                                                                                                    .getLogger(ConfigControllerImpl.class);

    /**
     * <����ģ����,���ü�����>
     */
    private final ConcurrentHashMap<String, NotifyConfigListener<? extends Serializable>> modules;

    /**
     * ���ڵ������Ŀ¼
     */
    private URI                                                                           configDir;
    /**
     * ��Ϣ �������� �����ļ�
     */
    public static final String                                                            NOTIFY_SERVERS_CONFIG = "notifyServers.properties";

    /**
     * ���ڵ��ר�������ļ�Ŀ¼����mysql_setting.xml
     */
    private String                                                                        localNodeSettingFile;
    /**
     * ��Ⱥ����
     */
    private String                                                                        clusterName;

    private ConfigControllerImpl() {
        this.modules = new ConcurrentHashMap<String, NotifyConfigListener<? extends Serializable>>();
    }

    private static class Holder {
        private static final ConfigController instance = new ConfigControllerImpl();
    }

    public static ConfigController getInstance() {
        return Holder.instance;
    }

    /**
     * ��ʼ������ʹ��ǰ��Ҫ���ñ��������Լ���ģ��
     * 
     * @param path
     */
    public void load(String path) {
        log.warn("���������ļ� from: " + path);
        if (path == null) {
            throw new NullPointerException("�����ļ���·��Ϊ��");
        }

        File file;
        try {
            file = ResourceUtils.getFile(path);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("�����ļ���·��������: " + path, e);
        }
        this.configDir = file.toURI();

        // ����notify��ʵ�����ƣ���������һ��server�����ж��ʵ��
        String instanceName = NotifyUtils.getNotifyInstanceName();
        log.warn("Notify Server Instance Name: " + instanceName);

        String configKey = NotifyUtils.getHostName() + "_" + instanceName;
        Properties props = this.getNotifyServersProperty();
        this.loadConfig(configKey, props);
    }

    private void loadConfig(String configKey, Properties props) {

        this.localNodeSettingFile = props.getProperty(configKey);
        if (this.localNodeSettingFile == null) {
            throw new NullPointerException("���ó�ʼ��ʧ�ܣ���" + NOTIFY_SERVERS_CONFIG
                                           + "��û�����ñ�NS��ר�������ļ�,key=" + configKey);
        } else {
            int endIndex = this.localNodeSettingFile.indexOf("/");
            if (endIndex > 0) {
                this.setClusterName(this.localNodeSettingFile.substring(0, endIndex));
            } else {
                this.setClusterName("default");
            }
            this.loadModules();
        }
    }

    private void loadModules() {
        Properties props = this.loadProperties(this.configDir.getPath() + "/"
                                               + this.localNodeSettingFile);

        final List<NotifyConfigListener<? extends Serializable>> listenerList = new ArrayList<NotifyConfigListener<? extends Serializable>>();
        for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
            String moduleName = (String) e.nextElement();
            NotifyConfigListener<? extends Serializable> configListener = this.modules
                .get(moduleName);
            if (configListener == null) {
                throw new NullPointerException(this.localNodeSettingFile + "�е�ģ��δע�᣺" + moduleName);
            }

            String configFilePath = FilenameUtils.normalize(this.configDir.getPath() + "/"
                                                            + props.get(moduleName));
            configListener.prepareConfigFilePath(configFilePath);
            listenerList.add(configListener);
        }

        /**
         * ����index����
         */
        Collections.sort(listenerList,
            new Comparator<NotifyConfigListener<? extends Serializable>>() {
                public int compare(NotifyConfigListener<? extends Serializable> o1,
                                   NotifyConfigListener<? extends Serializable> o2) {
                    return o1.index() - o2.index();
                }

            });
        for (NotifyConfigListener<? extends Serializable> configListener : listenerList) {
            log.warn("װ������ģ��" + configListener.getModuleName() + " from  ConfigFilePath:"
                     + configListener.getConfigFilePath());
            String ctxPath = FilenameUtils.normalize(this.configDir + "/"
                                                     + props.get(configListener.getModuleName()));
            configListener.load(new ClassPathXmlApplicationContext(ctxPath));
        }
    }

    private Properties getNotifyServersProperty() {
        return this.loadProperties(this.configDir.getPath() + "/" + NOTIFY_SERVERS_CONFIG);
    }

    Properties loadProperties(String filePath) {

        log.warn("���������ļ�:  " + filePath);

        Properties props = new Properties();
        FileInputStream inStream = null;
        try {
            File file = ResourceUtils.getFile(filePath);
            inStream = new FileInputStream(file);
            props.load(inStream);
        } catch (FileNotFoundException e) {
            log.error("δ�ҵ������ļ���" + filePath, e);
            throw new RuntimeException("δ�ҵ������ļ���" + filePath, e);
        } catch (IOException e) {
            log.error("���������ļ�ʧ�ܣ�" + filePath, e);
            throw new RuntimeException("���������ļ�ʧ�ܣ�" + filePath, e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    log.error("�ر������ļ�ʧ�ܣ�" + filePath, e);
                }
            }
        }
        return props;
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#setAttribute(java.lang.String, java.lang.String, java.io.Serializable, boolean)
     */
    public void setAttribute(String moduleName, String attributeName, Serializable newValue,
                             boolean sync) throws IOException {
        NotifyConfigListener<? extends Serializable> config = this.modules.get(moduleName);
        if (config != null) {
            config.updateAttribute(attributeName, newValue);
            if (sync) {
                this.saveConfig(moduleName);
            }
        }
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#setAttribute(java.lang.String, java.lang.String, java.io.Serializable)
     */
    public void setAttribute(String moduleName, String attributeName, Serializable newValue)
                                                                                            throws IOException {
        this.setAttribute(moduleName, attributeName, newValue, false);
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#getAttributeValue(java.lang.String, java.lang.String)
     */
    public Object getAttributeValue(String moduleName, String attributeName) {
        NotifyConfigListener<? extends Serializable> config = this.modules.get(moduleName);
        if (config != null) {
            return config.getAttributeVlaue(attributeName);
        } else {
            return null;
        }
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#getModuleNames()
     */
    public Set<String> getModuleNames() {
        return new HashSet<String>(this.modules.keySet());
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#getAttributeNames(java.lang.String)
     */
    public Set<String> getAttributeNames(String moduleName) {
        NotifyConfigListener<? extends Serializable> config = this.modules.get(moduleName);
        if (config != null) {
            return config.getAttributeNames();
        } else {
            return null;
        }
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#saveConfig(java.lang.String)
     */
    public void saveConfig(String moduleName) throws IOException {
        NotifyConfigListener<? extends Serializable> config = this.modules.get(moduleName);
        if (config != null) {
            config.save();
        }
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#saveConfig(java.lang.String, java.lang.String)
     */
    public void saveConfig(String moduleName, String filePath) throws IOException {
        NotifyConfigListener<? extends Serializable> config = this.modules.get(moduleName);
        if (config != null) {
            config.save(filePath);
        }
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#backupConfig(java.lang.String)
     */
    public String backupConfig(String moduleName) throws IOException {
        NotifyConfigListener<? extends Serializable> config = this.modules.get(moduleName);
        if (config != null) {
            return config.backup();
        }
        return null;
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#removeConfigModule(java.lang.String)
     */
    public void removeConfigModule(String name) {
        this.modules.remove(name);
    }

    public void clear() {
        this.modules.clear();
    }

    public NotifyConfigListener<? extends Serializable> getConfigListener(String name) {
        return this.modules.get(name);
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#getClusterName()
     */
    public String getClusterName() {
        return this.clusterName;
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#getServerTag()
     */
    public String getServerTag() {
        return NotifyServerTag.getServerTag();
    }

    /** 
     * @see org.cadre.notify.domain.config.ConfigController#reload()
     */
    public void reload() {
        Properties props = this.loadProperties(this.configDir.getPath() + "/"
                                               + this.localNodeSettingFile);

        final List<NotifyConfigListener<? extends Serializable>> listenerList = new ArrayList<NotifyConfigListener<? extends Serializable>>();
        for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
            String moduleName = (String) e.nextElement();
            NotifyConfigListener<? extends Serializable> configListener = this.modules
                .get(moduleName);
            if (configListener == null) {
                throw new NullPointerException(this.localNodeSettingFile + "�е�ģ��δע�᣺" + moduleName);
            }

            String configFilePath = FilenameUtils.normalize(this.configDir.getPath() + "/"
                                                            + props.get(moduleName));
            configListener.prepareConfigFilePath(configFilePath);
            listenerList.add(configListener);
        }

        /**
         * ����index����
         */
        Collections.sort(listenerList,
            new Comparator<NotifyConfigListener<? extends Serializable>>() {
                public int compare(NotifyConfigListener<? extends Serializable> o1,
                                   NotifyConfigListener<? extends Serializable> o2) {
                    return o1.index() - o2.index();
                }

            });
        for (NotifyConfigListener<? extends Serializable> configListener : listenerList) {
            log.warn("����װ������ģ��" + configListener.getModuleName() + " from  ConfigFilePath:"
                     + configListener.getConfigFilePath());
            String ctxPath = FilenameUtils.normalize(this.configDir + "/"
                                                     + props.get(configListener.getModuleName()));
            configListener.reload(new ClassPathXmlApplicationContext(ctxPath));
        }
    }

    /**
     * Getter method for property <tt>configDir</tt>.
     * 
     * @return property value of configDir
     */
    public URI getConfigDir() {
        return configDir;
    }

    /**
     * Setter method for property <tt>configDir</tt>.
     * 
     * @param configDir value to be assigned to property configDir
     */
    public void setConfigDir(URI configDir) {
        this.configDir = configDir;
    }

    /**
     * Getter method for property <tt>localNodeSettingFile</tt>.
     * 
     * @return property value of localNodeSettingFile
     */
    public String getLocalNodeSettingFile() {
        return localNodeSettingFile;
    }

    /**
     * �����ڲ��� ---���������������޸�
     * Setter method for property <tt>localNodeSettingFile</tt>.
     * 
     * @param localNodeSettingFile value to be assigned to property localNodeSettingFile
     */
    public void setLocalNodeSettingFile(String localNodeSettingFile) {
        this.localNodeSettingFile = localNodeSettingFile;
    }

    /**
     * Getter method for property <tt>modules</tt>.
     * 
     * @return property value of modules
     */
    public ConcurrentHashMap<String, NotifyConfigListener<? extends Serializable>> getModules() {
        return modules;
    }

    /**
     * Setter method for property <tt>clusterName</tt>.
     * 
     * @param clusterName value to be assigned to property clusterName
     */
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void addConfigModule(NotifyConfigListener<? extends Serializable> listener) {
        this.modules.put(listener.getModuleName(), listener);
    }

}
