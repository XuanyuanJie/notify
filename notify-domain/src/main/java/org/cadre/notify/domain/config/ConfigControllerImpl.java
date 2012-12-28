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
 * 配置模块全局管理器实现
 * @author 轩辕
 * @version $Id: ConfigControllerImpl.java, v 0.1 2011-10-31 下午03:35:17 轩辕 Exp $
 */
public class ConfigControllerImpl implements ConfigController {

    static final Logger                                                                   log                   = LoggerFactory
                                                                                                                    .getLogger(ConfigControllerImpl.class);

    /**
     * <配置模块名,配置监听器>
     */
    private final ConcurrentHashMap<String, NotifyConfigListener<? extends Serializable>> modules;

    /**
     * 本节点的配置目录
     */
    private URI                                                                           configDir;
    /**
     * 消息 服务器端 配置文件
     */
    public static final String                                                            NOTIFY_SERVERS_CONFIG = "notifyServers.properties";

    /**
     * 本节点的专有配置文件目录，如mysql_setting.xml
     */
    private String                                                                        localNodeSettingFile;
    /**
     * 集群名称
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
     * 初始化，在使用前需要调用本方法用以加载模块
     * 
     * @param path
     */
    public void load(String path) {
        log.warn("加载配置文件 from: " + path);
        if (path == null) {
            throw new NullPointerException("配置文件的路径为空");
        }

        File file;
        try {
            file = ResourceUtils.getFile(path);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("配置文件的路径不存在: " + path, e);
        }
        this.configDir = file.toURI();

        // 设置notify的实例名称，用来区分一个server上运行多个实例
        String instanceName = NotifyUtils.getNotifyInstanceName();
        log.warn("Notify Server Instance Name: " + instanceName);

        String configKey = NotifyUtils.getHostName() + "_" + instanceName;
        Properties props = this.getNotifyServersProperty();
        this.loadConfig(configKey, props);
    }

    private void loadConfig(String configKey, Properties props) {

        this.localNodeSettingFile = props.getProperty(configKey);
        if (this.localNodeSettingFile == null) {
            throw new NullPointerException("配置初始化失败，在" + NOTIFY_SERVERS_CONFIG
                                           + "中没有配置本NS的专有配置文件,key=" + configKey);
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
                throw new NullPointerException(this.localNodeSettingFile + "中的模块未注册：" + moduleName);
            }

            String configFilePath = FilenameUtils.normalize(this.configDir.getPath() + "/"
                                                            + props.get(moduleName));
            configListener.prepareConfigFilePath(configFilePath);
            listenerList.add(configListener);
        }

        /**
         * 按照index排序
         */
        Collections.sort(listenerList,
            new Comparator<NotifyConfigListener<? extends Serializable>>() {
                public int compare(NotifyConfigListener<? extends Serializable> o1,
                                   NotifyConfigListener<? extends Serializable> o2) {
                    return o1.index() - o2.index();
                }

            });
        for (NotifyConfigListener<? extends Serializable> configListener : listenerList) {
            log.warn("装载配置模块" + configListener.getModuleName() + " from  ConfigFilePath:"
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

        log.warn("加载配置文件:  " + filePath);

        Properties props = new Properties();
        FileInputStream inStream = null;
        try {
            File file = ResourceUtils.getFile(filePath);
            inStream = new FileInputStream(file);
            props.load(inStream);
        } catch (FileNotFoundException e) {
            log.error("未找到配置文件：" + filePath, e);
            throw new RuntimeException("未找到配置文件：" + filePath, e);
        } catch (IOException e) {
            log.error("加载配置文件失败：" + filePath, e);
            throw new RuntimeException("加载配置文件失败：" + filePath, e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    log.error("关闭配置文件失败：" + filePath, e);
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
                throw new NullPointerException(this.localNodeSettingFile + "中的模块未注册：" + moduleName);
            }

            String configFilePath = FilenameUtils.normalize(this.configDir.getPath() + "/"
                                                            + props.get(moduleName));
            configListener.prepareConfigFilePath(configFilePath);
            listenerList.add(configListener);
        }

        /**
         * 按照index排序
         */
        Collections.sort(listenerList,
            new Comparator<NotifyConfigListener<? extends Serializable>>() {
                public int compare(NotifyConfigListener<? extends Serializable> o1,
                                   NotifyConfigListener<? extends Serializable> o2) {
                    return o1.index() - o2.index();
                }

            });
        for (NotifyConfigListener<? extends Serializable> configListener : listenerList) {
            log.warn("重新装载配置模块" + configListener.getModuleName() + " from  ConfigFilePath:"
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
     * 仅用于测试 ---正常环境不允许修改
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
