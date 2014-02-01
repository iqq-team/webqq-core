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
import iqq.im.action.GetGroupAccoutAction;
import iqq.im.action.GetGroupFaceAction;
import iqq.im.action.GetGroupInfoAction;
import iqq.im.action.GetGroupListAction;
import iqq.im.action.GetGroupMemberStatusAction;
import iqq.im.action.SearchGroupInfoAction;
import iqq.im.action.UpdateGroupMessageFilterAction;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQGroupSearchList;
import iqq.im.event.QQActionFuture;

/**
 * 
 * 好友列表模块，处理好友的添加和删除
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class GroupModule extends AbstractModule {

	public QQActionFuture getGroupList(QQActionListener listener) {
		return pushHttpAction(new GetGroupListAction(getContext(), listener));
	}
	
	public QQActionFuture updateGroupMessageFilter(QQActionListener listener) {
		return pushHttpAction(new UpdateGroupMessageFilterAction(getContext(), listener));
	}
	
	public QQActionFuture getGroupFace(QQGroup group, QQActionListener listener){
		return pushHttpAction(new GetGroupFaceAction(getContext(), listener, group));
	}

	public QQActionFuture getGroupInfo(QQGroup group, QQActionListener listener) {
		return pushHttpAction(new GetGroupInfoAction(getContext(), listener, group));
	}

	public QQActionFuture getGroupGid(QQGroup group, QQActionListener listener) {
		return pushHttpAction(new GetGroupAccoutAction(getContext(), listener, group));
	}
	
	public QQActionFuture getMemberStatus(QQGroup group, QQActionListener listener) {
		return pushHttpAction(new GetGroupMemberStatusAction(getContext(), listener, group));
	}
	
	public QQActionFuture searchGroup(QQGroupSearchList resultList, QQActionListener listener)
	{
		return pushHttpAction(new SearchGroupInfoAction(getContext(), listener, resultList));
	}
}
