package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQClientType;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.core.QQStore;
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
 * @since 2013-2-23
 */
public class GetOnlineFriendAction extends AbstractHttpAction {

	/**
	 * <p>Constructor for GetOnlineFriendAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public GetOnlineFriendAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_GET_ONLINE_BUDDY_LIST);
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		QQStore store = getContext().getStore();
		if (json.getInt("retcode") == 0) {
			JSONArray result = json.getJSONArray("result");
			for (int i = 0; i < result.length(); i++) {
				JSONObject obj = result.getJSONObject(i);
				long uin = obj.getLong("uin");
				String status = obj.getString("status");
				int clientType = obj.getInt("client_type");
				
				QQBuddy buddy = store.getBuddyByUin(uin);
				buddy.setStatus(QQStatus.valueOfRaw(status));
				buddy.setClientType(QQClientType.valueOfRaw(clientType));
			}
			
		}

		notifyActionEvent(QQActionEvent.Type.EVT_OK, store.getOnlineBuddyList());
	}

}
