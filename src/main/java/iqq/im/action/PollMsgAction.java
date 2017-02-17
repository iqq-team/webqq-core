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
import iqq.im.bean.*;
import iqq.im.core.*;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.module.DiscuzModule;
import iqq.im.module.GroupModule;
import iqq.im.module.UserModule;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 轮询Poll消息
 * <p/>
 * 增加更多的消息处理（群被踢、群文件共享、加好友请求）
 *
 * @author solosky
 * @author elthon
 */
public class PollMsgAction extends AbstractHttpAction {

    private static final Logger LOG = LoggerFactory.getLogger(PollMsgAction.class);

    /**
     * <p>Constructor for PollMsgAction.</p>
     *
     * @param context  a {@link iqq.im.core.QQContext} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     */
    public PollMsgAction(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
        QQSession session = getContext().getSession();
        JSONObject json = new JSONObject();
        json.put("clientid", session.getClientId());
        json.put("psessionid", session.getSessionId());
        json.put("key", ""); // 暂时不知道什么用的
//        json.put("ids", new JSONArray()); // 同上
        json.put("ptwebqq", session.getPtwebqq());
        QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_POLL_MSG);
        req.addPostValue("r", json.toString());
        req.addPostValue("clientid", session.getClientId() + "");
        req.addPostValue("psessionid", session.getSessionId());
        req.setReadTimeout(70 * 1000);
        req.setConnectTimeout(10 * 1000);
        req.addHeader("Referer", QQConstants.REFFER);
        req.addHeader("Origin", QQConstants.ORIGIN);
        return req;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onHttpFinish(QQHttpResponse response) {
        //如果返回的内容为空，认为这次pollMsg仍然成功
       /* if (response.getContentLength() == 0) {
            LOG.info("PollMsgAction: empty response!!!! "+response.getResponseString());
            notifyActionEvent(QQActionEvent.Type.EVT_OK, new ArrayList<QQNotifyEvent>());
        } else {
            super.onHttpFinish(response);
        }*/
        super.onHttpFinish(response);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
            JSONException {
        QQStore store = getContext().getStore();
        List<QQNotifyEvent> notifyEvents = new ArrayList<QQNotifyEvent>();
        JSONObject json = new JSONObject(response.getResponseString());
        LOG.info(json.toString());
        int retcode = json.getInt("retcode");
        if (retcode == 0) {
            //有可能为  {"retcode":0,"result":"ok"}
            if (!json.isNull("result") && json.get("result") instanceof JSONArray) {
                JSONArray results = json.getJSONArray("result");
                // 消息下载来的列表中是倒过来的，那我直接倒过来取，编位回来
                for (int i = results.length() - 1; i >= 0; i--) {
                    JSONObject poll = results.getJSONObject(i);
                    String pollType = poll.getString("poll_type");
                    JSONObject pollData = poll.getJSONObject("value");
                    if (pollType.equals("input_notify")) {
                        long fromUin = pollData.getLong("from_uin");
                        QQBuddy buddy = store.getBuddyByUin(fromUin);
                        notifyEvents.add(new QQNotifyEvent(QQNotifyEvent.Type.BUDDY_INPUT, buddy));
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
                        QQUser user = getContext().getStore().getBuddyByUin(fromUin);
                        notifyEvents.add(new QQNotifyEvent(QQNotifyEvent.Type.SHAKE_WINDOW, user));
                    } else if (pollType.equals("kick_message")) {
                        // 被踢下线
                        getContext().getAccount().setStatus(QQStatus.OFFLINE);
                        getContext().getSession().setState(QQSession.State.KICKED);
                        notifyEvents.add(new QQNotifyEvent(
                                QQNotifyEvent.Type.KICK_OFFLINE, pollData
                                .getString("reason")));
                    } else if (pollType.equals("buddies_status_change")) {
                        notifyEvents.add(processBuddyStatusChange(pollData));
                    } else if (pollType.equals("system_message")) {
                        //好友添加
                        QQNotifyEvent processSystemMessage = processSystemMsg(pollData);
                        if (processSystemMessage != null) {
                            notifyEvents.add(processSystemMessage);
                        }
                    } else if (pollType.equals("group_web_message")) {
                        //发布了共享文件
                        QQNotifyEvent processSystemMessage = processGroupWebMsg(pollData);
                        if (processSystemMessage != null) {
                            notifyEvents.add(processSystemMessage);
                        }
                    } else if (pollType.equals("sys_g_msg")) {
                        //被踢出了群
                        QQNotifyEvent processSystemMessage = processSystemGroupMsg(pollData);
                        if (processSystemMessage != null) {
                            notifyEvents.add(processSystemMessage);
                        }
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
        } else if (retcode == 121 || retcode == 120 || retcode == 100) {    // 121,120 : ReLinkFailure		100 : NotReLogin
            // 服务器需求重新认证
            // {"retcode":121,"t":"0"}
            LOG.info("**** NEED_REAUTH retcode: " + retcode + " ****");
            getContext().getSession().setState(QQSession.State.OFFLINE);
            QQException ex = new QQException(QQException.QQErrorCode.INVALID_LOGIN_AUTH);
            notifyActionEvent(QQActionEvent.Type.EVT_ERROR, ex);
            return;
            //notifyEvents.add(new QQNotifyEvent(QQNotifyEvent.Type.NEED_REAUTH, null));
        }else if(retcode == 103){
            //账号异常，需要人工登录。
            getContext().getSession().setState(QQSession.State.OFFLINE);
            QQException ex = new QQException(QQException.QQErrorCode.USER_ERROR);
            notifyActionEvent(QQActionEvent.Type.EVT_ERROR,ex);
            return;
        }else {

            LOG.error("**Reply retcode to author**");
            LOG.error("***************************");
            LOG.error("Unknown retcode: " + retcode);
            LOG.error("***************************");
            // 返回错误，核心遇到未知retcode
            // getContext().getSession().setState(QQSession.State.ERROR);
            notifyEvents.add(new QQNotifyEvent(QQNotifyEvent.Type.UNKNOWN_ERROR, json));
        }
        notifyActionEvent(QQActionEvent.Type.EVT_OK, notifyEvents);
    }

    /**
     * <p>processBuddyStatusChange.</p>
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     */
    public QQNotifyEvent processBuddyStatusChange(JSONObject pollData)
            throws JSONException {
        LOG.info(pollData + "");

        long uin = pollData.getLong("uin");
        String status = pollData.getString("status");
        int clientType = pollData.getInt("client_type");
        QQStore store = getContext().getStore();
        QQBuddy buddy = store.getBuddyByUin(uin);
        if (buddy == null) {
            buddy = new QQBuddy();
            buddy.setUin(uin);
            store.addBuddy(buddy);

            // 获取用户信息
            UserModule userModule = getContext().getModule(QQModule.Type.USER);
            userModule.getUserInfo(buddy, null);
        }
        buddy.setStatus(QQStatus.valueOfRaw(status));
        buddy.setClientType(QQClientType.valueOfRaw(clientType));
        return new QQNotifyEvent(QQNotifyEvent.Type.BUDDY_STATUS_CHANGE, buddy);
    }

    /**
     * <p>processBuddyMsg.</p>
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     * @throws iqq.im.QQException     if any.
     */
    public QQNotifyEvent processBuddyMsg(JSONObject pollData)
            throws JSONException, QQException {
        QQStore store = getContext().getStore();

        long fromUin = pollData.getLong("from_uin");
        QQMsg msg = new QQMsg();
        msg.setId(pollData.getLong("msg_id"));
        msg.setId2(pollData.has("msg_id2") ? pollData.getLong("msg_id2") : 0);
        msg.parseContentList(pollData.getJSONArray("content").toString());
        msg.setType(QQMsg.Type.BUDDY_MSG);
        msg.setTo(getContext().getAccount());
        msg.setFrom(store.getBuddyByUin(fromUin));
        msg.setDate(new Date(pollData.getLong("time") * 1000));
        if (msg.getFrom() == null) {
            QQBuddy member = store.getBuddyByUin(fromUin); // 搜索好友列表
            if (member == null) {
                member = new QQBuddy();
                member.setUin(fromUin);
                store.addBuddy(member);

                // 获取用户信息
                UserModule userModule = getContext().getModule(QQModule.Type.USER);
                userModule.getUserInfo(member, null);
            }
            msg.setFrom(member);
        }

        return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
    }

    /**
     * <p>processGroupMsg.</p>
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     * @throws iqq.im.QQException     if any.
     */
    public QQNotifyEvent processGroupMsg(JSONObject pollData) throws JSONException, QQException {
        LOG.info(pollData.toString());
        QQStore store = getContext().getStore();
        QQMsg msg = new QQMsg();
        msg.setId(pollData.getLong("msg_id"));
        msg.setId2(pollData.has("msg_id2")?pollData.getLong("msg_id2"):0);
        long fromUin = pollData.getLong("send_uin");
        long groupUin = pollData.getLong("from_uin");
        long groupCode = pollData.getLong("group_code");
        QQGroup group = store.getGroupByGin(groupCode);
        if (group == null) {
            GroupModule groupModule = getContext().getModule(QQModule.Type.GROUP);
            group = new QQGroup();
            group.setGin(groupUin);
            group.setCode(groupCode);
            // put to store
            store.addGroup(group);
            groupModule.getGroupInfo(group, null);
        }
        msg.parseContentList(pollData.getJSONArray("content").toString());
        msg.setType(QQMsg.Type.GROUP_MSG);
        msg.setGroup(group);
        msg.setTo(getContext().getAccount());
        msg.setDate(new Date(pollData.getLong("time") * 1000));
        msg.setFrom(group.getMemberByUin(fromUin));
        if (msg.getFrom() == null) {
            QQGroupMember member = new QQGroupMember();
            member.setUin(fromUin);
            msg.setFrom(member);
            group.getMembers().add(member);

            // 获取用户信息
            UserModule userModule = getContext().getModule(QQModule.Type.USER);
            userModule.getStrangerInfo(member, null);
        }

        return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
    }

    /**
     * <p>processDiscuzMsg.</p>
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     * @throws iqq.im.QQException     if any.
     */
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

        if (msg.getDiscuz() == null) {
            QQDiscuz discuz = new QQDiscuz();
            discuz.setDid(did);
            msg.setDiscuz(discuz);
            store.addDiscuz(discuz);

            DiscuzModule discuzModule = getContext().getModule(QQModule.Type.DISCUZ);
            discuzModule.getDiscuzInfo(discuz, null);
        }
        msg.setFrom(msg.getDiscuz().getMemberByUin(fromUin));

        if (msg.getFrom() == null) {
            QQDiscuzMember member = new QQDiscuzMember();
            member.setUin(fromUin);
            msg.setFrom(member);
            msg.getDiscuz().getMembers().add(member);

            // 获取用户信息
            UserModule userModule = getContext().getModule(QQModule.Type.USER);
            userModule.getStrangerInfo(member, null);
        }
        return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
    }

    /**
     * <p>processSessionMsg.</p>
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     * @throws iqq.im.QQException     if any.
     */
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
                if (group == null) {
                    group = new QQGroup();
                    group.setCode(typeId);
                    // 获取群信息
                    GroupModule groupModule = getContext().getModule(QQModule.Type.GROUP);
                    groupModule.getGroupInfo(group, null);
                }
                for (QQUser u : group.getMembers()) {
                    if (u.getUin() == fromUin) {
                        user = u;
                        break;
                    }
                }
            } else if (serviceType == 1) { // 是讨论组成员
                QQDiscuz discuz = store.getDiscuzByDid(typeId);
                if (discuz == null) {
                    discuz = new QQDiscuz();
                    discuz.setDid(typeId);

                    // 获取讨论组信息
                    DiscuzModule discuzModule = getContext().getModule(QQModule.Type.DISCUZ);
                    discuzModule.getDiscuzInfo(discuz, null);
                }
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
                store.addStranger((QQStranger) user);

                // 获取用户信息
                UserModule userModule = getContext().getModule(QQModule.Type.USER);
                userModule.getStrangerInfo(user, null);
            }
        }
        user.setQQ(fromQQ); // 带上QQ号码
        msg.setFrom(user);
        return new QQNotifyEvent(QQNotifyEvent.Type.CHAT_MSG, msg);
    }

