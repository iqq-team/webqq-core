package iqq.im.event;

import iqq.im.QQException;

public interface QQActionFuture {

	/**
	 * 判断这个操作是否可以取消
	 * @return
	 */
	public abstract boolean isCancelable();
	
	/**
	 * 判断操作是否取消
	 * @return
	 */
	public abstract boolean isCanceled();

	/**
	 *  尝试取消操作
	 * @throws QQException 如果不能取消，抛出UNABLE_CANCEL异常
	 */
	public abstract void cancel() throws QQException;

	/**
	 * 等待事件的到来
	 * Note:可能有最终会产生多个事件如EVT_READ, EVT_WRITE等，此时应该反复调用waitEvent来获得需要的事件
	 * @return
	 * @throws QQException 等待被中断抛出WAIT_INTERUPPTED
	 */
	public abstract QQActionEvent waitEvent() throws QQException;

	/**
	 * 给定一个超时时间，等待事件到来
	 * @param timeoutMs		超时时间，毫秒为单位
	 * @return
	 * @throws QQException 超时抛出 WAIT_TIMEOUT， 等待被中断抛出WAIT_INTERUPPTED
	 */
	public abstract QQActionEvent waitEvent(long timeoutMs) throws QQException;

	/**
	 * 等待最终的事件，通常是EVT_CANCELED,EVT_ERROR,EVT_OK
	 * @return
	 * @throws QQException
	 */
	public abstract QQActionEvent waitFinalEvent() throws QQException;

	/**
	 * 给定一个超时时间，等待最终的事件
	 * @return
	 * @throws QQException
	 */
	public abstract QQActionEvent waitFinalEvent(long timeoutMs)
			throws QQException;

}