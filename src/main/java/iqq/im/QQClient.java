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
 * QQ客户端接口
 *
 * @author solosky
 */
public interface QQClient {
    /**
     * <p>destroy.</p>
     */
    public void destroy();

    public void getQRcode(QQActionListener qqActionListener);

    public void checkQRCode(QQActionListener qqActionListener);

    /**
     * <p>login.</p>
     *
     * @param status   a {@link iqq.im.bean.QQStatus} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture login(QQStatus status, QQActionListener listener);

    /**
     * <p>relogin.</p>
     *
     * @param status   a {@link iqq.im.bean.QQStatus} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture relogin(QQStatus status, QQActionListener listener);

    /**
     * <p>logout.</p>
     *
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture logout(QQActionListener listener);

    /**
     * <p>changeStatus.</p>
     *
     * @param status   a {@link iqq.im.bean.QQStatus} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture changeStatus(QQStatus status, QQActionListener listener);

    /**
     * <p>getBuddyList.</p>
     *
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getBuddyList(QQActionListener qqActionListener);

    /**
     * <p>getOnlineList.</p>
     *
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getOnlineList(QQActionListener qqActionListener);

    /**
     * <p>getRecentList.</p>
     *
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getRecentList(QQActionListener qqActionListener);

    /**
     * <p>getUserFace.</p>
     *
     * @param user             a {@link iqq.im.bean.QQUser} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getUserFace(QQUser user, QQActionListener qqActionListener);

    /**
     * <p>getUserSign.</p>
     *
     * @param user             a {@link iqq.im.bean.QQUser} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getUserSign(QQUser user, QQActionListener qqActionListener);

    /**
     * <p>getUserInfo.</p>
     *
     * @param user             a {@link iqq.im.bean.QQUser} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getUserInfo(QQUser user, QQActionListener qqActionListener);

    /**
     * <p>getUserQQ.</p>
     *
     * @param user             a {@link iqq.im.bean.QQUser} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getUserQQ(QQUser user, QQActionListener qqActionListener);

    /**
     * <p>getUserLevel.</p>
     *
     * @param user             a {@link iqq.im.bean.QQUser} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getUserLevel(QQUser user, QQActionListener qqActionListener);

    /**
     * <p>getStrangerInfo.</p>
     *
     * @param user             a {@link iqq.im.bean.QQUser} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getStrangerInfo(QQUser user, QQActionListener qqActionListener);

    /**
     * <p>getGroupList.</p>
     *
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getGroupList(QQActionListener listener);

    /**
     * <p>getGroupFace.</p>
     *
     * @param group            a {@link iqq.im.bean.QQGroup} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getGroupFace(QQGroup group, QQActionListener qqActionListener);

    /**
     * <p>getGroupInfo.</p>
     *
     * @param group            a {@link iqq.im.bean.QQGroup} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getGroupInfo(QQGroup group, QQActionListener qqActionListener);

    /**
     * <p>getGroupGid.</p>
     *
     * @param group            a {@link iqq.im.bean.QQGroup} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getGroupGid(QQGroup group, QQActionListener qqActionListener);

    /**
     * <p>getGroupMemberStatus.</p>
     *
     * @param group    a {@link iqq.im.bean.QQGroup} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getGroupMemberStatus(QQGroup group, QQActionListener listener);

    /**
     * <p>getDiscuzList.</p>
     *
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getDiscuzList(QQActionListener qqActionListener);

    /**
     * <p>getDiscuzInfo.</p>
     *
     * @param discuz           a {@link iqq.im.bean.QQDiscuz} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getDiscuzInfo(QQDiscuz discuz, QQActionListener qqActionListener);

    /**
     * <p>getSessionMsgSig.</p>
     *
     * @param user             a {@link iqq.im.bean.QQStranger} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getSessionMsgSig(QQStranger user, QQActionListener qqActionListener);

    /**
     * <p>sendMsg.</p>
     *
     * @param msg              a {@link iqq.im.bean.QQMsg} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture sendMsg(QQMsg msg, QQActionListener qqActionListener);

    /**
     * <p>sendShake.</p>
     *
     * @param user             a {@link iqq.im.bean.QQUser} object.
     * @param qqActionListener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture sendShake(QQUser user, QQActionListener qqActionListener);

    /**
     * <p>getOffPic.</p>
     *
     * @param offpic   a {@link iqq.im.bean.content.OffPicItem} object.
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     * @param picout   a {@link java.io.OutputStream} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getOffPic(OffPicItem offpic, QQMsg msg,
                                    OutputStream picout, QQActionListener listener);

    /**
     * <p>getUserPic.</p>
     *
     * @param cface    a {@link iqq.im.bean.content.CFaceItem} object.
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     * @param picout   a {@link java.io.OutputStream} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getUserPic(CFaceItem cface, QQMsg msg,
                                     OutputStream picout, QQActionListener listener);

    /**
     * <p>getGroupPic.</p>
     *
     * @param cface    a {@link iqq.im.bean.content.CFaceItem} object.
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     * @param picout   a {@link java.io.OutputStream} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture getGroupPic(CFaceItem cface, QQMsg msg,
                                      OutputStream picout, QQActionListener listener);

    /**
     * <p>uploadOffPic.</p>
     *
     * @param user     a {@link iqq.im.bean.QQUser} object.
     * @param file     a {@link java.io.File} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture uploadOffPic(QQUser user, File file, QQActionListener listener);

    /**
     * <p>uploadCustomPic.</p>
     *
     * @param file     a {@link java.io.File} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture uploadCustomPic(File file, QQActionListener listener);

    /**
     * <p>sendInputNotify.</p>
     *
     * @param user     a {@link iqq.im.bean.QQUser} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture sendInputNotify(QQUser user, QQActionListener listener);

    /**
     * <p>freshVerify.</p>
     *
     * @param verifyEvent a {@link iqq.im.event.QQNotifyEvent} object.
     * @param listener    a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture freshVerify(QQNotifyEvent verifyEvent, QQActionListener listener);

    /**
     * <p>updateGroupMessageFilter.</p>
     *
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture updateGroupMessageFilter(QQActionListener listener);


    /**
     * <p>searchGroupGetList.</p>
     *
     * @param resultList a {@link iqq.im.bean.QQGroupSearchList} object.
     * @param listener   a {@link iqq.im.QQActionListener} object.
     * @return a {@link iqq.im.event.QQActionFuture} object.
     */
    public QQActionFuture searchGroupGetList(QQGroupSearchList resultList, QQActionListener listener);