    /**
     * <p>processSystemMessage.</p>
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     */
    public QQNotifyEvent processSystemMsg(JSONObject pollData)
            throws JSONException {
        String type = pollData.optString("type");
        if (StringUtils.isNoneBlank(type) && type.equalsIgnoreCase("verify_required")) {    //好友请求
            JSONObject target = new JSONObject();
            target.put("type", "verify_required");    //通知类型（好友请求）
            target.put("from_uin", pollData.optLong("from_uin"));    //哪个人请求
            target.put("msg", pollData.optString("msg"));    //请求添加好友原因
            return new QQNotifyEvent(QQNotifyEvent.Type.BUDDY_NOTIFY, target.toString());
        }
        LOG.warn("暂不识别的系统消息：" + pollData.toString());
        return null;
    }

    /**
     * <p>processGroupWebMsg.</p>
     * 处理其他人上传文件的通知
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     */
    public QQNotifyEvent processGroupWebMsg(JSONObject pollData)
            throws JSONException {
        //{"retcode":0,"result":[{"poll_type":"group_web_message","value":{"msg_id":25082,"from_uin":802292893,"to_uin":3087958343,"msg_id2":343597,"msg_type":45,"reply_ip":176756769,"group_code":898704454,"group_type":1,"ver":1,"send_uin":3014857601,"xml":"\u003c?xml version=\"1.0\" encoding=\"utf-8\"?\u003e\u003cd\u003e\u003cn t=\"h\" u=\"2519967390\"/\u003e\u003cn t=\"t\" s=\"\u5171\u4EAB\u6587\u4EF6\"/\u003e\u003cn t=\"b\"/\u003e\u003cn t=\"t\" s=\"IMG_1193.jpg\"/\u003e\u003c/d\u003e"}}]}
        JSONObject target = new JSONObject();
        target.put("type", "share_file");    //通知类型（共享群文件消息）
        target.put("file", pollData.optString("xml"));    //共享的文件信息
        target.put("sender", pollData.optLong("send_uin"));    //共享者
        return new QQNotifyEvent(QQNotifyEvent.Type.GROUP_NOTIFY, target.toString());
    }

