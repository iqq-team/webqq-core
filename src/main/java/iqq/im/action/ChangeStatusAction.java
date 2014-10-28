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
 * 改变在线状态
 *
 * @author ChenZhiHui
 */
public class ChangeStatusAction extends AbstractHttpAction {

	private QQStatus status;

	/**
	 * <p>Constructor for ChangeStatusAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 * @param status a {@link iqq.im.bean.QQStatus} object.
	 */
	public ChangeStatusAction(QQContext context, QQActionListener listener,
			QQStatus status) {
		super(context, listener);
		this.status = status;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_CHANGE_STATUS);
		req.addGetValue("newstatus", status.getValue());
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
		if (json.getInt("retcode") == 0) {
			getContext().getAccount().setStatus(status);
			notifyActionEvent(QQActionEvent.Type.EVT_OK, status);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
				new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString()));
		}
	}

}
