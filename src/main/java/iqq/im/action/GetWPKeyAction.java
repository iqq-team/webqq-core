package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过登录得到的sid，获取到wpkey
 *
 * @author 承∮诺
 * @since 2014年1月25日
 */
public class GetWPKeyAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(GetWPKeyAction.class);
	private String sid = "";
	/**
	 * <p>Constructor for GetWPKeyAction.</p>
	 *
	 * @param sid a {@link java.lang.String} object.
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public GetWPKeyAction(String sid, QQContext context, QQActionListener listener) {
		super(context, listener);
		this.sid = sid;
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_WP_KEY);
		req.addGetValue("r", "0.7975904128979892");
		req.addGetValue("resp_charset", "UTF8");
		req.addGetValue("ef", "js");
		req.addGetValue("sid", sid);
		req.addGetValue("Referer", "http://mail.qq.com/cgi-bin/frame_html?sid=" + sid);
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		String resp = response.getResponseString();
		resp = resp.substring(1, resp.length() - 1);
		JSONObject json = new JSONObject(resp);
		if(json.has("k")) {
			notifyActionEvent(QQActionEvent.Type.EVT_OK, json.getString("k"));
		} else {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
		}
		LOG.info("GetWPKeyAction: " + response.getResponseString());
	}

}
