package iqq.im;

import com.alibaba.fastjson.JSON;
import iqq.im.actor.SwingActorDispatcher;
import iqq.im.bean.QQDiscuz;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQUser;
import iqq.im.bean.content.FaceItem;
import iqq.im.bean.content.FontItem;
import iqq.im.bean.content.TextItem;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 使用二维码登录WebQQ
 * <p/>
 * <p/>
 * Created by Tony on 10/6/15.
 */
public class QRcodeLoginTest {

    static QQClient mClient = new WebQQClient(new QQNotifyListener() {
        @Override
        public void onNotifyEvent(QQNotifyEvent event) {
            System.out.println(event);
            switch (event.getType()) {
                case CHAT_MSG:
                    QQMsg revMsg = (QQMsg) event.getTarget();
                    revMsg(revMsg);
                    break;
            }
        }
    }, new SwingActorDispatcher());

    public static void main(String[] args) {
        // 获取二维码
        mClient.getQRcode(new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    try {
                        BufferedImage verify = (BufferedImage) event.getTarget();
                        ImageIO.write(verify, "png", new File("qrcode.png"));
                        System.out.println("请扫描在项目根目录下qrcode.png图片");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("获取二维码失败");
                }
            }
        });
        // 检查二维码状态
        mClient.checkQRCode(new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                System.out.println("checkQRCode " + event);
                switch (event.getType()) {
                    case EVT_OK:
                        // 扫描通过,登录成功
                        mClient.getBuddyList(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                    System.out.println("加载好友列表成功");
                                }
                            }
                        });
                        mClient.getGroupList(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                    System.out.println("加载群列表成功");
                                }
                            }
                        });
                        mClient.getDiscuzList(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                    System.out.println(JSON.toJSONString(mClient.getDiscuzList()));
                                    System.out.println("加载讨论组列表成功");
                                }
                            }
                        });
                        mClient.getSelfInfo(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                    System.out.println(JSON.toJSONString(event.getTarget()));
                                    System.out.println("获取个人信息成功");
                                }
                            }
                        });
                       mClient.beginPollMsg();
                        break;
                    case EVT_ERROR:
                        QQException ex = (QQException) (event.getTarget());
                        QQException.QQErrorCode code = ex.getError();
                        switch (code) {
                            // 二维码有效,等待用户扫描
                            // 二维码已经扫描,等用户允许登录
                            case QRCODE_OK:
                            case QRCODE_AUTH:
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // 继续检查二维码状态
                                mClient.checkQRCode(this);
                                break;
                        }
                        break;
                }
            }
        });
    }

    private static void revMsg(QQMsg revMsg) {
        switch (revMsg.getType()) {
            case BUDDY_MSG:
                sendMsg(revMsg.getFrom());
                break;
            case GROUP_MSG:
                sendMsg(revMsg.getGroup());
                break;
            case DISCUZ_MSG:
                sendDiscuz(revMsg.getDiscuz());
        }
        System.out.println(revMsg.getText());
    }

    public static void sendMsg(QQUser user) {
        System.out.println("sendMsg ");

        // 组装QQ消息发送回去
        QQMsg sendMsg = new QQMsg();
        sendMsg.setTo(user);                                // QQ好友UIN
        sendMsg.setType(QQMsg.Type.BUDDY_MSG);              // 发送类型为好友
        // QQ内容
        sendMsg.addContentItem(new TextItem("hello from iqq: https://github.com/im-qq")); // 添加文本内容
        sendMsg.addContentItem(new FaceItem(74));           // QQ id为0的表情
        sendMsg.addContentItem(new FontItem());             // 使用默认字体
        mClient.sendMsg(sendMsg, null);                     // 调用接口发送消息
    }

    public static void sendMsg(QQGroup group) {
        System.out.println("sendMsg " + group);

        // 组装QQ消息发送回去
        QQMsg sendMsg = new QQMsg();
        sendMsg.setGroup(group);                                // QQ好友UIN
        sendMsg.setType(QQMsg.Type.GROUP_MSG);              // 发送类型为好友
        // QQ内容
        sendMsg.addContentItem(new TextItem("hello from iqq: https://github.com/im-qq")); // 添加文本内容
        sendMsg.addContentItem(new FaceItem(74));           // QQ id为0的表情
        sendMsg.addContentItem(new FontItem());             // 使用默认字体
        mClient.sendMsg(sendMsg, null);                     // 调用接口发送消息
    }

    public static void sendDiscuz(QQDiscuz discuz) {
        System.out.println("sendMsg " + discuz);

        // 组装QQ消息发送回去
        QQMsg sendMsg = new QQMsg();
        sendMsg.setDiscuz(discuz);                                // QQ好友UIN
        sendMsg.setType(QQMsg.Type.DISCUZ_MSG);              // 发送类型为好友
        // QQ内容
        sendMsg.addContentItem(new TextItem("hello from iqq: https://github.com/im-qq")); // 添加文本内容
        sendMsg.addContentItem(new FaceItem(74));           // QQ id为0的表情
        sendMsg.addContentItem(new FontItem());             // 使用默认字体
        mClient.sendMsg(sendMsg, null);                     // 调用接口发送消息
    }
}
