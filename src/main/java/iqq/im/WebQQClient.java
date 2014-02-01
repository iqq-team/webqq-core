/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Project  : WebQQCore
 * Package  : iqq.im
 * File     : WebQQClient.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-8-1
 * License  : Apache License 2.0 
 */
package iqq.im;

import iqq.im.actor.QQActor;
import iqq.im.actor.QQActorDispatcher;
import iqq.im.bean.QQAccount;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQDiscuz;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQGroupSearchList;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQStatus;
import iqq.im.bean.QQStranger;
import iqq.im.bean.QQUser;
import iqq.im.bean.content.CFaceItem;
import iqq.im.bean.content.OffPicItem;
import iqq.im.core.QQContext;
import iqq.im.core.QQModule;
import iqq.im.core.QQService;
import iqq.im.core.QQSession;
import iqq.im.core.QQSession.State;
import iqq.im.core.QQStore;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionFuture;
import iqq.im.event.QQNotifyEvent;
import iqq.im.event.QQNotifyEvent.Type;
import iqq.im.event.QQNotifyEventArgs;
import iqq.im.event.QQNotifyEventArgs.ImageVerify.VerifyType;
import iqq.im.event.future.ProcActionFuture;
import iqq.im.module.BuddyModule;
import iqq.im.module.CategoryModule;
import iqq.im.module.ChatModule;
import iqq.im.module.DiscuzModule;
import iqq.im.module.EmailModule;
import iqq.im.module.GroupModule;
import iqq.im.module.LoginModule;
import iqq.im.module.ProcModule;
import iqq.im.module.UserModule;
import iqq.im.service.ApacheHttpService;
import iqq.im.service.HttpService;
import iqq.im.service.HttpService.ProxyType;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * WebQQ客户的实现
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class WebQQClient implements QQClient, QQContext {
	private static final Logger LOG = Logger.getLogger(WebQQClient.class);
	private Map<QQService.Type, QQService> services;
	private Map<QQModule.Type, QQModule> modules;
	private QQActorDispatcher actorDispatcher;
	private QQAccount account;
	private QQSession session;
	private QQStore store;
	private QQNotifyListener notifyListener;

	public WebQQClient(String username, String password,
			QQNotifyListener notifyListener, QQActorDispatcher actorDispatcher) {
		this.modules = new HashMap<QQModule.Type, QQModule>();
		this.services = new HashMap<QQService.Type, QQService>();

		this.modules.put(QQModule.Type.LOGIN, new LoginModule());
		this.modules.put(QQModule.Type.PROC, new ProcModule());
		this.modules.put(QQModule.Type.USER, new UserModule());
		this.modules.put(QQModule.Type.BUDDY, new BuddyModule());
		this.modules.put(QQModule.Type.CATEGORY, new CategoryModule());
		this.modules.put(QQModule.Type.GROUP, new GroupModule());
		this.modules.put(QQModule.Type.CHAT, new ChatModule());
		this.modules.put(QQModule.Type.DISCUZ, new DiscuzModule());
		this.modules.put(QQModule.Type.EMAIL, new EmailModule());

		this.services.put(QQService.Type.HTTP, new ApacheHttpService());

		this.account = new QQAccount();
		this.account.setUsername(username);
		this.account.setPassword(password);
		this.session = new QQSession();
		this.store = new QQStore();
		this.notifyListener = notifyListener;
		this.actorDispatcher = actorDispatcher;
		
		this.init();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends QQModule> T getModule(QQModule.Type type) {
		return (T) modules.get(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends QQService> T getSerivce(QQService.Type type) {
		return (T) services.get(type);
	}
	
	@Override
	public void setHttpUserAgent(String userAgent) {
		HttpService http = getSerivce(QQService.Type.HTTP);
		http.setUserAgent(userAgent);
		
	}

	@Override
	public void setHttpProxy(ProxyType proxyType, String proxyHost,
			int proxyPort, String proxyAuthUser, String proxyAuthPassword) {
		HttpService http = getSerivce(QQService.Type.HTTP);
		http.setHttpProxy(proxyType, proxyHost, proxyPort, 
							proxyAuthUser, proxyAuthPassword);
	}

	@Override
	public QQAccount getAccount() {
		return account;
	}

	@Override
	public QQStore getStore() {
		return store;
	}

	@Override
	public void pushActor(QQActor actor) {
		actorDispatcher.pushActor(actor);
	}

	private void init() {
		try {
			for (QQService.Type type : services.keySet()) {
				QQService service = services.get(type);
				service.init(this);
			}

			for (QQModule.Type type : modules.keySet()) {
				QQModule module = modules.get(type);
				module.init(this);
			}

			actorDispatcher.init(this);
			store.init(this);
		} catch (QQException e) {
			LOG.warn("init error:", e);
		}
	}

	public void destroy() {
		try {
			for (QQModule.Type type : modules.keySet()) {
				QQModule module = modules.get(type);
				module.destroy();
			}

			for (QQService.Type type : services.keySet()) {
				QQService service = services.get(type);
				service.destroy();
			}

			actorDispatcher.destroy();
			store.destroy();
		} catch (QQException e) {
			LOG.warn("destroy error:", e);
		}
	}

	@Override
	public QQActionFuture login(QQStatus status, final QQActionListener listener) {
		//检查客户端状态，是否允许登陆
		if (session.getState() == State.ONLINE) {
			throw new IllegalArgumentException("client is aready online !!!");
		}

		getAccount().setStatus(status);
		getSession().setState(QQSession.State.LOGINING);
		ProcModule procModule = (ProcModule) getModule(QQModule.Type.PROC);
		return procModule.login(listener);
	}
	
	@Override
	public QQActionFuture relogin(QQStatus status, final QQActionListener listener) {
		if (session.getState() == State.ONLINE) {
			throw new IllegalArgumentException("client is aready online !!!");
		}
		
		getAccount().setStatus(status);
		getSession().setState(QQSession.State.LOGINING);
		ProcModule procModule = (ProcModule) getModule(QQModule.Type.PROC);
		return procModule.relogin(status, listener);
	}

	public void getCaptcha(QQActionListener listener) {
		LoginModule loginModule = (LoginModule) getModule(QQModule.Type.LOGIN);
		loginModule.getCaptcha(getAccount().getUin(), listener);
	}

	@Override
	public QQSession getSession() {
		return session;
	}

	@Override
	public void fireNotify(QQNotifyEvent event) {
		if (notifyListener != null) {
			try {
				notifyListener.onNotifyEvent(event);
			} catch (Throwable e) {
				LOG.warn("fireNotify Error!!", e);
			}
		}
		// 重新登录成功，重新poll
		if(event.getType() == Type.RELOGIN_SUCCESS) {
			beginPollMsg();
		}
	}

	@Override
	public void beginPollMsg() {
		if (session.getState() == QQSession.State.OFFLINE) {
			throw new IllegalArgumentException("client is aready offline !!!");
		}
		
		ProcModule procModule = (ProcModule) getModule(QQModule.Type.PROC);
		EmailModule emailModule = (EmailModule) getModule(QQModule.Type.EMAIL);
		procModule.doPollMsg();
		emailModule.doPoll();
	}

	@Override
	public QQActionFuture getBuddyList(QQActionListener listener) {
		CategoryModule categoryModule = (CategoryModule) getModule(QQModule.Type.CATEGORY);
		return categoryModule.getCategoryList(listener);
	}

	@Override
	public QQActionFuture getGroupList(QQActionListener listener) {
		GroupModule groupModule = (GroupModule) getModule(QQModule.Type.GROUP);
		return groupModule.getGroupList(listener);
	}

	@Override
	public QQActionFuture getOnlineList(QQActionListener qqActionListener) {
		BuddyModule buddyModule = getModule(QQModule.Type.BUDDY);
		return buddyModule.getOnlineBuddy(qqActionListener);
	}

	@Override
	public QQActionFuture getRecentList(QQActionListener qqActionListener) {
		BuddyModule buddyModule = getModule(QQModule.Type.BUDDY);
		return buddyModule.getRecentList(qqActionListener);
	}

	@Override
	public QQActionFuture getUserQQ(QQUser user, QQActionListener qqActionListener) {
		UserModule userModule = getModule(QQModule.Type.USER);
		return userModule.getUserAccount(user, qqActionListener);
	}

	@Override
	public QQActionFuture logout(final QQActionListener listener) {
		if (session.getState() == QQSession.State.OFFLINE) {
			throw new IllegalArgumentException("client is aready offline !!!");
		}
		
		ProcModule procModule = (ProcModule) getModule(QQModule.Type.PROC);
		return procModule.doLogout(new QQActionListener() {
			public void onActionEvent(QQActionEvent event) {
				// 无论退出登录失败还是成功，都需要释放资源
				if (event.getType() == QQActionEvent.Type.EVT_OK
						|| event.getType() == QQActionEvent.Type.EVT_ERROR) {
					session.setState(QQSession.State.OFFLINE);
					destroy();
				}
				
				if (listener != null) {
					listener.onActionEvent(event);
				}
			}
		});
	}

	@Override
	public QQActionFuture changeStatus(final QQStatus status, final QQActionListener listener) {
		UserModule userModule = (UserModule) getModule(QQModule.Type.USER);
		return userModule.changeStatus(status , listener);
	}
	
	@Override
	public QQActionFuture getGroupFace(QQGroup group, QQActionListener qqActionListener) {
		GroupModule mod = getModule(QQModule.Type.GROUP);
		return mod.getGroupFace(group, qqActionListener);
	}
	
	@Override
	public QQActionFuture getGroupInfo(QQGroup group, QQActionListener qqActionListener) {
		GroupModule mod = getModule(QQModule.Type.GROUP);
		return mod.getGroupInfo(group, qqActionListener);
	}
	
	@Override
	public QQActionFuture getGroupGid(QQGroup group, QQActionListener qqActionListener){
		GroupModule mod = getModule(QQModule.Type.GROUP);
		return mod.getGroupGid(group, qqActionListener);
	}

	@Override
	public QQActionFuture getUserFace(QQUser user, QQActionListener qqActionListener) {
		UserModule mod = getModule(QQModule.Type.USER);
		return mod.getUserFace(user, qqActionListener);
	}
	
	@Override
	public QQActionFuture getUserSign(QQUser user, QQActionListener qqActionListener) {
		UserModule mod = getModule(QQModule.Type.USER);
		return mod.getUserSign(user, qqActionListener);
	}
	
	@Override
	public QQActionFuture getUserLevel(QQUser user, QQActionListener qqActionListener){
		UserModule mod = getModule(QQModule.Type.USER);
		return mod.getUserLevel(user, qqActionListener);
	}

	@Override
	public QQActionFuture getUserInfo(QQUser user, QQActionListener qqActionListener) {
		UserModule mod = getModule(QQModule.Type.USER);
		return mod.getUserInfo(user, qqActionListener);
	}
	
	@Override
	public QQActionFuture getStrangerInfo(QQUser user, QQActionListener qqActionListener) {
		UserModule mod = getModule(QQModule.Type.USER);
		return mod.getStrangerInfo(user, qqActionListener);
	}
	
	@Override
	public QQActionFuture sendMsg(QQMsg msg, QQActionListener qqActionListener) {
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.sendMsg(msg, qqActionListener);
	}
	
	@Override
	public QQActionFuture sendShake(QQUser user, QQActionListener qqActionListener) {
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.sendShake(user, qqActionListener);
	}
	

	@Override
	public QQActionFuture getOffPic(OffPicItem offpic, QQMsg msg, OutputStream picout, 
					QQActionListener listener) {
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.getOffPic(offpic, msg, picout, listener);
	}
	
	@Override
	public QQActionFuture getUserPic(CFaceItem cface, QQMsg msg,
					OutputStream picout, QQActionListener listener){
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.getUserPic(cface, msg, picout, listener);
	}
	
	@Override
	public QQActionFuture getGroupPic(CFaceItem cface, QQMsg msg,
			OutputStream picout, QQActionListener listener){
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.getGroupPic(cface, msg, picout, listener);
	}
	
	@Override
	public QQActionFuture uploadOffPic(QQUser user, File file, QQActionListener listener){
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.uploadOffPic(user, file, listener);
	}
	
	@Override
	public QQActionFuture uploadCustomPic(File file, QQActionListener listener){
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.uploadCFace(file, listener);
	}
	
	@Override
	public QQActionFuture sendInputNotify(QQUser user, QQActionListener listener){
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.sendInputNotify(user, listener);
	}
	

	@Override
	public QQActionFuture getDiscuzList(QQActionListener qqActionListener) {
		DiscuzModule mod = getModule(QQModule.Type.DISCUZ);
		return mod.getDiscuzList(qqActionListener);
	}

	@Override
	public QQActionFuture getDiscuzInfo(QQDiscuz discuz, QQActionListener qqActionListener) {
		DiscuzModule mod = getModule(QQModule.Type.DISCUZ);
		return mod.getDiscuzInfo(discuz, qqActionListener);
	}
	
	@Override
	public QQActionFuture getSessionMsgSig(QQStranger user, QQActionListener qqActionListener) {
		ChatModule mod = getModule(QQModule.Type.CHAT);
		return mod.getSessionMsgSig(user, qqActionListener);
	}
	
	public QQActionFuture getGroupMemberStatus(QQGroup group, QQActionListener listener) {
		GroupModule mod = getModule(QQModule.Type.GROUP);
		return mod.getMemberStatus(group, listener);
	}

	@Override
	public void submitVerify(String code, QQNotifyEvent verifyEvent) {
		QQNotifyEventArgs.ImageVerify verify = 
			(QQNotifyEventArgs.ImageVerify) verifyEvent.getTarget();
		
		if(verify.type==VerifyType.LOGIN){
			ProcModule mod = getModule(QQModule.Type.PROC);
			mod.loginWithVerify(code, (ProcActionFuture)verify.future);
		}
	}
	
	@Override
	public QQActionFuture freshVerify(QQNotifyEvent verifyEvent, QQActionListener listener) {
		LoginModule mod = getModule(QQModule.Type.LOGIN);
		return mod.getCaptcha(account.getUin(), listener);
	}
	
	@Override
	public QQActionFuture updateGroupMessageFilter(QQActionListener listener) {
		GroupModule mod = getModule(QQModule.Type.GROUP);
		return mod.updateGroupMessageFilter(listener);
	}
	
	@Override
	public QQActionFuture searchGroupGetList(QQGroupSearchList resultList, QQActionListener listener)
	{
		GroupModule mod = getModule(QQModule.Type.GROUP);
		return mod.searchGroup(resultList, listener);
	}
	
	@Override
	public void cancelVerify(QQNotifyEvent verifyEvent) throws QQException {
		QQNotifyEventArgs.ImageVerify verify = 
			(QQNotifyEventArgs.ImageVerify) verifyEvent.getTarget();
		verify.future.cancel();
	}
	
	@Override
	public List<QQBuddy> getBuddyList() {
		return getStore().getBuddyList();
	}

	@Override
	public List<QQGroup> getGroupList() {
		return getStore().getGroupList();
	}

	@Override
	public List<QQDiscuz> getDiscuzList() {
		return getStore().getDiscuzList();
	}
	
	@Override
	public QQBuddy getBuddyByUin(long uin) {
		return getStore().getBuddyByUin(uin);
	}

	@Override
	public boolean isOnline() {
		return getSession().getState() == QQSession.State.ONLINE;
	}

	@Override
	public boolean isLogining() {
		return getSession().getState() == QQSession.State.LOGINING;
	}
}
