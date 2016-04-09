
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登录邮箱
 *
 * @author 承∮诺
 * @since 2014年1月24日
 */
public class LoginEmailAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(LoginEmailAction.class);

	/**
	 * <p>Constructor for LoginEmailAction.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public LoginEmailAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_LOGIN_EMAIL);
		req.addGetValue("fun", "passport");
		req.addGetValue("from", "webqq");
		req.addGetValue("Referer", "https://mail.qq.com/cgi-bin/loginpage");
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		String REGXP = "sid=(.*?)\"";
		Pattern p = Pattern.compile(REGXP);
        Matcher m = p.matcher(response.getResponseString());
        if(m.find()){
        	String sid = m.group(1);
        	notifyActionEvent(QQActionEvent.Type.EVT_OK, sid);
        	LOG.info("LoginEmailAction***" + sid);
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
        }
	}

}
