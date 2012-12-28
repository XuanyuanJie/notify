/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.startup;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.util.ExceptionMonitor;
import org.cadre.notify.domain.NotifyServerTag;
import org.cadre.notify.domain.config.ConfigController;
import org.cadre.notify.domain.config.ConfigControllerImpl;
import org.cadre.notify.domain.config.NotifyConfigFactory;
import org.cadre.notify.domain.config.server.listener.NotifyServerConfigListener;
import org.cadre.notify.domain.utils.NotifyUtils;
import org.cadre.notify.server.assembly.NotifyServerController;
import org.cadre.notify.startup.listener.ShutdownListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author ��ԯ
 * @version $Id: NotifyServer.java, v 0.1 2011-11-1 ����10:04:04 ��ԯ Exp $
 */
public class NotifyServer {
    static final Logger            log                    = LoggerFactory
                                                              .getLogger(NotifyServer.class);

    private final static String    HOST_NAME              = NotifyUtils.getHostName();

    private static final String    ARGS_SERVER_TAG        = "serverTag";
    private static final String    ARGS_CONTEXT_PATH      = "contextPath";
    private static final String    ARGS_LOCAL_CONFIG_FILE = "localConfig";

    private Map<String, Object>    parms                  = new HashMap<String, Object>();

    private ConfigController       configController;

    private NotifyServerController notifyServerController;

    public NotifyServer() {
    }

    public void startup(String[] args) {
        // ���� Notify Server
        if (!this.start(args)) {
            return;
        }

        // ���� shutdown Listener
        new ShutdownListener(this).start();
    }

    private boolean start(String[] args) {

        this.checkHostName();
        ExceptionMonitor.setInstance(new ExceptionMonitor() {

            @Override
            public void exceptionCaught(Throwable cause) {
                if (cause == null) {
                    Throwable throwable = new Throwable();
                    StringBuffer sb = new StringBuffer("Null cause:");
                    for (StackTraceElement element : throwable.getStackTrace()) {
                        sb.append(element).append("\n");
                    }
                    log.error(sb.toString());
                }

                log.error("Exception occured", cause);
            }

        });

        // ������������
        if (!this.parseArgs(args)) {
            return false;
        }

        // ��ӡ��־,master or slave
        log.warn("��������"
                 + (NotifyConfigFactory.getNotifyServerConfig().isMaster() ? "Master" : "Slave")
                 + " Notify Server...");

        // װ�����е�Manager
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/serverController.xml");
        this.notifyServerController = (NotifyServerController) ctx.getBean("serverController");

        //���ò����趨
        configController = ConfigControllerImpl.getInstance();
        //Ĭ�ϲ��� 
        //�Զ������

        this.notifyServerController.start();
        // All done.
        log.warn("Notify Server �������");
        return true;
    }

    /**
     * 
     * ������������,�ݲ�֧��Ĭ�ϲ�������
     * @param args
     * @return
     */
    private boolean parseArgs(String[] args) {

        Map<String, String> argsMap = parseToMap(args);
        log.warn("��������������" + argsMap);
        // ����ServerTag
        String serverTag = argsMap.get(ARGS_SERVER_TAG);
        if (!StringUtils.isBlank(serverTag)) {
            NotifyServerTag.setServerTag(serverTag);
        }

        // ���ر���property�ļ�
        String localConfigFile = argsMap.get(ARGS_LOCAL_CONFIG_FILE);
        if (localConfigFile == null) {
            localConfigFile = "UnknowPath";
        }
        //        this.initJMX(localConfigFile);

        // ����ָ�������ļ�·��
        String configPath = argsMap.get(ARGS_CONTEXT_PATH);
        if (configPath == null) {
            log.warn(this.usage());
            return false;
        }
        this.loadConfigModules(configPath);
        return true;
    }

    private String usage() {

        return "Usage: Main contextPath=configuration dir [serverTag=serverTag] [localConfig=����property�ļ�] ";
    }

    private void loadConfigModules(String configPath) {
        /**
         * ���ｫ������е�ע������ģ��
         */
        ConfigControllerImpl.getInstance()
            .addConfigModule(NotifyServerConfigListener.getInstance());
        ConfigControllerImpl.getInstance().load(configPath);
    }

    /**
     * ת���������Ϊmap
     * 
     * @param args
     * @return
     */
    private Map<String, String> parseToMap(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg.indexOf("=") != -1) {
                    String[] arrs = arg.split("=");
                    if (arrs != null) {
                        if (arrs.length == 2) {
                            String key = arrs[0];
                            String value = arrs[1];
                            map.put(key, value);
                        } else {
                            //FIXME �ݲ�֧�ַǳ�������
                        }
                    }
                }
            }
        } else {
            //FIXME ������,�ݲ�����
        }
        return map;
    }

    private void checkHostName() {
        log.warn("����������Ϸ���");
        if (null == HOST_NAME || HOST_NAME.compareToIgnoreCase("localhost") == 0
            || HOST_NAME.length() == 0) {
            log.warn("û������������");
            throw new RuntimeException("û������������");
        }
    }
}