    /**
     * <p>submitVerify.</p>
     *
     * @param code        a {@link java.lang.String} object.
     * @param verifyEvent a {@link iqq.im.event.QQNotifyEvent} object.
     */
    public void submitVerify(String code, QQNotifyEvent verifyEvent);

    /**
     * <p>cancelVerify.</p>
     *
     * @param verifyEvent a {@link iqq.im.event.QQNotifyEvent} object.
     * @throws iqq.im.QQException if any.
     */
    public void cancelVerify(QQNotifyEvent verifyEvent) throws QQException;

    /**
     * <p>beginPollMsg.</p>
     */
    public void beginPollMsg();

    /**
     * <p>setHttpUserAgent.</p>
     *
     * @param userAgent a {@link java.lang.String} object.
     */
    public void setHttpUserAgent(String userAgent);

    /**
     * <p>setHttpProxy.</p>
     *
     * @param proxyType         a {@link iqq.im.service.HttpService.ProxyType} object.
     * @param proxyHost         a {@link java.lang.String} object.
     * @param proxyPort         a int.
     * @param proxyAuthUser     a {@link java.lang.String} object.
     * @param proxyAuthPassword a {@link java.lang.String} object.
     */
    public void setHttpProxy(ProxyType proxyType, String proxyHost,
                             int proxyPort, String proxyAuthUser, String proxyAuthPassword);


    /**
     * <p>getBuddyList.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<QQBuddy> getBuddyList();

    /**
     * <p>getGroupList.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<QQGroup> getGroupList();

    /**
     * <p>getDiscuzList.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<QQDiscuz> getDiscuzList();

    /**
     * <p>getBuddyByUin.</p>
     *
     * @param uin a long.
     * @return a {@link iqq.im.bean.QQBuddy} object.
     */
    public QQBuddy getBuddyByUin(long uin);

    /**
     * <p>getAccount.</p>
     *
     * @return a {@link iqq.im.bean.QQAccount} object.
     */
    public QQAccount getAccount();

    /**
     * <p>isOnline.</p>
     *
     * @return a boolean.
     */
    public boolean isOnline();

    /**
     * <p>isLogining.</p>
     *
     * @return a boolean.
     */
    public boolean isLogining();

    /**
     * 接受别人加你为好友的请求
     *
     * @param qq
     * @param qqActionListener
     */
    public void acceptBuddyRequest(String qq, QQActionListener qqActionListener);

    /**
     *
     * @param msg
     * @param qqActionListener
     * @return
     */
    public QQActionFuture getConvertMsg(QQMsg msg, QQActionListener qqActionListener);
}
