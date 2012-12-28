/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.handler;

import java.net.SocketAddress;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.cadre.notify.domain.NotifyProcessor;
import org.cadre.notify.domain.ProcessorContext;
import org.cadre.notify.domain.handle.HandleProcessorContext;
import org.cadre.notify.domain.notify.NotifyMessage;
import org.cadre.notify.domain.request.NotifyRequest;
import org.cadre.notify.domain.request.callback.RequestCallBack;
import org.cadre.notify.domain.response.NotifyResponse;
import org.cadre.notify.domain.response.ResponseCommand;
import org.cadre.notify.domain.response.enums.CommandType;
import org.cadre.notify.domain.response.enums.ResponseStatus;
import org.cadre.notify.server.build.BuildProcessorContextHelper;
import org.cadre.notify.server.exception.IllegalMessageException;
import org.cadre.notify.server.remoting.service.SingleRequestCallBackListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ͳһ��Ϣ����
 * @author ��ԯ
 * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 ����03:17:23 ��ԯ Exp $
 */
public class NotifyHandler implements IoHandler {
    private static final Logger logger = LoggerFactory.getLogger(NotifyHandler.class);

    /**
     * ���������������װ
     * 
     * @author ��ԯ
     * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 ����03:49:24 ��ԯ Exp $
     */
    private static final class ProcessorRunner<T extends NotifyRequest<NotifyMessage>, C extends ProcessorContext>
                                                                                                                   implements
                                                                                                                   Runnable {
        private final IoSession          session;
        private final NotifyProcessor<C> notifyProcessor;
        private final T                  request;

        private ProcessorRunner(T request, IoSession session, NotifyProcessor<C> processor) {
            this.session = session;
            this.request = request;
            this.notifyProcessor = processor;
        }

        public void run() {
            C context = BuildProcessorContextHelper.buildProcessContextForNotify(request);
            notifyProcessor.beforeProcess(context, session);
            notifyProcessor.process(context, session);
            notifyProcessor.afterProcess(context, session);
        }

    }

    /**
     * ��ʱִ�л��� .��Ҫʹ�ó����� �������첽��Ϣ����
     * 
     * @author ��ԯ
     * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 ����05:10:47 ��ԯ Exp $
     */
    private static final class ScheduleRunner implements Runnable {

        //        HeartBeatListener listener;

        public ScheduleRunner(HeartBeatListener heartBeatListener) {
            //            this.listener = heartBeatListener;
        }

        public void run() {
            //            listener.onResponse(response, session);
        }

    }

    /**
     * 
     * ����������첽������
     * 
     * @author ��ԯ
     * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 ����03:49:10 ��ԯ Exp $
     */
    private final static class HeartBeatListener implements SingleRequestCallBackListener {
        private final IoSession session;
        static final String     HEARBEAT_FAIL_COUNT = "connection_heartbeat_fail_count";

        public ThreadPoolExecutor getExecutor() {
            return null;
        }

        private HeartBeatListener(IoSession session) {
            this.session = session;
        }

        public void onException(Exception e) {
            this.innerCloseConnection(this.session);
        }

        public void onResponse(NotifyResponse<NotifyMessage> response, IoSession session) {
            if (response == null || response.getResponseStatus() != ResponseStatus.NO_ERROR) {
                Integer count = (Integer) this.session.setAttributeIfAbsent(HEARBEAT_FAIL_COUNT, 1);
                if (count != null) {
                    count++;
                    if (count < 3) {
                        session.setAttribute(HEARBEAT_FAIL_COUNT, count);
                    } else {
                        this.innerCloseConnection(session);
                    }
                }
            } else {
                this.session.removeAttribute(HEARBEAT_FAIL_COUNT);
            }
        }

