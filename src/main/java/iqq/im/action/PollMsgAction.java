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
 * Package  : iqq.im
 * File     : WebQQClientTest.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-2-17
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQClientType;
import iqq.im.bean.QQDiscuz;
import iqq.im.bean.QQDiscuzMember;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQGroupMember;
import iqq.im.bean.QQHalfStranger;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQStatus;
import iqq.im.bean.QQStranger;
import iqq.im.bean.QQUser;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.core.QQStore;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 轮询Poll消息
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class PollMsgAction extends AbstractHttpAction {

	private static final Logger LOG = Logger.getLogger(PollMsgAction.class);

	public PollMsgAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		JSONObject json = new JSONObject();
		json.put("clientid", session.getClientId());
		json.put("psessionid", session.getSessionId());
		json.put("key", 0); // 暂时不知道什么用的
		json.put("ids", new JSONArray()); // 同上

		QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_POLL_MSG);
		req.addPostValue("r", json.toString());
		req.addPostValue("clientid", session.getClientId() + "");
		req.addPostValue("psessionid", session.getSessionId());
		req.setReadTimeout(70 * 1000);
		req.setConnectTimeout(10 * 1000);
		req.addHeader("Referer", QQConstants.REFFER);
		
		return req;
	}
	
	@Override
	public void onHttpFinish(QQHttpResponse response) {
		//如果返回的内容为空，认为这次pollMsg仍然成功
		if(response.getContentLength() == 0){
			LOG.debug("PollMsgAction: empty response!!!!");
			notifyActionEvent(QQActionEvent.Type.EVT_OK, new ArrayList<QQNotifyEvent>());
		}else{
			super.onHttpFinish(response);
		}
	}

	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		QQStore store = getContext().getStore();
		List<QQNotifyEvent> notifyEvents = new ArrayList<QQNotifyEvent>();
		JSONObject json = new JSONObject(response.getResponseString());
		int retcode = json.getInt("retcode");
		if (retcode == 0) {
			//有可能为  {"retcode":0,"result":"ok"}
			if ( !json.isNull("result") && json.get("result") instanceof JSONArray) {
				JSONArray results = json.getJSONArray("result");
				// 消息下载来的列表中是倒过来的，那我直接倒过来取，编位回来
				for (int i = results.length() - 1; i >= 0; i--) {
					JSONObject poll = results.getJSONObject(i);
					String pollType = poll.getString("poll_type");
					JSONObject pollData = poll.getJSONObject("value");
					if (pollType.equals("input_notify")) {
						long fromUin = pollData.getLong("from_uin");
						QQBuddy buddy = store.getBuddyByUin(fromUin);
						notifyEvents.add(new QQNotifyEvent(
								QQNotifyEvent.Type.BUDDY_INPUT, buddy));
					} else if (pollType.equals("message")) {
						// 好友消息
						notifyEvents.add(processBuddyMsg(pollData));
					} else if (pollType.equals("group_message")) {
						// 群消息
						notifyEvents.add(processGroupMsg(pollData));
					} else if (pollType.equals("discu_message")) {
						// 讨论组消息
						notifyEvents.add(processDiscuzMsg(pollData));
					} else if (pollType.equals("sess_message")) {
						// 临时会话消息
						notifyEvents.add(processSessionMsg(pollData));
					} else if (pollType.equals("shake_message")) {
						// 窗口震动
						long fromUin = pollData.getLong("from_uin");
						QQUser user = getContext().getStore().getBuddyByUin(
								fromUin);
						notifyEvents.add(new QQNotifyEvent(
								QQNotifyEvent.Type.SHAKE_WINDOW, user));
					} else if (pollType.equals("kick_message")) {
						// 被踢下线
						getContext().getAccount().setStatus(QQStatus.OFFLINE);
						getContext().getSession().setState(
								QQSession.State.KICKED);
						notifyEvents.add(new QQNotifyEvent(
								QQNotifyEvent.Type.KICK_OFFLINE, pollData
										.getString("reason")));
					} else if (pollType.equals("buddies_status_change")) {
						notifyEvents.add(processBuddyStatusChange(pollData));
					} else {
						// TODO ...
						LOG.warn("unknown pollType: " + pollType);
					}
				}
			}
			// end recode == 0
		} else if (retcode == 102) {
			// 接连正常，没有消息到达 {"retcode":102,"errmsg":""}
			// 继续进行下一个消息请求
			
		} else if (retcode == 110 || retcode == 109) { // 客户端主动退出
			getContext().getSession().setState(QQSession.State.OFFLINE);
		} else if (retcode == 116) {
			// 需要更新Ptwebqq值，暂时不知道干嘛用的
			// {"retcode":116,"p":"2c0d8375e6c09f2af3ce60c6e081bdf4db271a14d0d85060"}
			// if (a.retcode === 116) alloy.portal.setPtwebqq(a.p)
			getContext().getSession().setPtwebqq(json.getString("p"));
		} else if (retcode == 121 || retcode == 120 || retcode == 100) {	// 121,120 : ReLinkFailure		100 : NotReLogin
			// 服务器需求重新认证
			// {"retcode":121,"t":"0"}
			LOG.info("**** NEED_REAUTH retcode: " + retcode + " ****");
			getContext().getSession().setState(QQSession.State.OFFLINE);
			QQException ex = new QQException(QQException.QQErrorCode.INVALID_LOGIN_AUTH);
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, ex);
			return ;
			//notifyEvents.add(new QQNotifyEvent(QQNotifyEvent.Type.NEED_REAUTH, null));
		} else {
			
			LOG.error("**Reply retcode to author**");
			LOG.error("***************************");
			LOG.error("Unknown retcode: " + retcode);
			LOG.error("***************************");
			// 返回错误，核心遇到未知recode
			// getContext().getSession().setState(QQSession.State.ERROR);
			notifyEvents.add(new QQNotifyEvent(QQNotifyEvent.Type.UNKNOWN_ERROR, json));
		}
		notifyActionEvent(QQActionEvent.Type.EVT_OK, notifyEvents);
	}

	public QQNotifyEvent processBuddyStatusChange(JSONObject pollData)
			throws JSONException {
		long uin = pollData.getLong("uin");
		QQBuddy buddy = getContext().getStore().getBuddyByUin(uin);
		String status = pollData.getString("status");
		int clientType = pollData.getInt("client_type");
		buddy.setStatus(QQStatus.valueOfRaw(status));
		buddy.setClientType(QQClientType.valueOfRaw(clientType));
		return new QQNotifyEvent(QQNotifyEvent.Type.BUDDY_STATUS_CHANGE, buddy);
	}

	public QQNotifyEvent processBuddyMsg(JSONObject pollData)
			throws JSONException, QQException {
		QQStore store = getContext().getStore();

		long fromUin = pollData.getLong("from_uin");
		QQMsg msg = new QQMsg();
		msg.setId(pollData.getLong("msg_id"));
		msg.setId2(pollData.getLong("msg_id2"));
		msg.parseContentList(pollData.getJSONArray("content").toString());
		msg.setType(QQMsg.Type.BUDDY_MSG);
		msg.setTo(getContext().getAccount());
		msg.setFrom(store.getBuddyByUin(fromUin));
		msg.setDate(new Date(pollData.getLong("time") * 1000));
		if (msg.getFrom() == null) {
			QQUser member = store.getStrangerByUin(fromUin); // 搜索陌生人列表
			if (member == null) {
				member = new QQHalfStranger();
				member.setUin(fromUin);
				store.addStranger((QQStranger) member);
			}
			msg.setFrom(member);
		}

		return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
	}

	public QQNotifyEvent processGroupMsg(JSONObject pollData)
			throws JSONException, QQException {
		// {"retcode":0,"result":[{"poll_type":"group_message",
		// "value":{"msg_id":6175,"from_uin":3924684389,"to_uin":1070772010,"msg_id2":992858,"msg_type":43,"reply_ip":176621921,
		// "group_code":3439321257,"send_uin":1843694270,"seq":875,"time":1365934781,"info_seq":170125666,"content":[["font",{"size":10,"color":"3b3b3b","style":[0,0,0],"name":"\u5FAE\u8F6F\u96C5\u9ED1"}],"eeeeeeeee "]}}]}

		QQStore store = getContext().getStore();
		QQMsg msg = new QQMsg();
		msg.setId(pollData.getLong("msg_id"));
		msg.setId2(pollData.getLong("msg_id2"));
		long fromUin = pollData.getLong("send_uin");
		long groupCode = pollData.getLong("group_code");
		long groupID = pollData.getLong("info_seq"); // 真实群号码
		QQGroup group = store.getGroupByCode(groupCode);
		if (group.getGid() <= 0) {
			group.setGid(groupID);
		}
		msg.parseContentList(pollData.getJSONArray("content").toString());
		msg.setType(QQMsg.Type.GROUP_MSG);
		msg.setGroup(group);
		msg.setTo(getContext().getAccount());
		msg.setDate(new Date(pollData.getLong("time") * 1000));

		if (group != null) {
			msg.setFrom(group.getMemberByUin(fromUin));
		}

		if (msg.getFrom() == null) {
			QQGroupMember member = new QQGroupMember();
			member.setUin(fromUin);
			msg.setFrom(member);
			if (group != null) {
				group.getMembers().add(member);
			}
		}

		return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
	}

	public QQNotifyEvent processDiscuzMsg(JSONObject pollData)
			throws JSONException, QQException {
		QQStore store = getContext().getStore();

		QQMsg msg = new QQMsg();
		long fromUin = pollData.getLong("send_uin");
		long did = pollData.getLong("did");

		msg.parseContentList(pollData.getJSONArray("content").toString());
		msg.setType(QQMsg.Type.DISCUZ_MSG);
		msg.setDiscuz(store.getDiscuzByDid(did));
		msg.setTo(getContext().getAccount());
		msg.setDate(new Date(pollData.getLong("time") * 1000));

		if (msg.getDiscuz() != null) {
			msg.setFrom(msg.getDiscuz().getMemberByUin(fromUin));
		}

		if (msg.getFrom() == null) {
			QQDiscuzMember member = new QQDiscuzMember();
			member.setUin(fromUin);
			msg.setFrom(member);
			if (msg.getDiscuz() != null) {
				msg.getDiscuz().getMembers().add(member);
			}
		}
		return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
	}

	public QQNotifyEvent processSessionMsg(JSONObject pollData)
			throws JSONException, QQException {
		// {"retcode":0,"result":[{"poll_type":"sess_message",
		// "value":{"msg_id":25144,"from_uin":167017143,"to_uin":1070772010,"msg_id2":139233,"msg_type":140,"reply_ip":176752037,"time":1365931836,"id":2581801127,"ruin":444674479,"service_type":1,
		// "flags":{"text":1,"pic":1,"file":1,"audio":1,"video":1},"content":[["font",{"size":9,"color":"000000","style":[0,0,0],"name":"Tahoma"}],"2\u8F7D3 ",["face",1]," "]}}]}
		QQStore store = getContext().getStore();

		QQMsg msg = new QQMsg();
		long fromUin = pollData.getLong("from_uin");
		long fromQQ = pollData.getLong("ruin"); // 真实QQ
		int serviceType = pollData.getInt("service_type"); // Group:0,Discuss:1
		long typeId = pollData.getLong("id"); // Group ID or Discuss ID

		msg.parseContentList(pollData.getJSONArray("content").toString());
		msg.setType(QQMsg.Type.SESSION_MSG);
		msg.setTo(getContext().getAccount());
		msg.setDate(new Date(pollData.getLong("time") * 1000));

		QQUser user = store.getBuddyByUin(fromUin); // 首先看看是不是自己的好友
		if (user != null) {
			msg.setType(QQMsg.Type.BUDDY_MSG); // 是自己的好友
		} else {
			if (serviceType == 0) { // 是群成员
				QQGroup group = store.getGroupByCode(typeId);
				for (QQUser u : group.getMembers()) {
					if (u.getUin() == fromUin) {
						user = u;
						break;
					}
				}
			} else if (serviceType == 1) { // 是讨论组成员
				QQDiscuz discuz = store.getDiscuzByDid(typeId);
				for (QQUser u : discuz.getMembers()) {
					if (u.getUin() == fromUin) {
						user = u;
						break;
					}
				}
			} else {
				user = store.getStrangerByUin(fromUin); // 看看陌生人列表中有木有
			}
			if (user == null) { // 还没有就新建一个陌生人，原理来说不应该这样。后面我就不知道怎么回复这消息了，但是消息是不能丢失的
				user = new QQStranger();
				user.setQQ(pollData.getLong("ruin"));
				user.setUin(fromUin);
				user.setNickname(pollData.getLong("ruin") + "");
				store.addStranger((QQStranger)user);
			}
		}
		user.setQQ(fromQQ); // 带上QQ号码
		msg.setFrom(user);
		return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
	}
}
