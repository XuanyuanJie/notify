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
 * 统一消息处理
 * @author 轩辕
 * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 下午03:17:23 轩辕 Exp $
 */
public class NotifyHandler implements IoHandler {
    private static final Logger logger = LoggerFactory.getLogger(NotifyHandler.class);

    /**
     * 请求处理器的任务包装
     * 
     * @author 轩辕
     * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 下午03:49:24 轩辕 Exp $
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
     * 定时执行机制 .主要使用场景是 心跳的异步消息发送
     * 
     * @author 轩辕
     * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 下午05:10:47 轩辕 Exp $
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
     * 心跳命令的异步监听器
     * 
     * @author 轩辕
     * @version $Id: NotifyHandler.java, v 0.1 2011-11-1 下午03:49:10 轩辕 Exp $
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
            logger.info("心跳检测失败，关闭连接" + session.getRemoteAddress());
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
        //FIXME  心跳使用异步消息发送机制
        ScheduleRunner runner = new ScheduleRunner(listener);
        runner.run();
    }

    /** 
     * @see org.apache.mina.core.service.IoHandler#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
     */
    public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
        //FIXME 返回异常错误信息
        SocketAddress address = null;
        try {
            address = session.getRemoteAddress();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("error getRemoteSocketAddress", e);
            }
        }
        if (throwable.getCause() != null) {
            logger.error("通讯层异常" + address, throwable.getCause());
        } else {
            logger.error("通讯层异常" + address, throwable);
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
            throw new IllegalMessageException("未知的消息类型" + message);
        }
    }

    private <C extends HandleProcessorContext> void processRequest(IoSession session,
                                                                   NotifyRequest<NotifyMessage> message) {
        NotifyProcessor<C> processor = this.getProcessorByMessage(message);
        //        final RequestProcessorr<T> processor = this.getProcessorByMessage(message);
        if (processor == null) {
            logger.error("未找到" + message.getClass().getCanonicalName() + "对应的处理器");
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
                //FIXME 异步返回错误信息,线程池繁忙
            }
        }
    }

    private RequestCallBack getRequestCallBack(CommandType type) {
        //FIXME 指定CallBack
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
        //FIXME 返回错误信息,未找到指定请求的处理器
        //                    ResponseStatus.NO_PROCESSOR,
        //                    "未注册请求处理器，请求处理器类为" + message.getClass().getCanonicalName()));
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
