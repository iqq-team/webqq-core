/**
 * 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQDiscuzMember;
import iqq.im.bean.QQGroupMember;
import iqq.im.bean.QQStranger;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 临时消息信道，用于发送群 U 2 U会话消息
 * 
 * @author ZhiHui_Chen<6208317@qq.com>
 * @create date 2013-4-10
 */
public class GetSessionMsgSigAction extends AbstractHttpAction {
	private static final Logger LOG = Logger.getLogger(GetSessionMsgSigAction.class);
	private QQStranger user;

	/**
	 * @param context
	 * @param listener
	 */
	public GetSessionMsgSigAction(QQContext context, QQActionListener listener,
			QQStranger user) {
		super(context, listener);
		this.user = user;
	}

	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_GET_SESSION_MSG_SIG);
		if(user instanceof QQGroupMember) {
			QQGroupMember mb = (QQGroupMember) user;
			mb.setServiceType(0);
			req.addGetValue("id", mb.getGroup().getGin() + "");
			req.addGetValue("service_type", "0"); // 0为群，1为讨论组
		} else if(user instanceof QQDiscuzMember) {
			QQDiscuzMember mb = (QQDiscuzMember) user;
			mb.setServiceType(1);
			req.addGetValue("id", mb.getDiscuz().getDid() + "");
			req.addGetValue("service_type", "1"); // 0为群，1为讨论组
		} else {
			LOG.info("GetSessionMsgSigAction unknow type :" + user);
		}
		req.addGetValue("to_uin", user.getUin() + "");
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
		return req;
	}

	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		// {"retcode":0,"result":{"type":0,"value":"sig","flags":{"text":1,"pic":1,"file":1,"audio":1,"video":1}}}
		JSONObject json = new JSONObject(response.getResponseString());
		int retcode = json.getInt("retcode");

		if (retcode == 0) {
			JSONObject result = json.getJSONObject("result");
			if (result.has("value")) {
				user.setGroupSig(result.getString("value"));
				notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
				return;
			}
		}

		notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(
				QQErrorCode.UNEXPECTED_RESPONSE, json.toString()));
	}
}
