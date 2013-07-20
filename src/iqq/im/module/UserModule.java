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
 * Project  : WebQQCoreAsync
 * Package  : iqq.im.module
 * File     : UserModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package iqq.im.module;

import iqq.im.QQActionListener;
import iqq.im.action.ChangeStatusAction;
import iqq.im.action.GetFriendAccoutAction;
import iqq.im.action.GetFriendFaceAction;
import iqq.im.action.GetFriendInfoAction;
import iqq.im.action.GetFriendSignAction;
import iqq.im.action.GetStrangerInfoAction;
import iqq.im.action.GetUserLevelAction;
import iqq.im.bean.QQStatus;
import iqq.im.bean.QQUser;
import iqq.im.event.QQActionFuture;

/**
 * 
 * 个人信息模块
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class UserModule extends AbstractModule {
	public QQActionFuture getUserFace(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetFriendFaceAction(getContext(), listener, user));
	}
	
	public QQActionFuture getUserInfo(QQUser user, QQActionListener listener){
		return pushHttpAction(new GetFriendInfoAction(getContext(), listener, user));
	}
	
	public QQActionFuture getUserAccount(QQUser user, QQActionListener listener){
		return pushHttpAction(new GetFriendAccoutAction(getContext(), listener, user));
	}
	
	public QQActionFuture getUserSign(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetFriendSignAction(getContext(), listener, user));
	}
	
	public QQActionFuture getUserLevel(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetUserLevelAction(getContext(), listener, user));
	}
	

	public QQActionFuture changeStatus(QQStatus status, QQActionListener listener) {
		return pushHttpAction(new ChangeStatusAction(getContext(), listener, status));
	}
	
	public QQActionFuture getStrangerInfo(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetStrangerInfoAction(getContext(), listener, user));
	}
}
