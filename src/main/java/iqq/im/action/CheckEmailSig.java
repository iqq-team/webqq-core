
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import org.json.JSONException;

/**
 * 通过pt4获取到的URL进行封装
 * 检测邮箱是否合法登录了
 *
 * @author 承∮诺
 */
public class CheckEmailSig extends AbstractHttpAction {
	
	private String url = "";

	/**
	 * <p>Constructor for CheckEmailSig.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public CheckEmailSig(String url, QQContext context, QQActionListener listener) {
		super(context, listener);
		this.url = url;
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		url += "&regmaster=undefined&aid=1";
		url += "&s_url=http%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Ffun%3Dpassport%26from%3Dwebqq";
		
		QQHttpRequest req = createHttpRequest("GET", url);
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		notifyActionEvent(QQActionEvent.Type.EVT_OK, "");
	}

}
