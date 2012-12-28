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
 * ����ģ��ȫ�ֹ�����
 * @author ��ԯ
 * @version $Id: ConfigControllerFacde.java, v 0.1 2011-10-31 ����03:33:53 ��ԯ Exp $
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