        private void innerCloseConnection(IoSession session) {
            logger.info("�������ʧ�ܣ��ر�����" + session.getRemoteAddress());
            session.close(true);
        }

    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#sessionCreated(org.apache.mina.core.session.IoSession)
     */
    public void sessionCreated(IoSession session) throws Exception {
    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#sessionOpened(org.apache.mina.core.session.IoSession)
     */
    public void sessionOpened(IoSession session) throws Exception {
    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#sessionClosed(org.apache.mina.core.session.IoSession)
     */
    public void sessionClosed(IoSession session) throws Exception {
    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#sessionIdle(org.apache.mina.core.session.IoSession, org.apache.mina.core.session.IdleStatus)
     */
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        //        final Connection conn = this.remotingContext.getConnectionBySession((NioSession) session);
        //            session.(conn.getRemotingContext().getCommandFactory().createHeartBeatCommand(),
        //                new HeartBeatListener(conn), 5000, TimeUnit.MILLISECONDS);
        HeartBeatListener listener = new HeartBeatListener(session);
        //FIXME  ����ʹ���첽��Ϣ���ͻ���
        ScheduleRunner runner = new ScheduleRunner(listener);
        runner.run();
    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
     */
    public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
        //FIXME �����쳣������Ϣ
        SocketAddress address = null;
        try {
            address = session.getRemoteAddress();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("error getRemoteSocketAddress", e);
            }
        }
        if (throwable.getCause() != null) {
            logger.error("ͨѶ���쳣" + address, throwable.getCause());
        } else {
            logger.error("ͨѶ���쳣" + address, throwable);
        }
    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public void messageReceived(IoSession session, Object message) throws Exception {
        if (message instanceof NotifyRequest) {
            this.processRequest(session, (NotifyRequest<NotifyMessage>) message);
        } else if (message instanceof ResponseCommand) {
            this.processResponse((ResponseCommand) message);
        } else {
            throw new IllegalMessageException("δ֪����Ϣ����" + message);
        }
    }

    private <C extends HandleProcessorContext> void processRequest(IoSession session,
                                                                   NotifyRequest<NotifyMessage> message) {
        NotifyProcessor<C> processor = this.getProcessorByMessage(message);
        //        final RequestProcessorr<T> processor = this.getProcessorByMessage(message);
        if (processor == null) {
            logger.error("δ�ҵ�" + message.getClass().getCanonicalName() + "��Ӧ�Ĵ�����");
            this.responseNoProcessor(session, message);
            return;
        } else {
            this.executeProcessor(session, message, processor);
        }
    }

    private <C extends HandleProcessorContext> NotifyProcessor<C> getProcessorByMessage(NotifyRequest<NotifyMessage> message) {
        return null;

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <C extends ProcessorContext> void executeProcessor(IoSession session,
                                                               final NotifyRequest<NotifyMessage> message,
                                                               final NotifyProcessor<C> processor) {
        if (processor.getExecutor() == null) {
            C context = BuildProcessorContextHelper.buildProcessContextForNotify(message);
            processor.beforeProcess(context, session);
            processor.process(context, session);
            processor.afterProcess(context, session);
        } else {
            try {
                processor.getExecutor().execute(new ProcessorRunner(message, session, processor));
            } catch (RejectedExecutionException e) {
                //FIXME �첽���ش�����Ϣ,�̳߳ط�æ
            }
        }
    }

    private RequestCallBack getRequestCallBack(CommandType type) {
        //FIXME ָ��CallBack
        return null;
    }

    private void processResponse(ResponseCommand responseCommand) {
        RequestCallBack requestCallBack = getRequestCallBack(responseCommand.getCommandType());
        if (requestCallBack != null) {
            requestCallBack.onResponse(responseCommand);
        }
    }

    private void responseNoProcessor(IoSession session, Object message) {
        //        try {
        //FIXME ���ش�����Ϣ,δ�ҵ�ָ������Ĵ�����
        //                    ResponseStatus.NO_PROCESSOR,
        //                    "δע����������������������Ϊ" + message.getClass().getCanonicalName()));
        //        } catch (NotifyRemotingException e) {
        //            this.onExceptionCaught(session, e);
        //        }
    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#messageSent(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    public void messageSent(IoSession session, Object message) throws Exception {
    }

}
