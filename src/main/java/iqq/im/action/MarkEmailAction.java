package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQEmail;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>MarkEmailAction class.</p>
 */
public class MarkEmailAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(MarkEmailAction.class);
	private boolean status;
	private List<QQEmail> markList;

	/**
	 * <p>Constructor for MarkEmailAction.</p>
	 *
	 * @param status a boolean.
	 * @param markList a {@link java.util.List} object.
	 * @param context a {@link iqq.im.core.QQContext} object.
	 * @param listener a {@link iqq.im.QQActionListener} object.
	 */
	public MarkEmailAction(boolean status, List<QQEmail> markList,
			QQContext context, QQActionListener listener) {
		super(context, listener);
		
		this.status = status;
		this.markList = markList;

	}
	
	/** {@inheritDoc} */
	@Override
	public QQHttpRequest buildRequest() throws QQException {
		QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_MARK_EMAIL);
		req.addPostValue("mailaction", "mail_flag");
		req.addPostValue("flag", "new");
		req.addPostValue("resp_charset", "UTF8");
		req.addPostValue("ef", "js");
		req.addPostValue("folderkey", "1");
		req.addPostValue("sid", getContext().getSession().getEmailAuthKey());
		req.addPostValue("status", status + "");
		for(QQEmail mail : markList) {
			req.addPostValue("mailid", mail.getId());
		}
		return req;
	}
	
	// ({msg : "new successful",rbkey : "1391255617",status : "false"})
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException {
		String ct = response.getResponseString();
		LOG.info("mark email: " + ct);
		if(ct.contains("success")) {
			notifyActionEvent(QQActionEvent.Type.EVT_OK, ct);
		} else {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, ct);
		}
	}

}