    /**
     * <p>processSystemMessage.</p>
     *
     * @param pollData a {@link org.json.JSONObject} object.
     * @return a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws org.json.JSONException if any.
     */
    public QQNotifyEvent processSystemGroupMsg(JSONObject pollData)
            throws JSONException {
        //{"retcode":0,"result":[{"poll_type":"sys_g_msg","value":{"msg_id":39855,"from_uin":802292893,"to_uin":3087958343,"msg_id2":518208,"msg_type":34,"reply_ip":176757073,"type":"group_leave","gcode":898704454,"t_gcode":310070477,"op_type":3,"old_member":3087958343,"t_old_member":"","admin_uin":1089498579,"t_admin_uin":"","admin_nick":"\u521B\u5EFA\u8005"}}]}
        String type = pollData.optString("type");
        if (StringUtils.isNoneBlank(type) && type.equals("group_leave")) {
            JSONObject target = new JSONObject();
            target.put("type", "group_leave");    //通知类型（离群消息）
            target.put("group_code", pollData.optLong("gcode"));    //从那个群（群临时编号）
            target.put("group_num", pollData.optLong("t_gcode"));    //从那个群（群号）
            target.put("admin_uin", pollData.optLong("admin_uin"));    //被哪个人踢
            target.put("admin_nick", pollData.optString("admin_nick"));
            return new QQNotifyEvent(QQNotifyEvent.Type.GROUP_NOTIFY, target.toString());
        }
        LOG.warn("暂不识别的系统消息：" + pollData.toString());
        return null;
    }
}
