/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.remoting.service.impl;

import org.apache.mina.core.service.IoAcceptor;
import org.cadre.notify.domain.config.NotifyConfig;
import org.cadre.notify.server.remoting.service.RemotingController;
import org.cadre.notify.server.remoting.service.exception.NotifyRemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RemotingController���������ʵ��,ʵ����������Ĺ���
 * @author ��ԯ
 * @version $Id: RemotingControllerAdapter.java, v 0.1 2011-11-1 ����11:30:17 ��ԯ Exp $
 */
public abstract class RemotingControllerAdapter implements RemotingController {

    private static final Logger logger = LoggerFactory.getLogger(RemotingControllerAdapter.class);

    protected NotifyConfig      notifyConfig;

    protected IoAcceptor        acceptor;

    protected volatile boolean  started;

    /** 
     * @see org.cadre.notify.server.remoting.service.RemotingController#start()
     */
    public final synchronized void start() throws NotifyRemotingException {
        if (this.started) {
            return;
        }
        this.started = true;
        StringBuffer info = new StringBuffer("��������RemotingController...\n");
        info.append("����Ϊ��\n").append(this.notifyConfig.toString());
        logger.info(info.toString());
        acceptor = this.initController(notifyConfig);

        this.doStart();
    }

    /** 
     * @see org.cadre.notify.server.remoting.service.RemotingController#stop()
     */
    public final synchronized void stop() throws NotifyRemotingException {
        if (!this.started) {
            return;
        }
        this.started = false;
        this.doStop();
    }

    /** 
     * @see org.cadre.notify.server.remoting.service.RemotingController#isStarted()
     */
    public boolean isStarted() {
        return started;
    }

    protected abstract void doStart() throws NotifyRemotingException;

    protected abstract void doStop() throws NotifyRemotingException;

    protected abstract IoAcceptor initController(NotifyConfig config);

}
