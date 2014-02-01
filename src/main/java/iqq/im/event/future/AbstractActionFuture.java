package iqq.im.event.future;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionEvent.Type;
import iqq.im.event.QQActionFuture;

public abstract class AbstractActionFuture implements QQActionFuture, QQActionListener {
	private QQActionListener proxyListener;
	private BlockingQueue<QQActionEvent> eventQueue;
	private volatile boolean isCanceled;
	private volatile boolean hasEvent;

	public AbstractActionFuture(QQActionListener proxyListener) {
		this.hasEvent = true;
		this.proxyListener = proxyListener;
		this.eventQueue = new LinkedBlockingQueue<QQActionEvent>();
	}

	@Override
	public QQActionEvent waitEvent() throws QQException {
		if( !hasEvent ) {
			return null;
		}
		try {
			QQActionEvent event = eventQueue.take();
			hasEvent = !isFinalEvent(event);
			return event;
		} catch (InterruptedException e) {
			throw new QQException(QQErrorCode.WAIT_INTERUPPTED, e);
		}
	}

	@Override
	public QQActionEvent waitEvent(long timeoutMs) throws QQException {
		QQActionEvent event = null;
		if( !hasEvent ) {
			return null;
		}
		try {
			event = eventQueue.poll(timeoutMs, TimeUnit.MICROSECONDS);
		} catch (InterruptedException e) {
			throw new QQException(QQErrorCode.WAIT_INTERUPPTED, e);
		}
		if(event == null){
			throw new QQException(QQErrorCode.WAIT_TIMEOUT);
		}else{
			hasEvent = !isFinalEvent(event);
			return event;
		}
	}

	@Override
	public QQActionEvent waitFinalEvent() throws QQException {
		QQActionEvent event = null;
		while( (event = waitEvent()) != null){
			if( isFinalEvent(event) ){
				return event;
			}
		}
		throw new QQException(QQErrorCode.UNKNOWN_ERROR);
	}

	@Override
	public QQActionEvent waitFinalEvent(long timeoutMs) throws QQException {
		QQActionEvent event = null;
		long start = System.currentTimeMillis();
		while( (event = waitEvent(timeoutMs)) != null){
			long end = System.currentTimeMillis();
			if( isFinalEvent(event) ){
				return event;
			}else{
				timeoutMs -= end - start;
				start = System.currentTimeMillis();
			}
		}
		throw new QQException(QQErrorCode.UNKNOWN_ERROR);
	}
	
	private boolean isFinalEvent(QQActionEvent event){
		QQActionEvent.Type type = event.getType();
		return type==Type.EVT_CANCELED 
				|| type==Type.EVT_ERROR 
				|| type==Type.EVT_OK;
	}

	@Override
	public void onActionEvent(QQActionEvent event) {
		if (proxyListener != null){
			proxyListener.onActionEvent(event);
		}
		eventQueue.add(event);
	}

	@Override
	public boolean isCanceled() {
		return isCanceled;
	}
	
	public void setCanceled(boolean isCanceled){
		this.isCanceled = isCanceled;
	}
	
	public void notifyActionEvent(QQActionEvent.Type type, Object target){
		onActionEvent(new QQActionEvent(type, target, this));
	}

	/**
	 * @return the proxyListener
	 */
	public QQActionListener getProxyListener() {
		return proxyListener;
	}
}