package iqq.im;

import com.xabad.tuling.JsonU;
import com.xabad.tuling.StringU;
import com.xabad.tuling.TulingU;
import iqq.im.actor.ThreadActorDispatcher;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQCategory;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQMsg;
import iqq.im.bean.content.*;
import iqq.im.core.QQConstants;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;
import iqq.im.event.QQNotifyHandler;
import iqq.im.event.QQNotifyHandlerProxy;
import iqq.im.http.XabHttpRequest;
import iqq.im.http.XabHttpResult;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用二维码登录WebQQ
 * <p>
 * <p>
 * Created by Tony on 10/6/15.
 */
public class QRcodeLoginTest {
    private static final Logger LOG = LoggerFactory.getLogger(QRcodeLoginTest.class);
    QQClient client = null;
    List<Long> uinList = new ArrayList<Long>();

    public QRcodeLoginTest() {
        client = new WebQQClient(new QQNotifyHandlerProxy(this), new ThreadActorDispatcher());
    }

    public static void main(String[] args) {
        QRcodeLoginTest test = new QRcodeLoginTest();
        test.login();
    }

    public void login() {
        // 获取二维码
        client.getQRcode(new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                    try {
                        BufferedImage verify = (BufferedImage) event.getTarget();
                        ImageIO.write(verify, "png", new File("qrcode.png"));
                        LOG.info("请扫描在项目根目录下qrcode.png图片");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    LOG.info("获取二维码失败" + event.getTarget());
                }
            }
        });
        client.checkQRCode(new QQActionListener() {
            @Override
            public void onActionEvent(QQActionEvent event) {
                switch (event.getType()) {
                    case EVT_OK:
                        LOG.debug("SUCCESS");
                        client.getSelfInfo(new QQActionListener() {
                            public void onActionEvent(QQActionEvent event) {
//                                String selfInfo = (String) event.getTarget();
                                LOG.debug(event.getTarget().toString());
                            }
                        });
                        client.getBuddyList(new QQActionListener() {
                            @Override
                            public void onActionEvent(QQActionEvent event) {
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
//                        // 获取群列表
                        client.getGroupList(new QQActionListener() {

                            @Override
                            public void onActionEvent(QQActionEvent event) {
                                if (event.getType() == QQActionEvent.Type.EVT_OK) {
                                    List<QQGroup> groupList = (List<QQGroup>) event.getTarget();
                                    for (QQGroup g : groupList) {
                                        client.getGroupInfo(g, null);
                                        LOG.info("Group: " + g.getName());
                                    }
                                } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
                                    LOG.info("** 群列表获取失败，处理重新获取");
                                }
                            }
                        });
//                        // 获取讨论组列表
//                        client.getDiscuzList(new QQActionListener() {
//
//                            @Override
//                            public void onActionEvent(QQActionEvent event) {
//                                if (event.getType() == QQActionEvent.Type.EVT_OK) {
//                                    for (QQDiscuz d : client.getDiscuzList()) {
//                                        client.getDiscuzInfo(d, null);
//                                        LOG.info("Discuz: " + d.getName());
//                                    }
//                                }
//                            }
//                        });
                        client.beginPollMsg();
                        break;
                    case EVT_ERROR:
                        QQException ex = (QQException) (event.getTarget());
                        QQException.QQErrorCode code = ex.getError();
                        switch (code) {
                            case QRCODE_OK:
                                LOG.debug("等待扫描二维码!");
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                client.checkQRCode(this);
                                break;
                            case QRCODE_AUTH:
                                LOG.debug("二维码已经扫描,等待验证!");
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                client.checkQRCode(this);
                                break;
                            case QRCODE_INVALID:
                                LOG.debug("二维码已经过期!");
                                break;
                            default:
                                LOG.debug("扫描二维码发生未知错误!");
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @QQNotifyHandler(QQNotifyEvent.Type.CHAT_MSG)
    public void processBuddyMsg(QQNotifyEvent event) throws QQException {
        QQMsg msg = (QQMsg) event.getTarget();

        LOG.info("[消息] " + msg.getFrom().getNickname() + "说:" + msg.getText());
        List<ContentItem> items = msg.getContentList();

        if (msg.getType() == QQMsg.Type.BUDDY_MSG) {
            // 组装QQ消息发送回去
            String content = "";
            for (ContentItem item : items) {
                if (item.getType() == ContentItem.Type.FACE) {
                    System.out.print(" Face:" + ((FaceItem) item).getId());
                } else if (item.getType() == ContentItem.Type.OFFPIC) {
                    System.out.print(" Picture:" + ((OffPicItem) item).getFilePath());
                } else if (item.getType() == ContentItem.Type.TEXT) {
                    content = ((TextItem) item).getContent();
                }
            }
            QQMsg sendMsg = new QQMsg();
            sendMsg.setTo(msg.getFrom());                       // QQ好友UIN
            sendMsg.setType(QQMsg.Type.BUDDY_MSG);
            XabHttpResult result = null;
            try {
                result = XabHttpRequest.getInstance().Get(QQConstants.URL_CONVERT_MSG + URLEncoder.encode(content, "UTF-8"));
                String baseJson = result.getEOS();
                int code = JsonU.getInt(baseJson, "code");
                switch (code) {
                    case TulingU.text:
                        sendMsg.addContentItem(new TextItem(StringU.replace(JsonU.getString(baseJson, "text"),"<br>","\r\n")));
                        sendMsg.addContentItem(new FontItem());             // 使用默认字体
                        client.sendMsg(sendMsg, null);
                        break;
                    case TulingU.news:
                        JSONArray array = JsonU.getJsonArray(baseJson, "list");
                        int arrayLength = array.length();
                        for (int i = 0; i < arrayLength; i++) {
                            String string = array.getString(i);
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "article")));
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "detailurl")));
                            sendMsg.addContentItem(new FontItem());             // 使用默认字体
                            client.sendMsg(sendMsg, null);

                        }
                        break;
                    case TulingU.link:
                        sendMsg.addContentItem(new TextItem("点击打开: " + JsonU.getString(baseJson, "url")));
                        sendMsg.addContentItem(new FontItem());             // 使用默认字体
                        client.sendMsg(sendMsg, null);
                        break;
                    case TulingU.cook:
                        JSONArray array1 = JsonU.getJsonArray(baseJson, "list");
                        int arrayLength1 = array1.length();
                        for (int i = 0; i < arrayLength1; i++) {
                            String string = array1.getString(i);
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "name") + "\n"));
                            sendMsg.addContentItem(new TextItem("材料 :" + JsonU.getString(string, "info") + "\n"));
                            sendMsg.addContentItem(new TextItem("点击打开: "));
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "detailurl")));
                            sendMsg.addContentItem(new FontItem());             // 使用默认字体
                            client.sendMsg(sendMsg, null);
                            break;
                        }
                        break;
                    default:
                        LOG.debug("不会回答!");
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            if (StringU.equals(content, "团长最帅")) {
//                QQMsg sendMsg = new QQMsg();
//                sendMsg.setTo(msg.getFrom());                       // QQ好友UIN
//                sendMsg.setType(QQMsg.Type.BUDDY_MSG);              // 发送类型为好友
//                // QQ内容
//                if (!uinList.contains(msg.getFrom().getUin())) {
//                    uinList.add(msg.getFrom().getUin());
//                    sendMsg.addContentItem(new TextItem("加入白名单成功."));      // 添加文本内容
//                } else {
//                    sendMsg.addContentItem(new TextItem("大哥/大姐 咱别重复加入了."));      // 添加文本内容
//                }
//                sendMsg.addContentItem(new FontItem());             // 使用默认字体
//                client.sendMsg(sendMsg, null);                      // 调用接口发送消息
//            }
        } else if (msg.getType() == QQMsg.Type.GROUP_MSG) {
            if (!uinList.contains(msg.getFrom().getUin())) {
                LOG.debug("不是管理员,无视.");
                return;
            }
            QQMsg sendMsg = new QQMsg();
            sendMsg.setGroup(msg.getGroup());                       // QQ好友UIN
            sendMsg.setType(QQMsg.Type.GROUP_MSG);
            XabHttpResult result = null;
            try {
                result = XabHttpRequest.getInstance().Get(QQConstants.URL_CONVERT_MSG + URLEncoder.encode(msg.getText(), "UTF-8"));
                String baseJson = result.getEOS();
                int code = JsonU.getInt(baseJson, "code");
                switch (code) {
                    case TulingU.text:
                        sendMsg.addContentItem(new TextItem(StringU.replace(JsonU.getString(baseJson, "text"),"<br>","\r\n")));
                        sendMsg.addContentItem(new FontItem());             // 使用默认字体
                        client.sendMsg(sendMsg, null);
                        break;
                    case TulingU.news:
                        JSONArray array = JsonU.getJsonArray(baseJson, "list");
                        int arrayLength = array.length();
                        for (int i = 0; i < arrayLength; i++) {
                            String string = array.getString(i);
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "article")));
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "detailurl")));
                            sendMsg.addContentItem(new FontItem());             // 使用默认字体
                            client.sendMsg(sendMsg, null);

                        }
                        break;
                    case TulingU.link:
                        sendMsg.addContentItem(new TextItem("点击打开: " + JsonU.getString(baseJson, "url")));
                        sendMsg.addContentItem(new FontItem());             // 使用默认字体
                        client.sendMsg(sendMsg, null);
                        break;
                    case TulingU.cook:
                        JSONArray array1 = JsonU.getJsonArray(baseJson, "list");
                        int arrayLength1 = array1.length();
                        for (int i = 0; i < arrayLength1; i++) {
                            String string = array1.getString(i);
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "name") + "\n"));
                            sendMsg.addContentItem(new TextItem("材料 :" + JsonU.getString(string, "info") + "\n"));
                            sendMsg.addContentItem(new TextItem("点击打开: "));
                            sendMsg.addContentItem(new TextItem(JsonU.getString(string, "detailurl")));
                            sendMsg.addContentItem(new FontItem());             // 使用默认字体
                            client.sendMsg(sendMsg, null);
                            break;
                        }
                        break;
                    default:
                        LOG.debug("不会回答!");
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
