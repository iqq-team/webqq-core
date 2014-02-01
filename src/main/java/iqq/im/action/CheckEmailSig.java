/**
 * 
 */
package iqq.im.action;

import org.json.JSONException;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

/**
 * 通过pt4获取到的URL进行封装
 * 检测邮箱是否合法登录了
 * 
 * @author 承∮诺<6208317@qq.com>
 * @Created 2014年1月24日
 */
public class CheckEmailSig extends AbstractHttpAction {
	
	private String url = "";
	/**
	 * @param context
	 * @param listener
	 */
	public CheckEmailSig(String url, QQContext context, QQActionListener listener) {
		super(context, listener);
		this.url = url;
	}
	
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		url += "&regmaster=undefined&aid=1";
		url += "&s_url=http%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Ffun%3Dpassport%26from%3Dwebqq";
		
		QQHttpRequest req = createHttpRequest("GET", url);
		return req;
	}
	
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		notifyActionEvent(QQActionEvent.Type.EVT_OK, "");
	}

}
