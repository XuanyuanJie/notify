/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.domain.config;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import org.cadre.notify.domain.config.listener.NotifyConfigListener;

/**
 * 配置模块全局管理器
 * @author 轩辕
 * @version $Id: ConfigControllerFacde.java, v 0.1 2011-10-31 下午03:33:53 轩辕 Exp $
 */
public interface ConfigController {

    void setAttribute(String moduleName, String attributeName, Serializable newValue, boolean sync)
                                                                                                   throws IOException;

    void setAttribute(String moduleName, String attributeName, Serializable newValue)
                                                                                     throws IOException;

    Object getAttributeValue(String moduleName, String attributeName);

    Set<String> getModuleNames();

    Set<String> getAttributeNames(String moduleName);

    void saveConfig(String moduleName) throws IOException;

    void saveConfig(String moduleName, String filePath) throws IOException;

    String backupConfig(String moduleName) throws IOException;

    void removeConfigModule(String name);

    public String getClusterName();

    public String getServerTag();

    public void reload();

    public void load(String path);

    public void addConfigModule(NotifyConfigListener<? extends Serializable> listener);
}
