package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录退出
 * 
 * @author ChenZhiHui
 * @create-time 2013-2-23
 */
public class WebLogoutAction extends AbstractHttpAction {

	public WebLogoutAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_LOGOUT);
		req.addGetValue("ids", ""); // 产生过会话才出现ID，如何获取？？
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		String isOK = json.getString("result");
		if (json.getInt("retcode") == 0) {
			if (isOK.equalsIgnoreCase("ok")) {
				notifyActionEvent(QQActionEvent.Type.EVT_OK, isOK);
				return;
			}
		}

		notifyActionEvent(QQActionEvent.Type.EVT_ERROR, isOK);
	}

}
