
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;
import iqq.im.util.QQEncryptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.json.JSONException;
import org.slf4j.LoggerFactory;

/**
 * pt4登录验证
 * 获取到一个链接
 *
 * @author 承∮诺
 * @since 2014年1月24日
 */
public class GetPT4Auth extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(GetPT4Auth.class);

	/**
	 * <p>Constructor for GetPT4Auth.</p>
	 *
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public GetPT4Auth(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		HttpService httpService = (HttpService) getContext().getSerivce(QQService.Type.HTTP);
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_PT4_AUTH);
		req.addGetValue("daid", "4");
		req.addGetValue("appid", "1");
		req.addGetValue("auth_token", QQEncryptor.time33(httpService.getCookie("supertoken",  QQConstants.URL_CHANNEL_LOGIN).getValue()) + "");
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		Pattern p = Pattern.compile(QQConstants.REGXP_EMAIL_AUTH);
        Matcher m = p.matcher(response.getResponseString());
        if(m.find()) {
        	String qqHex = m.group(2);
        	notifyActionEvent(QQActionEvent.Type.EVT_OK, qqHex);
        	LOG.info("GetPT4Auth: " + qqHex);
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
        			new QQException(QQException.QQErrorCode.INVALID_LOGIN_AUTH));
        }
	}
}
