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
 * Created  : 2012-9-6
 * License  : Apache License 2.0
 */
package iqq.im;

import iqq.im.actor.ThreadActorDispatcher;
import iqq.im.bean.*;
import iqq.im.bean.content.*;
import iqq.im.core.QQConstants;
import iqq.im.event.*;
import iqq.im.event.QQActionEvent.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Client测试类
 *
 * @author solosky
 */
public class WebQQClientTest {
    private static final Logger LOG = LoggerFactory.getLogger(WebQQClientTest.class);
    QQClient client;

    public WebQQClientTest(String user, String pwd) {
        client = new WebQQClient(user, pwd, new QQNotifyHandlerProxy(this), new ThreadActorDispatcher());
    }

    /**
     * 程序入口
     */
    public static void main(String[] args) {
        WebQQClientTest test = new WebQQClientTest("201286738", "xy89415799");
        test.login();
    }

    /**
     * 聊天消息通知，使用这个注解可以收到QQ消息
     * <p/>
     * 接收到消息然后组装消息发送回去
     *
     * @throws QQException
     */
    @QQNotifyHandler(QQNotifyEvent.Type.CHAT_MSG)
    public void processBuddyMsg(QQNotifyEvent event) throws QQException {
        QQMsg msg = (QQMsg) event.getTarget();

        LOG.info("[消息] " + msg.getFrom().getNickname() + "说:" + msg.packContentList());
        System.out.print("消息内容: ");
        List<ContentItem> items = msg.getContentList();
        for (ContentItem item : items) {
            if (item.getType() == ContentItem.Type.FACE) {
                System.out.print(" Face:" + ((FaceItem) item).getId());
            } else if (item.getType() == ContentItem.Type.OFFPIC) {
                System.out.print(" Picture:" + ((OffPicItem) item).getFilePath());
            } else if (item.getType() == ContentItem.Type.TEXT) {
                System.out.print(" Text:" + ((TextItem) item).getContent());
            }
        }

        if (msg.getType() == QQMsg.Type.BUDDY_MSG) {
            // 组装QQ消息发送回去
            QQMsg sendMsg = new QQMsg();
            sendMsg.setTo(msg.getFrom());                       // QQ好友UIN
            sendMsg.setType(QQMsg.Type.BUDDY_MSG);              // 发送类型为好友
            // QQ内容
            sendMsg.addContentItem(new TextItem("hello from webqq-core"));      // 添加文本内容
            sendMsg.addContentItem(new FaceItem(0));            // QQ id为0的表情
            sendMsg.addContentItem(new FontItem());             // 使用默认字体
            client.sendMsg(sendMsg, null);                      // 调用接口发送消息
        }
    }

    /**
     * 被踢下线通知
     */
    @QQNotifyHandler(QQNotifyEvent.Type.KICK_OFFLINE)
    protected void processKickOff(QQNotifyEvent event) {
        LOG.info("被踢下线: " + (String) event.getTarget());
    }

    /**
     * 需要验证码通知
     *
     * @throws IOException
     */
    @QQNotifyHandler(QQNotifyEvent.Type.CAPACHA_VERIFY)
    protected void processVerify(QQNotifyEvent event) throws IOException {
        QQNotifyEventArgs.ImageVerify verify = (QQNotifyEventArgs.ImageVerify) event.getTarget();
        ImageIO.write(verify.image, "png", new File("verify.png"));
        LOG.info(verify.reason);
        System.out.print("请输入在项目根目录下verify.png图片里面的验证码:");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
        client.submitVerify(code, event);
    }

