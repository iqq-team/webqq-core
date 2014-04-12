package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQEmail;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQNotifyEvent;
import iqq.im.event.QQNotifyEvent.Type;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 * 邮件轮询
 *
 * @author 承∮诺<6208317@qq.com>
 * @Created 2014年1月25日
 */
public class PollEmailAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(PollEmailAction.class);

	private String sid = "";
	private long t = 0;
	
	public PollEmailAction(String sid, long t, QQContext context, QQActionListener listener) {
		super(context, listener);
		this.sid = sid;
		this.t = t;
	}
	
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_EMAIL_POLL);
		req.addGetValue("r", Math.random() + "");
		req.addGetValue("u", getContext().getAccount().getUsername());
		req.addGetValue("s", "7");
		req.addGetValue("k", sid);
		req.addGetValue("t", t + "");
		req.addGetValue("i", "30");
		req.addGetValue("r", Math.random() + "");
		req.setReadTimeout(70 * 1000);
		req.setConnectTimeout(10 * 1000);
		req.addHeader("Referer", "http://wp.mail.qq.com/ajax_proxy.html?mail.qq.com&v=110702");
		return req;
	}
	
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		String content = response.getResponseString();
		LOG.info("Poll email content: " + content);
		if(content.startsWith("({e:-101")) {
			// 空，没有新邮件
			notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
		} else if(content.startsWith("({e:-100")){
			// 凭证已经失效，需要重新登录或者获取wpkey	
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
					new QQException(QQException.QQErrorCode.INVALID_LOGIN_AUTH, content));
		} else {
			content = content.substring(1, content.length() - 1);
			JSONArray arr = new JSONArray(content);
			// 封装返回的邮件列表
			List<QQEmail> list = new ArrayList<QQEmail>();
			for(int i=0; i<arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);
				JSONObject ct = json.getJSONObject("c");
				QQEmail mail = new QQEmail();
				mail.setFlag(json.getLong("t"));
				mail.setId(ct.getString("mailid"));
				mail.setSender(ct.getString("sender"));
				mail.setSenderNick(ct.getString("senderNick"));
				mail.setSenderEmail(ct.getString("senderEmail"));
				mail.setSubject(ct.getString("subject"));
				mail.setSummary(ct.getString("summary"));
				mail.setUnread(true);
				list.add(mail);
			}
			notifyActionEvent(QQActionEvent.Type.EVT_OK, 
					new QQNotifyEvent(Type.EMAIL_NOTIFY, list));
		}
	}

}
