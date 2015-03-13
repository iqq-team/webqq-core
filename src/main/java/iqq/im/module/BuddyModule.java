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
 * Package  : iqq.im.module
 * File     : BuddyModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package iqq.im.module;

import iqq.im.QQActionListener;
import iqq.im.action.AcceptBuddyAddAction;
import iqq.im.action.GetOnlineFriendAction;
import iqq.im.action.GetRecentListAction;
import iqq.im.event.QQActionFuture;

/**
 *
 * 好友信息处理模块
 *
 * @author solosky
 */
public class BuddyModule extends AbstractModule {
	/**
	 * <p>getOnlineBuddy.</p>
	 *
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @return a {@link iqq.im.event.QQActionFuture} object.
	 */
	public QQActionFuture getOnlineBuddy(QQActionListener listener) {
		return pushHttpAction(new GetOnlineFriendAction(getContext(), listener));
	}
	
	/**
	 * <p>getRecentList.</p>
	 *
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @return a {@link iqq.im.event.QQActionFuture} object.
	 */
	public QQActionFuture getRecentList(QQActionListener listener){
		return pushHttpAction(new GetRecentListAction(getContext(), listener));
	}
	
	/**
	 * <p>addBuddy.</p>
	 *
	 * @param account target qq
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @return a {@link iqq.im.event.QQActionFuture} object.
	 */
	public QQActionFuture addBuddy(QQActionListener listener, String account){
		return pushHttpAction(new AcceptBuddyAddAction(getContext(), listener,account));
	}
}