    /**
     * 登录
     */
    public void login() {
        final QQActionListener listener = new QQActionListener() {
            public void onActionEvent(QQActionEvent event) {
                LOG.info("LOGIN_STATUS:" + event.getType() + ":" + event.getTarget());
                if (event.getType() == Type.EVT_OK) {
                    //到这里就算是登录成功了

                    //获取下用户信息
                    client.getUserInfo(client.getAccount(), new QQActionListener() {
                        public void onActionEvent(QQActionEvent event) {
                            LOG.info("LOGIN_STATUS:" + event.getType() + ":" + event.getTarget());
                        }
                    });

                    // 获取好友列表..TODO.
                    // 不一定调用，可能会有本地缓存
                    client.getBuddyList(new QQActionListener() {

                        @Override
                        public void onActionEvent(QQActionEvent event) {
                            // TODO Auto-generated method stub
                            LOG.info("******** " + event.getType()
                                    + " ********");
                            if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                LOG.info("******** 好友列表  ********");
                                List<QQCategory> qqCategoryList = (List<QQCategory>) event
                                        .getTarget();

                                for (QQCategory c : qqCategoryList) {
                                    LOG.info("分组名称:" + c.getName());
                                    List<QQBuddy> buddyList = c.getBuddyList();
                                    for (QQBuddy b : buddyList) {
                                        LOG.info("---- QQ nick:"
                                                + b.getNickname()
                                                + " markname:"
                                                + b.getMarkname() + " uin:"
                                                + b.getUin() + " isVip:"
                                                + b.isVip() + " vip_level:"
                                                + b.getVipLevel());
                                    }

                                }
                            } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                                LOG.info("** 好友列表获取失败，处理重新获取");
                            }
                        }
                    });
                    // 获取群列表
                    client.getGroupList(new QQActionListener() {

                        @Override
                        public void onActionEvent(QQActionEvent event) {
                            if (event.getType() == Type.EVT_OK) {
                                for (QQGroup g : client.getGroupList()) {
                                    client.getGroupInfo(g, null);
                                    LOG.info("Group: " + g.getName());
                                }
                            } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                                LOG.info("** 群列表获取失败，处理重新获取");
                            }
                        }
                    });
                    // 获取讨论组列表
                    client.getDiscuzList(new QQActionListener() {

                        @Override
                        public void onActionEvent(QQActionEvent event) {
                            if (event.getType() == Type.EVT_OK) {
                                for (QQDiscuz d : client.getDiscuzList()) {
                                    client.getDiscuzInfo(d, null);
                                    LOG.info("Discuz: " + d.getName());
                                }
                            }
                        }
                    });

                    // 查群测试
                    QQGroupSearchList list = new QQGroupSearchList();
                    list.setKeyStr("QQ");
                    client.searchGroupGetList(list, new QQActionListener() {
                        @Override
                        public void onActionEvent(QQActionEvent event) {
                            if (event.getType() == Type.EVT_OK) {

                            }
                        }
                    });

                    //启动轮询时，需要获取所有好友、群成员、讨论组成员
                    //所有的逻辑完了后，启动消息轮询
                    client.beginPollMsg();
                }else{
                    LOG.info("******************");
                    LOG.info(event.toString());
                }

            }
        };

        String ua = "Mozilla/5.0 (@os.name; @os.version; @os.arch) AppleWebKit/537.36 (KHTML, like Gecko) @appName Safari/537.36";
        ua = ua.replaceAll("@appName", QQConstants.USER_AGENT);
        ua = ua.replaceAll("@os.name", System.getProperty("os.name"));
        ua = ua.replaceAll("@os.version", System.getProperty("os.version"));
        ua = ua.replaceAll("@os.arch", System.getProperty("os.arch"));
        client.setHttpUserAgent(ua);
        client.login(QQStatus.ONLINE, listener);
    }

    /**
     * 新邮件通知
     * <p/>
     * 这个暂时没有启用
     *
     * @throws QQException
     */
    @QQNotifyHandler(QQNotifyEvent.Type.EMAIL_NOTIFY)
    public void processEmailMsg(QQNotifyEvent event) throws QQException {
        List<QQEmail> list = (List<QQEmail>) event.getTarget();
        for (QQEmail mail : list) {
            LOG.info("邮件: " + mail.getSubject());
        }
    }

}
