package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQStranger;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息发送
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class SendMsgAction extends AbstractHttpAction {

    private static final Logger LOG = LoggerFactory.getLogger(SendMsgAction.class);
    private static long msgId = 81690000;
    private QQMsg msg;

    /**
     * <p>Constructor for SendMsgAction.</p>
     *
     * @param context  a {@link iqq.im.core.QQContext} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     * @param msg      a {@link iqq.im.bean.QQMsg} object.
     */
    public SendMsgAction(QQContext context, QQActionListener listener, QQMsg msg) {
        super(context, listener);
        this.msg = msg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
        // r:{"to":2982077931,"face":0,"content":"[\"123\",[\"face\",1],\"456\",[\"face\",0],\"\",\"\\n【提示：此用户正在使用Q+ Web：http://web.qq.com/】\",[\"font\",{\"name\":\"微软雅黑\",\"size\":\"11\",\"style\":[0,0,0],\"color\":\"ffcc99\"}]]","msg_id":91310001,"clientid":"74131454","psessionid":"8368046764001e636f6e6e7365727665725f77656271714031302e3133332e34312e3230320000230700001f01026e04002aafd23f6d0000000a40484a526f4866467a476d00000028d954c71693cd99ae8c0c64b651519e88f55ce5075140346da7d957f3abefb51d0becc25c425d7cf5"}
        // r:{"group_uin":3408869879,"content":"[\"群消息发送测试\",[\"face\",13],\"\",\"\\n【提示：此用户正在使用Q+ Web：http://web.qq.com/】\",[\"font\",{\"name\":\"微软雅黑\",\"size\":\"11\",\"style\":[0,0,0],\"color\":\"ffcc99\"}]]","msg_id":91310002,"clientid":"74131454","psessionid":"8368046764001e636f6e6e7365727665725f77656271714031302e3133332e34312e3230320000230700001f01026e04002aafd23f6d0000000a40484a526f4866467a476d00000028d954c71693cd99ae8c0c64b651519e88f55ce5075140346da7d957f3abefb51d0becc25c425d7cf5"}
        // clientid、psessionid
        QQHttpRequest req = null;
        QQSession session = getContext().getSession();
        JSONObject json = new JSONObject();
        if (msg.getType() == QQMsg.Type.BUDDY_MSG) {
            req = createHttpRequest("POST", QQConstants.URL_SEND_BUDDY_MSG);
            json.put("to", msg.getTo().getUin());
            json.put("face", 0); // 这个是干嘛的？？
        } else if (msg.getType() == QQMsg.Type.GROUP_MSG) {
            req = createHttpRequest("POST", QQConstants.URL_SEND_GROUP_MSG);
            json.put("group_uin", msg.getGroup().getGin());
            json.put("face", 0);
        } else if (msg.getType() == QQMsg.Type.DISCUZ_MSG) {
            req = createHttpRequest("POST", QQConstants.URL_SEND_DISCUZ_MSG);
            json.put("did", msg.getDiscuz().getDid());
            json.put("face", 573);
        } else if (msg.getType() == QQMsg.Type.SESSION_MSG) {    // 临时会话消息
            req = createHttpRequest("POST", QQConstants.URL_SEND_SESSION_MSG);
            QQStranger member = (QQStranger) msg.getTo();
            json.put("to", member.getUin());
            json.put("face", 0); // 这个是干嘛的？？
            json.put("group_sig", member.getGroupSig());
            json.put("service_type", member.getServiceType() + "");
        } else {
            LOG.warn("unknown MsgType: " + msg.getType());
        }

        json.put("content", msg.packContentList());
        json.put("msg_id", ++msgId);
        json.put("clientid", session.getClientId());
        json.put("psessionid", session.getSessionId());

        req.addPostValue("r", json.toString());
        req.addHeader("Referer", QQConstants.REFFER);

        System.out.println("sendMsg: " + json.toString());
        return req;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
        System.out.println("send Msg result: " + response.getResponseString());
        JSONObject json = new JSONObject(response.getResponseString());
        int retcode = json.getInt("retcode");
        if (retcode == 0 || retcode == 100100) {
            String result = json.getString("result");
            if (result.equals("ok")) {
                notifyActionEvent(QQActionEvent.Type.EVT_OK, msg);
                return;
            }
        }
        notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNEXPECTED_RESPONSE, json.toString()));
    }

}
