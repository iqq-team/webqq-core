/**
 * 
 */
package iqq.im.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

/**
 * 登录邮箱
 *
 * @author 承∮诺<6208317@qq.com>
 * @Created 2014年1月24日
 */
public class LoginEmailAction extends AbstractHttpAction {

	/**
	 * @param context
	 * @param listener
	 */
	public LoginEmailAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}
	
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_LOGIN_EMAIL);
		req.addGetValue("fun", "passport");
		req.addGetValue("from", "webqq");
		req.addGetValue("Referer", "https://mail.qq.com/cgi-bin/loginpage");
		return req;
	}
	
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		String REGXP = "sid=(.*?)\"";
		Pattern p = Pattern.compile(REGXP);
        Matcher m = p.matcher(response.getResponseString());
        if(m.find()){
        	String sid = m.group(1);
        	notifyActionEvent(QQActionEvent.Type.EVT_OK, sid);
        	System.out.println("LoginEmailAction***" + sid);
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
        }
	}

}
