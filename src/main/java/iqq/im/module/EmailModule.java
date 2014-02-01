package iqq.im.module;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.action.CheckEmailSig;
import iqq.im.action.DeleteEmailAction;
import iqq.im.action.GetPT4Auth;
import iqq.im.action.GetWPKeyAction;
import iqq.im.action.LoginEmailAction;
import iqq.im.action.MarkEmailAction;
import iqq.im.action.PollEmailAction;
import iqq.im.bean.QQEmail;
import iqq.im.core.QQConstants;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionFuture;
import iqq.im.event.QQNotifyEvent;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * 邮箱模块
 * 
 * @author 承∮诺<6208317@qq.com>
 * @Created 2014年1月23日
 */
public class EmailModule extends AbstractModule {
	private static final Logger LOG = Logger.getLogger(EmailModule.class);
	private int errorCount = 0;

	/**
	 * 轮询邮件信息 先通过pt4获取check url 再通过check检查登录验证 再通过login获取key2 再通过wpkey获取key3
	 * 然后得到wpkey，进行邮件轮询
	 */
	public void doPoll() {
		// 步骤四
		final QQActionListener wpkeyListener = new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					errorCount = 0;
					// 跳到轮询
					loopPoll(event.getTarget().toString(), 0);
				} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
					if (errorCount < QQConstants.MAX_POLL_ERR_CNT) {
						getPT4Auth(this);
						errorCount++;
					}
				}
			}
		};
		// 步骤三
		final QQActionListener loginListener = new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					errorCount = 0;
					// 跳到步骤四
					String key = event.getTarget().toString();
					getWPKey(key, wpkeyListener);
					getContext().getSession().setEmailAuthKey(key);
				} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
					if (errorCount < QQConstants.MAX_POLL_ERR_CNT) {
						getPT4Auth(this);
						errorCount++;
					}
				}
			}
		};
		// 步骤二
		final QQActionListener checkListener = new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					errorCount = 0;
					// 跳到步骤三
					login(loginListener);
				} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
					if (errorCount < QQConstants.MAX_POLL_ERR_CNT) {
						getPT4Auth(this);
						errorCount++;
					}
				}
			}
		};
		// 步骤一
		QQActionListener pt4Listener = new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					errorCount = 0;
					// 跳到步骤二
					check(event.getTarget().toString(), checkListener);
				} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
					QQException ex = (QQException) event.getTarget();
					if (ex.getError() == QQErrorCode.INVALID_LOGIN_AUTH) {
						// 登录失败，QQ消息的POLL同时也失效，这时那边会重新登录
						// 如果已经在登录中，或者已经登录了，就不用再次执行
						LOG.warn("getPT4Auth error!!! wait relogin...", ex);
						ProcModule procModule = getContext().getModule(
								Type.PROC);
						procModule.relogin();// 重新登录成功会重新唤醒beginPoll
					} else if (errorCount < QQConstants.MAX_POLL_ERR_CNT) {
						getPT4Auth(this);
						errorCount++;
					}
				}
			}
		};

		getPT4Auth(pt4Listener);
	}

	/**
	 * 反复轮询
	 * 
	 * @param sid
	 *            凭证ID，就算没有Cookie都可以轮询
	 * @param t
	 *            邮件，好像是时间，用来消除轮询返回的列表中邮件，不然一直会返回那个邮件回来
	 */
	public void loopPoll(final String sid, long t) {
		QQActionListener listener = new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					errorCount = 0;
					if (event.getTarget() == null) {
						// 没有新邮件，t直接传0
						loopPoll(sid, 0);
					} else {
						// 有新邮件
						QQNotifyEvent evt = (QQNotifyEvent) event.getTarget();
						// 通知事件
						getContext().fireNotify(evt);
						// 消除所有，传上最后t的标记上去
						List<QQEmail> mailList = (List<QQEmail>) evt
								.getTarget();
						loopPoll(sid, mailList.get(mailList.size() - 1)
								.getFlag());
						
						// 把邮件标记为已读，需要邮件列表ID
						// mark(false, mailList, null);
					}
				} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
					QQException ex = (QQException) event.getTarget();
					if (ex.getError() == QQException.QQErrorCode.INVALID_LOGIN_AUTH) {
						// 凭证失效，重新认证
						doPoll();
					} else if (errorCount < QQConstants.MAX_POLL_ERR_CNT) {
						loopPoll(sid, 0);
						errorCount++;
						LOG.warn("Poll email retry!!! count: " + errorCount, ex);
					} else {
						LOG.warn("Poll email error!!!", ex);
					}
				}
			}
		};
		poll(sid, t, listener);
	}

	/**
	 * 轮询，需要到sid
	 * 
	 * @param sid
	 * @param listener
	 * @return
	 */
	public QQActionFuture poll(String sid, long t, QQActionListener listener) {
		return pushHttpAction(new PollEmailAction(sid, t, getContext(),
				listener));
	}

	/**
	 * 通过登录得到的sid，获取到wpkey
	 * 
	 * @param sid
	 * @param listener
	 * @return
	 */
	public QQActionFuture getWPKey(String sid, QQActionListener listener) {
		return pushHttpAction(new GetWPKeyAction(sid, getContext(), listener));
	}

	/**
	 * 登录邮箱
	 * 
	 * @param listener
	 * @return
	 */
	public QQActionFuture login(QQActionListener listener) {
		return pushHttpAction(new LoginEmailAction(getContext(), listener));
	}

	/**
	 * 通过pt4获取到的URL进行封装 检测邮箱是否合法登录了
	 * 
	 * @param url
	 * @param listener
	 * @return
	 */
	public QQActionFuture check(String url, QQActionListener listener) {
		return pushHttpAction(new CheckEmailSig(url, getContext(), listener));
	}

	/**
	 * pt4登录验证 获取到一个链接
	 * 
	 * @param listener
	 * @return
	 */
	public QQActionFuture getPT4Auth(QQActionListener listener) {
		return pushHttpAction(new GetPT4Auth(getContext(), listener));
	}

	/**
	 * 把邮件标记为已经读，或者未读
	 * 
	 * @param unread
	 * @param mails
	 * @param listener
	 * @return
	 */
	public QQActionFuture mark(boolean unread, List<QQEmail> mails,
			QQActionListener listener) {
		return pushHttpAction(new MarkEmailAction(unread, mails, getContext(), listener));
	}
	
	/**
	 * 删除邮件
	 * 
	 * @param mails
	 * @param listener
	 * @return
	 */
	public QQActionFuture delete(List<QQEmail> mails,
			QQActionListener listener) {
		return pushHttpAction(new DeleteEmailAction(mails, getContext(), listener));
	}
}
