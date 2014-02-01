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
 * File     : QQClient.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im;

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
import iqq.im.event.QQActionFuture;
import iqq.im.event.QQNotifyEvent;
import iqq.im.service.HttpService.ProxyType;

import java.io.File;
import java.io.OutputStream;
import java.util.List;


/**
 *
 * QQ客户端接口
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public interface QQClient {
	public void destroy();
	public QQActionFuture login(QQStatus status, QQActionListener listener);
	public QQActionFuture relogin(QQStatus status, QQActionListener listener);
	public QQActionFuture logout(QQActionListener listener);
	public QQActionFuture changeStatus(QQStatus status, QQActionListener listener);
	public QQActionFuture getBuddyList(QQActionListener qqActionListener);
	public QQActionFuture getOnlineList(QQActionListener qqActionListener);
	public QQActionFuture getRecentList(QQActionListener qqActionListener);
	public QQActionFuture getUserFace(QQUser user, QQActionListener qqActionListener);
	public QQActionFuture getUserSign(QQUser user, QQActionListener qqActionListener);
	public QQActionFuture getUserInfo(QQUser user, QQActionListener qqActionListener);
	public QQActionFuture getUserQQ(QQUser user, QQActionListener qqActionListener);
	public QQActionFuture getUserLevel(QQUser user, QQActionListener qqActionListener);
	public QQActionFuture getStrangerInfo(QQUser user, QQActionListener qqActionListener);
	public QQActionFuture getGroupList(QQActionListener listener);
	public QQActionFuture getGroupFace(QQGroup group, QQActionListener qqActionListener);
	public QQActionFuture getGroupInfo(QQGroup group, QQActionListener qqActionListener);
	public QQActionFuture getGroupGid(QQGroup group, QQActionListener qqActionListener);
	public QQActionFuture getGroupMemberStatus(QQGroup group, QQActionListener listener);
	public QQActionFuture getDiscuzList(QQActionListener qqActionListener);
	public QQActionFuture getDiscuzInfo(QQDiscuz discuz, QQActionListener qqActionListener);
	public QQActionFuture getSessionMsgSig(QQStranger user, QQActionListener qqActionListener);
	public QQActionFuture sendMsg(QQMsg msg, QQActionListener qqActionListener);
	public QQActionFuture sendShake(QQUser user, QQActionListener qqActionListener);
	public QQActionFuture getOffPic(OffPicItem offpic, QQMsg msg,
										OutputStream picout, QQActionListener listener);
	public QQActionFuture getUserPic(CFaceItem cface, QQMsg msg,
										OutputStream picout, QQActionListener listener);
	public QQActionFuture getGroupPic(CFaceItem cface, QQMsg msg,
										OutputStream picout, QQActionListener listener);
	public QQActionFuture uploadOffPic(QQUser user, File file, QQActionListener listener);
	public QQActionFuture uploadCustomPic(File file, QQActionListener listener);
	public QQActionFuture sendInputNotify(QQUser user, QQActionListener listener);
	public QQActionFuture freshVerify(QQNotifyEvent verifyEvent, QQActionListener listener);
	public QQActionFuture updateGroupMessageFilter(QQActionListener listener);
	
	
	public QQActionFuture searchGroupGetList(QQGroupSearchList resultList, QQActionListener listener);
	
	public void submitVerify(String code, QQNotifyEvent verifyEvent);
	public void cancelVerify(QQNotifyEvent verifyEvent) throws QQException;
	public void beginPollMsg();
	public void setHttpUserAgent(String userAgent);
	public void setHttpProxy(ProxyType proxyType, String proxyHost,
							 int proxyPort, String proxyAuthUser, String proxyAuthPassword);
	
	
	public List<QQBuddy> getBuddyList();
	public List<QQGroup> getGroupList();
	public List<QQDiscuz> getDiscuzList();
	public QQBuddy getBuddyByUin(long uin);
	public QQAccount getAccount();
	public boolean isOnline();
	public boolean isLogining();
}