package iqq.im;

import com.oracle.tools.packager.Log;
import iqq.im.actor.ThreadActorDispatcher;
import iqq.im.bean.QQDiscuz;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQUser;
import iqq.im.bean.content.ContentItem;
import iqq.im.bean.content.FaceItem;
import iqq.im.bean.content.FontItem;
import iqq.im.bean.content.TextItem;
import iqq.im.core.QQConstants;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;
import iqq.im.event.QQNotifyHandler;
import iqq.im.event.QQNotifyHandlerProxy;
import iqq.im.http.XabHttpRequest;
import iqq.im.http.XabHttpResult;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * 使用二维码登录WebQQ
 * <p>
 * <p>
 * Created by Tony on 10/6/15.
 */
public class QRcodeLoginTest {

    QQClient mClient = null;

    public QRcodeLoginTest() {
        mClient = new WebQQClient(new QQNotifyHandlerProxy(this), new ThreadActorDispatcher());
    }

    public static void main(String[] args) {
        QRcodeLoginTest test = new QRcodeLoginTest();
        test.login();
    }
    public void login(){
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
                switch (event.getType()) {
                    case EVT_OK:
                        // 扫描通过,登录成功
                        Log.debug("扫描通过,登录成功 "+mClient.getAccount().toString());
                        mClient.beginPollMsg();
                        break;
                    case EVT_ERROR:
                        QQException ex = (QQException) (event.getTarget());
                        QQException.QQErrorCode code = ex.getError();
                        switch (code) {
                            // 二维码有效,等待用户扫描
                            // 二维码已经扫描,等用户允许登录
                            case QRCODE_OK:
                                Log.debug("QRCODE_OK");
                            case QRCODE_AUTH:
                                Log.debug("QRCODE_AUTH");
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

    private void revMsg(QQMsg revMsg) {
        switch (revMsg.getType()) {
            case BUDDY_MSG:
                sendMsg(revMsg.getFrom(), revMsg);
                break;
            case GROUP_MSG:
                sendMsg(revMsg.getGroup());
                break;
            case DISCUZ_MSG:
                sendDiscuz(revMsg.getDiscuz());
        }
    }

    public void sendMsg(QQUser user, QQMsg revMsg) {
        Log.debug("sendMsg " + user);
        if (StringUtils.contains(revMsg.getText(), "安全退出")) {
            mClient.logout(null);
            return;
        }
        // 组装QQ消息发送回去
        QQMsg sendMsg = new QQMsg();
        sendMsg.setTo(user);                                // QQ好友UIN
        sendMsg.setType(QQMsg.Type.BUDDY_MSG);              // 发送类型为好友
        // QQ内容
        sendMsg.addContentItem(new TextItem("不要和我说话，正在调试")); // 添加文本内容

        XabHttpResult result = XabHttpRequest.getInstance().Get(QQConstants.URL_CONVERT_MSG);
        sendMsg.addContentItem(new TextItem(result.getEOS()));
        sendMsg.addContentItem(new FaceItem(74));           // QQ id为0的表情
        sendMsg.addContentItem(new FontItem());             // 使用默认字体
        mClient.sendMsg(sendMsg, null);


//        mClient.getConvertMsg(sendMsg, new ConvertMsgListener(sendMsg));
    }

    class ConvertMsgListener implements QQActionListener {
        QQMsg sendMsg;

        public ConvertMsgListener(QQMsg sendMsg) {
            this.sendMsg = sendMsg;
        }

        @Override
        public void onActionEvent(QQActionEvent event) {
            if (event.getType() == QQActionEvent.Type.EVT_OK) {
                System.out.println(event.getTarget());
                sendMsg.addContentItem(new TextItem(event.getTarget().toString()));
                mClient.sendMsg(sendMsg, null);                     // 调用接口发送消息
            }
        }
    }

    public void sendMsg(QQGroup group) {
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

    public void sendDiscuz(QQDiscuz discuz) {
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

    @QQNotifyHandler(QQNotifyEvent.Type.CHAT_MSG)
    public void processBuddyMsg(QQNotifyEvent event) throws QQException {
        QQMsg msg = (QQMsg) event.getTarget();

        System.out.println("[消息] " + msg.getFrom().getNickname() + "说:" + msg.packContentList());
        System.out.print("消息内容: ");
        List<ContentItem> items = msg.getContentList();
    }
}
