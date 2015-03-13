package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQSession;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 接受别人的加好友请求
 *
 * @author elthon
 */
public class AcceptBuddyAddAction extends AbstractHttpAction {

	private String account;

	/**
	 * <p>Constructor for ChangeStatusAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param status a {@link iqq.im.bean.QQStatus} object.
	 */
	public AcceptBuddyAddAction(QQContext context, QQActionListener listener,
			String account) {
		super(context, listener);
		this.account = account;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("POST",
				QQConstants.URL_ACCEPET_BUDDY_ADD);
		
		JSONObject json = new JSONObject();
		json.put("account", account);
		json.put("gid", "0");
		json.put("mname", "");
		json.put("vfwebqq", session.getVfwebqq());
		req.addPostValue("r", json.toString());
		
		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		if (json.getInt("retcode") == 0) {
			notifyActionEvent(QQActionEvent.Type.EVT_OK, json);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
				new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString()));
		}
	}

}
