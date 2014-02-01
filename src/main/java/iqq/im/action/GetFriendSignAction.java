package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQUser;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 个人签名
 * 
 * @author ChenZhiHui
 * @create-time 2013-2-23
 */
public class GetFriendSignAction extends AbstractHttpAction {

	private QQUser buddy;

	public GetFriendSignAction(QQContext context, QQActionListener listener,
			QQUser buddy) {
		super(context, listener);
		this.buddy = buddy;
	}

	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_GET_USER_SIGN);
		req.addGetValue("tuin", buddy.getUin() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		if (json.getInt("retcode") == 0) {
			JSONArray result = json.getJSONArray("result");
			JSONObject obj = result.getJSONObject(0);
			buddy.setSign(obj.getString("lnick"));
		}

		notifyActionEvent(QQActionEvent.Type.EVT_OK, buddy);
	}

}
